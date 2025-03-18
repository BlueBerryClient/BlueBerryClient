package xyz.blackdev.Blueberry;

import net.minecraft.util.Identifier;
import xyz.blackdev.Blueberry.features.gui.overlay.OverlayManager;
import xyz.blackdev.Blueberry.features.gui.overlay.impl.FPS;
import xyz.blackdev.Blueberry.features.module.ModuleManager;
import xyz.blackdev.Blueberry.utils.config.ConfigManager;
import xyz.blackdev.Blueberry.utils.config.configs.MainConfig;

public class Blueberry {

    public static String defaultdir = "blueberry";

    public static String name = "Blueberry" , version = "v1", author = "BlackDev";
    private final ModuleManager manager;

    public static Identifier cape;

    public Blueberry() {
        this.manager = new ModuleManager();
    }

    public static Blueberry instance() {
        return ModEntryPoint.getBlueBarryEntryPoint();
    }

    protected void init() {
        OverlayManager overlayManager = new OverlayManager();


         this.module().register(overlayManager);

         overlayManager.register(new FPS());
         overlayManager.setActive(FPS.class,true);

    }

    public ModuleManager module() {
        return manager;
    }
}
