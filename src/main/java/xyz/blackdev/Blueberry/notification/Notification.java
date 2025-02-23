package xyz.blackdev.Blueberry.notification;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import java.awt.Color;

public class Notification {
   private long start;
   private final NotificationType type;
   private final String title;
   private final String message;
   private final long end;
   private final long fadeIn;
   private final long fadeOut;

   MinecraftClient minecraft = MinecraftClient.getInstance();
   TextRenderer textRenderer = minecraft.textRenderer;

   public Notification(NotificationType type, String title, String message, int length) {
      this.type = type;
      this.title = title;
      this.message = message;
      this.fadeIn = 200L * (long)length;
      this.fadeOut = this.fadeIn + 500L * (long)length;
      this.end = this.fadeOut + this.fadeIn;
   }

   public void renderNotification(DrawContext context) {
      int NOTIFICATION_WIDTH = 120;
      int NOTIFICATION_HEIGHT = 25;
      double animationOffset;
      if (this.getNotificationTime() < this.fadeIn) {
         animationOffset = Math.tanh((double)this.getNotificationTime() / (double)this.fadeIn * 3.0D) * (double)NOTIFICATION_WIDTH;
      } else if (this.getNotificationTime() > this.fadeOut) {
         animationOffset = Math.tanh(3.0D - (double)(System.currentTimeMillis() - this.start - this.fadeOut) / (double)(this.end - this.fadeOut) * 3.0D) * (double)NOTIFICATION_WIDTH;
      } else {
         animationOffset = (double)NOTIFICATION_WIDTH;
      }

      context.fill((int)((double)minecraft.getWindow().getScaledWidth() - animationOffset), minecraft.getWindow().getScaledHeight() - 5 - NOTIFICATION_HEIGHT, minecraft.getWindow().getScaledWidth(), minecraft.getWindow().getScaledHeight() - 5, Color.BLACK.getRGB());
      context.fill((int)((double)minecraft.getWindow().getScaledWidth() - animationOffset), minecraft.getWindow().getScaledHeight() - 5 - NOTIFICATION_HEIGHT, (int)((double)minecraft.getWindow().getScaledWidth() - animationOffset + 2.0D), minecraft.getWindow().getScaledHeight() - 5, this.type.color.getRGB());
      context.drawText(textRenderer, this.title, (int)((double)minecraft.getWindow().getScaledWidth() - animationOffset + 8.0D), minecraft.getWindow().getScaledHeight() - 2 - NOTIFICATION_HEIGHT, -1, true);
      context.drawText(textRenderer, this.message, (int)((double)minecraft.getWindow().getScaledWidth() - animationOffset + 8.0D), minecraft.getWindow().getScaledHeight() - 15, -1, true);
   }

   public void showNotification() {
      this.start = System.currentTimeMillis();
   }

   public boolean isNotificationShown() {
      return this.getNotificationTime() <= this.end;
   }

   private long getNotificationTime() {
      return System.currentTimeMillis() - this.start;
   }
}
