package xyz.blackdev.Blueberry;

import net.minecraft.util.Identifier;
import xyz.blackdev.Blueberry.features.gui.overlay.OverlayInitializer;
import xyz.blackdev.Blueberry.features.module.ModuleManager;
import xyz.blackdev.Blueberry.utils.config.ConfigInitializer;

import java.io.File;

public class Blueberry {

    public static String defaultdir = "blueberry";

    public static String name = "Blueberry" , version = "v1", author = "BlackDev";
    private static final ModuleManager manager  = new ModuleManager();;
    public static Identifier cape;



    public Blueberry() {
    }

    public static Blueberry instance() {
        return ModEntryPoint.getBlueBarryEntryPoint();
    }

    protected void init() {
        ConfigInitializer.init();
        OverlayInitializer.init();
    }

    public static ModuleManager module() {
        return manager;
    }
}
