package xyz.blackdev.Blueberry.mixin.guicomponents.widgets;

import java.awt.Color;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import xyz.blackdev.Blueberry.utils.RenderUtils;

@Mixin({SliderWidget.class})
public class MixinSliderWidget extends ClickableWidget  {

   protected double value;

   public MixinSliderWidget(int x, int y, int width, int height, Text message) {
      super(x, y, width, height, message);
   }

   public void appendClickableNarrations(NarrationMessageBuilder builder) {
   }

   @Overwrite
   public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
      int colBlack = (new Color(0, 0, 0, 175)).getRGB();
      int colBorderBlack = (new Color(0, 0, 0)).getRGB();
      RenderUtils.renderBorderedRect(context, this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), colBlack, colBorderBlack);
      int colorWhite = (new Color(200, 200, 200, 175)).getRGB();
      int colorWhiteBorder = (new Color(200, 200, 200, 255)).getRGB();
      int sliderXPos = (int)(this.value * (double)(this.width - 8));
      context.fill(this.getX() + sliderXPos + 8, this.getY(), this.getX() + sliderXPos, this.getY() + this.getHeight(), colorWhite);
      context.fill(this.getX() + sliderXPos - 1, this.getY(), this.getX() + sliderXPos, this.getY() + this.getHeight(), colorWhiteBorder);
      context.fill(this.getX() + sliderXPos + 8, this.getY(), this.getX() + sliderXPos + 9, this.getY() + this.getHeight(), colorWhiteBorder);
      context.fill(this.getX() + sliderXPos + 8, this.getY() - 1, this.getX() + sliderXPos, this.getY(), colorWhiteBorder);
      context.fill(this.getX() + sliderXPos + 8, this.getY() + this.getHeight(), this.getX() + sliderXPos, this.getY() + this.getHeight() + 1, colorWhiteBorder);
      this.drawScrollableText(context, MinecraftClient.getInstance().textRenderer, 2, this.active ? 16777215 : 10526880 | MathHelper.ceil(this.alpha * 255.0F) << 24);
   }
}
