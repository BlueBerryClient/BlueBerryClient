package xyz.blackdev.Blueberry.features.gui.overlay;

import me.x150.renderer.font.FontRenderer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;
import xyz.blackdev.Blueberry.utils.config.ConfigInitializer;

public abstract class Overlay {

    private final String name;

    public abstract int width(TextRenderer font);

    public abstract int height(TextRenderer font);

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

        this.x = ConfigInitializer.overlayConfig.getOverlay(name).getX();
        this.y = ConfigInitializer.overlayConfig.getOverlay(name).getY();
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
    public int getY() {
        return (int) Math.round(y);
    }

    public void setX(int x) {
        this.x = x;
        ConfigInitializer.overlayConfig.getOverlay(getName()).setX(x);
        ConfigInitializer.configManager.saveConfig("overlay_config");
    }
    public void setY(int y) {
        this.y = y;
        ConfigInitializer.overlayConfig.getOverlay(getName()).setX(y);
        ConfigInitializer.configManager.saveConfig("overlay_config");
    }
}
