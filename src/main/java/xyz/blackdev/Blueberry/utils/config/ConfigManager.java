package xyz.blackdev.Blueberry.utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A comprehensive configuration manager for Fabric mods.
 * Supports multiple configuration files, hot reloading, and serialization.
 */
public class ConfigManager {
    private static final Logger LOGGER = LogManager.getLogger("ConfigManager");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private final String modId;
    private final Path configDir;
    private final ConcurrentHashMap<String, ConfigEntry<?>> configs = new ConcurrentHashMap<>();
    private final Map<String, Consumer<Object>> changeListeners = new HashMap<>();

    /**
     * Creates a new ConfigManager instance.
     *
     * @param modId The ID of your mod
     */
    public ConfigManager(String modId) {
        this.modId = modId;
        this.configDir = FabricLoader.getInstance().getConfigDir().resolve(modId);

        try {
            if (!Files.exists(configDir)) {
                Files.createDirectories(configDir);
                LOGGER.info("Created config directory for mod {}: {}", modId, configDir);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to create config directory for mod {}", modId, e);
        }
    }

    /**
     * Registers and loads a configuration.
     * If the configuration exists on disk, it will be loaded.
     * Otherwise, a new configuration will be created using the default supplier.
     *
     * @param name The name of the configuration (will be used as filename)
     * @param defaultSupplier A supplier that provides the default configuration
     * @param configClass The class of the configuration
     * @param <T> The type of the configuration
     * @return The loaded or created configuration
     */
    public <T> T register(String name, Supplier<T> defaultSupplier, Class<T> configClass) {
        ConfigEntry<T> entry = new ConfigEntry<>(name, defaultSupplier, configClass);
        configs.put(name, entry);

        return loadOrCreateConfig(name, defaultSupplier, configClass);
    }

    /**
     * Loads a configuration from disk or creates a new one if it doesn't exist.
     * This method can be used both for initial loading and reloading configurations.
     *
     * @param name The name of the configuration
     * @param defaultSupplier A supplier that provides the default configuration (only used if config doesn't exist)
     * @param configClass The class of the configuration
     * @param <T> The type of the configuration
     * @return The loaded configuration
     */
    @SuppressWarnings("unchecked")
    public <T> T loadOrCreateConfig(String name, Supplier<T> defaultSupplier, Class<T> configClass) {
        Path configPath = configDir.resolve(name + ".json");

        // Use existing ConfigEntry or create a new one
        ConfigEntry<T> entry;
        if (configs.containsKey(name)) {
            entry = (ConfigEntry<T>) configs.get(name);
        } else {
            entry = new ConfigEntry<>(name, defaultSupplier, configClass);
            configs.put(name, entry);
        }

        // Check if config file exists
        if (Files.exists(configPath)) {
            try (Reader reader = Files.newBufferedReader(configPath)) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                T config = GSON.fromJson(json, configClass);
                entry.setValue(config);
                LOGGER.info("Loaded configuration '{}' for mod {}", name, modId);
                return config;
            } catch (Exception e) {
                LOGGER.error("Failed to load configuration '{}' for mod {}, using default", name, modId, e);
                T defaultConfig = defaultSupplier.get();
                entry.setValue(defaultConfig);
                saveConfig(name);
                return defaultConfig;
            }
        } else {
            // Create default config
            T defaultConfig = defaultSupplier.get();
            entry.setValue(defaultConfig);
            saveConfig(name);
            LOGGER.info("Created default configuration '{}' for mod {}", name, modId);
            return defaultConfig;
        }
    }

    /**
     * Creates or updates a configuration.
     * If the configuration exists, it will be updated.
     * Otherwise, a new configuration will be created.
     *
     * @param name The name of the configuration
     * @param config The configuration object
     * @param <T> The type of the configuration
     * @return The updated or created configuration
     */
    @SuppressWarnings("unchecked")
    public <T> T saveOrUpdateConfig(String name, T config) {
        if (configs.containsKey(name)) {
            // Update existing config
            ConfigEntry<T> entry = (ConfigEntry<T>) configs.get(name);
            entry.setValue(config);

            // Notify listeners
            if (changeListeners.containsKey(name)) {
                changeListeners.get(name).accept(config);
            }
        } else {
            // Create new config
            Class<T> configClass = (Class<T>) config.getClass();
            ConfigEntry<T> entry = new ConfigEntry<>(name, () -> config, configClass);
            configs.put(name, entry);
            entry.setValue(config);
            LOGGER.info("Created new configuration '{}' for mod {}", name, modId);
        }

        // Save the config
        saveConfig(name);
        return config;
    }

