package xyz.blackdev.Blueberry.events.Events;

import xyz.blackdev.Blueberry.events.Event;
import xyz.blackdev.Blueberry.events.EventManager;
import xyz.blackdev.Blueberry.keys.Key;
import xyz.blackdev.Blueberry.keys.KeyInputRegestry;

public class ScrollUpEvent extends Event {
    public ScrollUpEvent() {
        EventManager.register(this);

        for(Key key : KeyInputRegestry.keys) {
            if(key.isPressed()) {
                key.onScrollUp();
            }
        }
    }

}
