package xyz.blackdev.Blueberry.mixin.guicomponents.chat;

import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ChatHud.class})
public class ChatHistoryMixin {
   @Inject(
      at = {@At("HEAD")},
      method = {"clear"},
      cancellable = true
   )
   public void clear(boolean clearHistory, CallbackInfo ci) {
      if (clearHistory) {
         ci.cancel();
      }

   }
}
