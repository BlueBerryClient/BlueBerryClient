package xyz.blackdev.Blueberry;

import net.minecraft.util.Identifier;
import xyz.blackdev.Blueberry.basesystem.SystemManager;
import xyz.blackdev.Blueberry.utils.config.ConfigManager;

public class Blueberry {

    public static String defaultdir = "blueberry";

    public static String name = "Blueberry" , version = "v1", author = "BlackDev";
    private final SystemManager manager;

    public static Identifier cape;

    public Blueberry() {
        this.manager = new SystemManager();
    }

    public static Blueberry instance() {
        return ModEntryPoint.getBlueBarryEntryPoint();
    }
    ConfigManager configManager = new ConfigManager();
    protected void init() {
         cape = Identifier.of(defaultdir,configManager.getConfigValue("CAPETEXTURE").toString());
    }

    public SystemManager system() {
        return manager;
    }
}
