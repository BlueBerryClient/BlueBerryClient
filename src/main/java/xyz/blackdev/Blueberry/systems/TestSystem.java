package xyz.blackdev.Blueberry.systems;

import net.minecraft.client.gui.DrawContext;
import xyz.blackdev.Blueberry.basesystem.BSystem;

import java.lang.System;

public class TestSystem extends BSystem {
    @Override
    protected void start() {
        System.out.println("TestSystem started");
    }

    @Override
    protected void stop() {
        System.out.println("TestSystem stopped");
    }

    @Override
    protected void tick() {
        System.out.println("TestSystem ticked");
    }

    @Override
    protected void render(DrawContext context) {
        System.out.println("TestSystem rendered with context: " + context);
    }
}
