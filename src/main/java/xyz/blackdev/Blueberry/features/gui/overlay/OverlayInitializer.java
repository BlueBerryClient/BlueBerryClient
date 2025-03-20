package xyz.blackdev.Blueberry.features.gui.overlay;

import xyz.blackdev.Blueberry.Blueberry;
import xyz.blackdev.Blueberry.features.gui.overlay.impl.Coords;
import xyz.blackdev.Blueberry.features.gui.overlay.impl.FPS;

public class OverlayInitializer {

    public static OverlayManager overlayManager = new OverlayManager();

    public static void init() {
        Blueberry.module().register(overlayManager);

        overlayManager.register(new FPS());
        overlayManager.setActive(FPS.class,true);

        overlayManager.register(new Coords());
        overlayManager.setActive(Coords.class,true);
    }
}
