package xyz.blackdev.Blueberry.features.gui.overlay.impl;

import me.x150.renderer.font.FontRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import xyz.blackdev.Blueberry.utils.config.ConfigInitializer;
import xyz.blackdev.Blueberry.utils.config.ConfigManager;
import xyz.blackdev.Blueberry.utils.config.configs.OverlayConfig;
import xyz.blackdev.Blueberry.utils.drawing.RenderUtils;
import xyz.blackdev.Blueberry.features.gui.overlay.Overlay;

public class FPS extends Overlay {


    public FPS() {
        super("fps");
    }

    public static ConfigManager configManager = ConfigInitializer.configManager;
    public static OverlayConfig overlayConfig = ConfigInitializer.overlayConfig;

    @Override
    public int width(TextRenderer font) {
        return (int) RenderUtils.fr.getTextWidth(Text.literal("FPS: " + MinecraftClient.getInstance().getCurrentFps()));
    }

    @Override
    public int height(TextRenderer font) {
        return (int) RenderUtils.fr.getTextHeight(Text.literal("FPS: " + MinecraftClient.getInstance().getCurrentFps()));
    }

    @Override
    public void render(DrawContext drawContext, FontRenderer font) {
        if (ConfigInitializer.overlayConfig.getOverlay(getName()).isVisible()) {
            RenderUtils.drawtext("FPS: " + MinecraftClient.getInstance().getCurrentFps(), getX(), getY(), 1, 1, 1, 1);
        }
    }

    @Override
    public void tick() {

    }
}
