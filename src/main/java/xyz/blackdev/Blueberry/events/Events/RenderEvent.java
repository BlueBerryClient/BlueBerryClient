package xyz.blackdev.Blueberry.events.Events;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;
import xyz.blackdev.Blueberry.events.Event;

public class RenderEvent extends Event {
    public DrawContext ctx;
    public MinecraftClient mc;

    public RenderEvent(DrawContext ctx, MinecraftClient mc) {
        this.ctx = ctx;
        this.mc = mc;
    }

    public @NotNull DrawContext getContext() {
        if(ctx == null) {
            return null;
        }
        return ctx;
    }

    public MinecraftClient getMinecraft() {
        return mc;
    }
}
