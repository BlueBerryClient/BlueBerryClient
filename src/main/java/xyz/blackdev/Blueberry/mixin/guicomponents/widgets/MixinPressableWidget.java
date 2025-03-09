package xyz.blackdev.Blueberry.mixin.guicomponents.widgets;

import java.awt.Color;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import xyz.blackdev.Blueberry.utils.drawing.RenderUtils;

@Mixin({PressableWidget.class})
public abstract class MixinPressableWidget extends ClickableWidget {
   @Shadow public abstract void drawMessage(DrawContext context, TextRenderer textRenderer, int color);

   public MixinPressableWidget(int x, int y, int width, int height, Text message) {
      super(x, y, width, height, message);
   }

   /**
    * @author BlackDev
    * @reason Change the button rendering
    */
   @Overwrite
   public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
      int color = this.isHovered() ? (new Color(218, 218, 218, 175)).getRGB() : (new Color(0, 0, 0, 175)).getRGB();
      int borderColor = this.isHovered() ? (new Color(255, 255, 255, 200)).getRGB() : (new Color(0, 0, 0, 255)).getRGB();
      int finalColor = this.active ? color : (new Color(0, 0, 0, 175)).getRGB();
      int finalBorderColor = this.active ? borderColor : (new Color(0, 0, 0, 255)).getRGB();
      RenderUtils.renderBorderedRect(context, this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), finalColor, finalBorderColor);
      this.drawMessage(context, MinecraftClient.getInstance().textRenderer, this.active ? 16777215 : 10526880 | MathHelper.ceil(this.alpha * 255.0F) << 24);
   }
}
