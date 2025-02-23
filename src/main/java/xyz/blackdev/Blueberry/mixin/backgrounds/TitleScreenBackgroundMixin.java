package xyz.blackdev.Blueberry.mixin.backgrounds;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import xyz.blackdev.Blueberry.Blueberry;

import java.util.function.Function;

@Mixin(TitleScreen.class)
public abstract class TitleScreenBackgroundMixin extends Screen{

    Identifier bg = Identifier.of(Blueberry.defaultdir, "textures/gui/background.png");
    Function<Identifier, RenderLayer> renderLayerFunction = identifier -> RenderLayer.getGuiOpaqueTexturedBackground(bg);

    protected TitleScreenBackgroundMixin(Text title) {
        super(title);

    }

    /**
     * @author BlackDev
     * @reason Rendering background speratly because TitleScreen is fucking special
     */
    @Overwrite
    public void renderPanoramaBackground(DrawContext context, float delta) {
        context.drawTexture(renderLayerFunction, bg, -21 / 90, -1 / 90, 0.0f, 0.0f, this.width + 100, this.height + 100, (this.width + 41), (this.height + 40));

    }


}