    /**
     * Saves a configuration to disk.
     *
     * @param name The name of the configuration
     * @return true if the save was successful, false otherwise
     */
    public boolean saveConfig(String name) {
        if (!configs.containsKey(name)) {
            LOGGER.error("Cannot save non-existent configuration '{}'", name);
            return false;
        }

        ConfigEntry<?> entry = configs.get(name);
        Path configPath = configDir.resolve(name + ".json");

        try {
            // Create backup if file exists
            if (Files.exists(configPath)) {
                Path backupPath = configDir.resolve(name + ".backup.json");
                Files.copy(configPath, backupPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }

            // Save config
            try (Writer writer = Files.newBufferedWriter(configPath)) {
                JsonElement json = GSON.toJsonTree(entry.getValue());
                GSON.toJson(json, writer);
                LOGGER.info("Saved configuration '{}' for mod {}", name, modId);
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("Failed to save configuration '{}' for mod {}", name, modId, e);
            return false;
        }
    }

    /**
     * Gets a configuration.
     *
     * @param name The name of the configuration
     * @param <T> The type of the configuration
     * @return The configuration, or null if it doesn't exist
     */
    @SuppressWarnings("unchecked")
    public <T> T getConfig(String name) {
        if (!configs.containsKey(name)) {
            LOGGER.warn("Attempted to get non-existent configuration '{}'", name);
            return null;
        }

        ConfigEntry<T> entry = (ConfigEntry<T>) configs.get(name);
        return entry.getValue();
    }

    /**
     * Reloads a specific configuration from disk.
     *
     * @param name The name of the configuration to reload
     * @param <T> The type of the configuration
     * @return The reloaded configuration or null if it couldn't be reloaded
     */
    @SuppressWarnings("unchecked")
    public <T> T reloadConfig(String name) {
        if (!configs.containsKey(name)) {
            LOGGER.warn("Cannot reload non-existent configuration '{}'", name);
            return null;
        }

        ConfigEntry<?> rawEntry = configs.get(name);

        try {
            ConfigEntry<T> entry = (ConfigEntry<T>) rawEntry;
            Supplier<T> defaultSupplier = entry.getDefaultSupplier();
            Class<T> configClass = entry.getConfigClass();

            T config = loadOrCreateConfig(name, defaultSupplier, configClass);

            // Notify listeners
            if (changeListeners.containsKey(name)) {
                changeListeners.get(name).accept(config);
            }

            return config;
        } catch (ClassCastException e) {
            LOGGER.error("Type error when reloading configuration '{}'", name, e);
            return null;
        }
    }

    /**
     * Reloads all configurations from disk.
     */
    public void reloadAllConfigs() {
        for (String name : new ArrayList<>(configs.keySet())) {  // Use a copy to avoid concurrent modification
            reloadConfig(name);
        }
        LOGGER.info("Reloaded all configurations for mod {}", modId);
    }

    /**
     * Unloads a configuration, removing it from memory.
     * The file on disk remains untouched.
     *
     * @param name The name of the configuration
     * @return true if the configuration was unloaded, false if it didn't exist
     */
    public boolean unloadConfig(String name) {
        if (!configs.containsKey(name)) {
            return false;
        }

        configs.remove(name);
        LOGGER.info("Unloaded configuration '{}' for mod {}", name, modId);
        return true;
    }

    /**
     * Deletes a configuration file from disk and removes it from memory.
     *
     * @param name The name of the configuration
     * @return true if the configuration was deleted, false if it didn't exist
     */
    public boolean deleteConfig(String name) {
        Path configPath = configDir.resolve(name + ".json");

        if (!Files.exists(configPath)) {
            LOGGER.warn("Configuration file '{}' does not exist", name);
            return false;
        }

        try {
            // Create backup before deletion
            Path backupDir = configDir.resolve("backups");
            Files.createDirectories(backupDir);
            Path backupPath = backupDir.resolve(name + ".deleted.json");
            Files.copy(configPath, backupPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            // Delete the file
            Files.delete(configPath);

            // Remove from memory
            configs.remove(name);

            LOGGER.info("Deleted configuration '{}' for mod {}", name, modId);
            return true;
        } catch (IOException e) {
            LOGGER.error("Failed to delete configuration '{}' for mod {}", name, modId, e);
            return false;
        }
    }

    /**
     * Registers a listener for configuration changes.
     *
     * @param name The name of the configuration
     * @param listener The listener to register
     */
    public void registerChangeListener(String name, Consumer<Object> listener) {
        changeListeners.put(name, listener);
    }

    /**
     * Checks if a configuration exists in memory.
     *
     * @param name The name of the configuration
     * @return true if the configuration exists in memory, false otherwise
     */
    public boolean hasConfig(String name) {
        return configs.containsKey(name);
    }

    /**
     * Checks if a configuration exists on disk.
     *
     * @param name The name of the configuration
     * @return true if the configuration exists on disk, false otherwise
     */
    public boolean configExistsOnDisk(String name) {
        Path configPath = configDir.resolve(name + ".json");
        return Files.exists(configPath);
    }

    /**
     * Lists all available configuration files in the config directory.
     *
     * @return A list of configuration names (without the .json extension)
     */
    public List<String> listAllConfigs() {
        List<String> configNames = new ArrayList<>();

        try {
            Files.list(configDir)
                    .filter(path -> path.toString().endsWith(".json"))
                    .filter(path -> !path.toString().endsWith(".backup.json"))
                    .forEach(path -> {
                        String fileName = path.getFileName().toString();
                        configNames.add(fileName.substring(0, fileName.length() - 5)); // Remove .json
                    });
        } catch (IOException e) {
            LOGGER.error("Failed to list configurations for mod {}", modId, e);
        }

        return configNames;
    }

    /**
     * Gets the config directory for this mod.
     *
     * @return The Path to the config directory
     */
    public Path getConfigDir() {
        return configDir;
    }

    /**
     * Internal class representing a configuration entry.
     */
    private static class ConfigEntry<T> {
        private final String name;
        private final Supplier<T> defaultSupplier;
        private final Class<T> configClass;
        private T value;

        public ConfigEntry(String name, Supplier<T> defaultSupplier, Class<T> configClass) {
            this.name = name;
            this.defaultSupplier = defaultSupplier;
            this.configClass = configClass;
        }

        public String getName() {
            return name;
        }

        public Supplier<T> getDefaultSupplier() {
            return defaultSupplier;
        }

        public Class<T> getConfigClass() {
            return configClass;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

    /**
     * A builder for creating config manager instances.
     */
    public static class Builder {
        private String modId;

        public Builder modId(String modId) {
            this.modId = modId;
            return this;
        }

        public ConfigManager build() {
            if (modId == null || modId.isEmpty()) {
                throw new IllegalStateException("Mod ID must be specified");
            }
            return new ConfigManager(modId);
        }
    }
}