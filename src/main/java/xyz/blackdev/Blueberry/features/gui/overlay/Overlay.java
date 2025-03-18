package xyz.blackdev.Blueberry.features.gui.overlay;

import me.x150.renderer.font.FontRenderer;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;

public abstract class Overlay {

    private final String name;

    public static void setActive(boolean active) {
        active = active;
    }
    public void toggleActive() {
        setActive(!isActive());
    }


    private static boolean active = false;
    private double x;
    private double y;

    public Overlay(@NotNull String name) {
        this.name = name;
        //tempo
        this.x = 10;
        this.y = 10;
    }

    public abstract void render(DrawContext drawContext, FontRenderer font);
    public abstract void tick();

    public boolean shouldRenderInGui() {
        return false;
    }

    public @NotNull String getName() {
        return name;
    }

    public static boolean isActive() {
        return active;
    }

    public int getX() {
        return (int) Math.round(x);
    }
}
