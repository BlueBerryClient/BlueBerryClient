package xyz.blackdev.Blueberry.mixin.player;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @Inject(
            at = @At("HEAD"),
            method = "hasLabel(Lnet/minecraft/entity/Entity;D)Z",
            cancellable = true
    )
    private void viewOwnLabel(Entity entity, double squaredDistanceToCamera, CallbackInfoReturnable<Boolean> cir) {
        if (entity == MinecraftClient.getInstance().player) cir.setReturnValue(MinecraftClient.isHudEnabled());
    }
}