package xyz.blackdev.Blueberry.events.Events;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import xyz.blackdev.Blueberry.events.Event;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÇ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/events/GameRenderEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "<init>", "()V", "liquidbounce"}
)
public final class GameRenderEvent extends xyz.blackdev.Blueberry.events.Event {
   @NotNull
   public static final GameRenderEvent INSTANCE = new GameRenderEvent();

   private GameRenderEvent() {
   }
}
