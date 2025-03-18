package xyz.blackdev.Blueberry.features.module;

import net.minecraft.client.gui.DrawContext;

public interface Module {

    default void start() {

    }

    default void stop() {

    }

    void tick();
      void render(DrawContext context);

}
