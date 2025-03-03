package xyz.blackdev.Blueberry.events;

public class EventCancelable extends Event {
    boolean canceled = false;

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
