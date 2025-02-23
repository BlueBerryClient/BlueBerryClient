package xyz.blackdev.Blueberry.notification;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NotificationManager {
   @NotNull
   public static final List<Notification> pendingNotifications = new ArrayList();
   @Nullable
   private static Notification currentNotification = null;

   public static void update() {
      if (currentNotification != null && !currentNotification.isNotificationShown()) {
         currentNotification = null;
      }

      if (currentNotification == null && !pendingNotifications.isEmpty()) {
         currentNotification = (Notification)pendingNotifications.removeLast();
         currentNotification.showNotification();
      }

   }

   public static void show(Notification notification) {
      pendingNotifications.add(notification);
   }

   public static void renderNotifications(DrawContext context, RenderTickCounter tickCounter) {
      update();
      if (currentNotification != null) {
         currentNotification.renderNotification(context);
      }

   }


   private NotificationManager() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
