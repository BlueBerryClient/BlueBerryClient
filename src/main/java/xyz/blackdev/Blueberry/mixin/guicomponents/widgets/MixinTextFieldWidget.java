package xyz.blackdev.Blueberry.mixin.guicomponents.widgets;

import java.awt.Color;
import java.util.function.BiFunction;
import java.util.function.Function;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.blackdev.Blueberry.utils.RenderUtils;

@Mixin({TextFieldWidget.class})
public abstract class MixinTextFieldWidget extends ClickableWidget implements Drawable {

   private int firstCharacterIndex;

   private BiFunction<String, Integer, OrderedText> renderTextProvider;

   public MixinTextFieldWidget(int x, int y, int width, int height, Text message) {
      super(x, y, width, height, message);
   }

   @Shadow
   public abstract boolean drawsBackground();

   @Redirect(
      method = {"renderWidget"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIII)V"
)
   )
   public void nightly$renderWidget(DrawContext instance, Function<Identifier, RenderLayer> renderLayers, Identifier sprite, int x, int y, int width, int height) {
      if (this.drawsBackground()) {
         RenderUtils.renderBorderedRect(instance, this.getX() - 1, this.getY() - 1, this.getX() + this.width + 1, this.getY() + this.height + 1, (new Color(0, 0, 0, 175)).getRGB(), (new Color(0, 0, 0, 255)).getRGB());
      }

   }
}
