package xyz.blackdev.Blueberry.events;

import java.util.ArrayList;
import java.util.List;

public abstract class Event {
    public void call() {
        List<EventData> eventDataList = EventManager.get(this.getClass());

        for (EventData data : new ArrayList<>(eventDataList)) {
            try {
                data.target().invoke(data.src(), this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
