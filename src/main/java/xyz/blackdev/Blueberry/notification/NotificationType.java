package xyz.blackdev.Blueberry.notification;

import java.awt.Color;

public enum NotificationType {
   INFORMATION(new Color(50, 200, 25)),
   WARNING(new Color(255, 150, 0)),
   CRITICAL(new Color(255, 0, 0));

   final Color color;


   public Color getColor() {
      return this.color;
   }


   private NotificationType(final Color color) {
      this.color = color;
   }

   // $FF: synthetic method
   private static NotificationType[] $values() {
      return new NotificationType[]{INFORMATION, WARNING, CRITICAL};
   }
}
