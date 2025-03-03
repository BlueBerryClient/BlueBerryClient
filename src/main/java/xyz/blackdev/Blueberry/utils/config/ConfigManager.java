package xyz.blackdev.Blueberry.utils.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Map;

public class ConfigManager {

    private final String configFilePath = "/config.json";
    private Map<String, Object> config;
    private final Gson gson;

    public ConfigManager() {
        this.gson = new Gson();
        loadConfig();
    }

    private void loadConfig() {
        try (InputStream inputStream = getClass().getResourceAsStream(configFilePath);
             Reader reader = new InputStreamReader(inputStream)) {
            Type type = new TypeToken<Map<String, Object>>() {}.getType();
            this.config = gson.fromJson(reader, type);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.json", e);
        }
    }

    public Object getConfigValue(String key) {
        return config.get(key);
    }

    public void addOrUpdateConfigValue(String key, Object value) {
        config.put(key, value);
        saveConfig();
    }

    private void saveConfig() {
        try (Writer writer = new FileWriter("src/main/resources" + configFilePath)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save config.json", e);
        }
    }
}