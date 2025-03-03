package xyz.blackdev.Blueberry.events;

import net.minecraft.client.gui.screen.Screen;

public class GUIEvent extends Event {
    public static Screen screen;
    public GUIEvent(Screen sc) {
        screen = sc;
    }

}
