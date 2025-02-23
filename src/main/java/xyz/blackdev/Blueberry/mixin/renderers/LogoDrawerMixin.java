package xyz.blackdev.Blueberry.mixin.renderers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import xyz.blackdev.Blueberry.Blueberry;

@Environment(EnvType.CLIENT)
@Mixin(LogoDrawer.class)
public class LogoDrawerMixin {

    private final boolean ignoreAlpha;
    private final boolean minceraft = (double) Random.create().nextFloat() < 1.0E-4;

    public LogoDrawerMixin(boolean ignoreAlpha) {
        this.ignoreAlpha = ignoreAlpha;
    }

    /**
     * @author BlackDev
     * @reason Change the logo
     */
    @Overwrite
    public void draw(DrawContext context, int screenWidth, float alpha, int y) {
        int i = screenWidth / 2 - 128;
        float f = this.ignoreAlpha ? 1.0F : alpha;
        int j = ColorHelper.getWhite(f);
        Identifier LogoTexture = Identifier.of(Blueberry.defaultdir, "textures/logos/blueberry-small-title.png");
        Identifier idkwhytheyneeditbuttheydo = Identifier.of(Blueberry.defaultdir, "textures/logos/blueberry-small-title.png");
        context.drawTexture(RenderLayer::getGuiTextured, this.minceraft ? LogoTexture : idkwhytheyneeditbuttheydo, i, y, 0.0F, 0.0F, 256, 44, 256, 64, j);
    }
}
