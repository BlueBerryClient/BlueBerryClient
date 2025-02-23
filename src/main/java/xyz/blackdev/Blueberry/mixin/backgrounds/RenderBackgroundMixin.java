package xyz.blackdev.Blueberry.mixin.backgrounds;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import xyz.blackdev.Blueberry.Blueberry;

import java.util.function.Function;

@Mixin(Screen.class)
public class RenderBackgroundMixin {

    @Shadow public int width;
    @Shadow public int height;
    Identifier bg = Identifier.of(Blueberry.defaultdir, "textures/gui/background.png");
    Function<Identifier, RenderLayer> renderLayerFunction = identifier -> RenderLayer.getGuiOpaqueTexturedBackground(bg);

    /**
     * @author BlackDev
     * @reason Change the background
     */
    @Overwrite
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTexture(renderLayerFunction, bg, -21 / 90, -1 / 90, 0.0f, 0.0f, this.width + 100, this.height + 100, (this.width + 41), (this.height + 40));
    }

    /**
     *
     * @author BlackDev
     * @reason Change the panorama background
     */
    @Overwrite
    public void renderPanoramaBackground(DrawContext context, float delta) {
        context.drawTexture(renderLayerFunction, bg, -21 / 90, -1 / 90, 0.0f, 0.0f, this.width + 100, this.height + 100, (this.width + 41), (this.height + 40));
    }}