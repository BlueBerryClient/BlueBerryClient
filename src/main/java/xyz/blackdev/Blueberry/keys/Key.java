package xyz.blackdev.Blueberry.keys;

public interface Key {
    int getKey();
    void pressAction();
    void holdAction();
    default String getName() {
        return "BlueBerry";
    }
    default void onScrollUp() {

    }
    default void onScrollDown() {

    }
    boolean isPressed();
    void setPressed(boolean pressed);
    void releaseAction();
}
