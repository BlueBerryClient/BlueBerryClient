package xyz.blackdev.Blueberry.features.gui.overlay.impl;

import me.x150.renderer.font.FontRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import xyz.blackdev.Blueberry.utils.drawing.RenderUtils;
import xyz.blackdev.Blueberry.features.gui.overlay.Overlay;

public class FPS extends Overlay {


    public FPS() {
        super("FPS");
    }

    @Override
    public void render(DrawContext drawContext, FontRenderer font) {
        RenderUtils.drawtext("FPS: " + MinecraftClient.getInstance().getCurrentFps(), 10,10,1,1,1, 1);
    }

    @Override
    public void tick() {

    }
}
