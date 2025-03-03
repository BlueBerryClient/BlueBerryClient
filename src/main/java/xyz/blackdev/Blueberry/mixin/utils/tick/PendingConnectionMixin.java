package xyz.blackdev.Blueberry.mixin.utils.tick;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.blackdev.Blueberry.events.Events.TickEvent;

@Mixin(MinecraftClient.class)
@Environment(EnvType.CLIENT)
public abstract class PendingConnectionMixin {
    @Inject(at = @At(value = "HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        new TickEvent().call();
    }
}
