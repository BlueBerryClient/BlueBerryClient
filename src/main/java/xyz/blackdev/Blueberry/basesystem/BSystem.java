package xyz.blackdev.Blueberry.basesystem;

import net.minecraft.client.gui.DrawContext;

public abstract class BSystem {

    protected abstract  void start();
    protected abstract void stop();
    protected abstract void tick();
    protected abstract void render(DrawContext context);

}
