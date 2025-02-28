package xyz.blackdev.Blueberry.keys;

public interface Key {

    int getkey();

    String getName();

    void releaseaction();

    void pressaction();

    void holdaction();

    default void onscrollup() {

    }

    default void onscrolldown() {

    }

}
