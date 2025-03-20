package xyz.blackdev.Blueberry.utils.config;

import xyz.blackdev.Blueberry.utils.config.configs.OverlayConfig;
import xyz.blackdev.Blueberry.utils.config.configs.DefaultConfig;

public class ConfigInitializer {

    public static ConfigManager configManager = new ConfigManager("blueberry");

    public static OverlayConfig overlayConfig;
    public static DefaultConfig defaultConfig;

    public static void init() {
        overlayConfig = configManager.register("overlay_config", OverlayConfig::new, OverlayConfig.class);
        defaultConfig = configManager.register("default_config", DefaultConfig::new, DefaultConfig.class);

        configManager.saveConfig("overlay_config");
        configManager.saveConfig("default_config");
    }
}