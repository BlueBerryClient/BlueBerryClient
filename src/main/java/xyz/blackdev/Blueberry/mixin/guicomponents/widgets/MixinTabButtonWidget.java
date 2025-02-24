package xyz.blackdev.Blueberry.mixin.guicomponents.widgets;

import java.awt.Color;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TabButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({TabButtonWidget.class})
public abstract class MixinTabButtonWidget extends ClickableWidget {
   @Final
   private Tab tab;

   public MixinTabButtonWidget(int x, int y, int width, int height, Text message) {
      super(x, y, width, height, message);
   }

   @Shadow
   public abstract Tab getTab();

   @Shadow
   public abstract boolean isCurrentTab();

   @Shadow
   protected abstract void renderBackgroundTexture(DrawContext var1, int var2, int var3, int var4, int var5);

   /**
    * @author BlackDev
    * @reason Fixing TabButtonWidget rendering
    */
   @Overwrite
   public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
      TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
      int i = this.active ? -1 : -6250336;
      this.drawMessage(context, textRenderer, i);
      if (this.isCurrentTab()) {
         this.drawMessage(context, textRenderer, Color.white.getRGB());
         this.drawCurrentTabLine(context, textRenderer, Color.white.getRGB());
      }

   }

   /**
    * @author BlackDev
    * @reason Change the button rendering
    */
   @Overwrite
   public void drawMessage(DrawContext context, TextRenderer textRenderer, int color) {
      int i = this.getX() + 1;
      int j = this.getY();
      int k = this.getX() + this.getWidth() - 1;
      int l = this.getY() + this.getHeight();
      drawScrollableText(context, textRenderer, this.getMessage(), i, j, k, l, color);
   }

   /**
    * @author BlackDev
    * @reason Change the button rendering
    */
   @Overwrite
   private void drawCurrentTabLine(DrawContext context, TextRenderer textRenderer, int color) {
      int i = Math.min(textRenderer.getWidth(this.getMessage()), this.getWidth() - 4);
      int j = this.getX() + (this.getWidth() - i) / 2;
      int k = this.getY() + this.getHeight() - 2;
      context.fill(j, k, j + i, k + 2, color);
   }
}
