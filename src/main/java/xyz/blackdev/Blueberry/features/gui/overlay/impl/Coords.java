package xyz.blackdev.Blueberry.features.gui.overlay.impl;

import me.x150.renderer.font.FontRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import xyz.blackdev.Blueberry.features.gui.overlay.Overlay;
import xyz.blackdev.Blueberry.features.gui.overlay.OverlayInitializer;
import xyz.blackdev.Blueberry.utils.config.ConfigInitializer;
import xyz.blackdev.Blueberry.utils.config.configs.OverlayConfig;
import xyz.blackdev.Blueberry.utils.drawing.RenderUtils;

public class Coords extends Overlay {

    public Coords() {
        super("coords");
    }

    public static OverlayConfig overlayConfig = ConfigInitializer.overlayConfig;

    @Override
    public int width(TextRenderer font) {
        assert MinecraftClient.getInstance().player != null;
        return (int) RenderUtils.fr.getTextWidth(Text.literal("X: " + MinecraftClient.getInstance().player.getBlockX() + " Y: " + MinecraftClient.getInstance().player.getBlockY() + " Z: " + MinecraftClient.getInstance().player.getBlockZ()));
    }

    @Override
    public int height(TextRenderer font) {
        assert MinecraftClient.getInstance().player != null;
        return (int) RenderUtils.fr.getTextHeight(Text.literal("X: " + MinecraftClient.getInstance().player.getBlockX() + " Y: " + MinecraftClient.getInstance().player.getBlockY() + " Z: " + MinecraftClient.getInstance().player.getBlockZ()));
    }

    @Override
    public void render(DrawContext drawContext, FontRenderer font) {
        assert MinecraftClient.getInstance().player != null;
        if (ConfigInitializer.overlayConfig.getOverlay(getName()).isVisible()) {
            RenderUtils.drawtext("X: " + MinecraftClient.getInstance().player.getBlockX() + " Y: " + MinecraftClient.getInstance().player.getBlockY() + " Z: " + MinecraftClient.getInstance().player.getBlockZ(), getX(), getY(), 1, 1, 1, 1);
        }
    }

    @Override
    public void tick() {

    }
}
