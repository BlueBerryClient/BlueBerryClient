package xyz.blackdev.Blueberry.events.Events.Registry;


import xyz.blackdev.Blueberry.events.EventManager;
import xyz.blackdev.Blueberry.events.Events.TickEvent;

public class EventRegistry {
    public static void register() {
        EventManager.register(new TickEvent());
    }
}
