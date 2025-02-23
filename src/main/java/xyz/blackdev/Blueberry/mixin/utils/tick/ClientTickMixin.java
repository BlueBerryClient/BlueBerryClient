package xyz.blackdev.Blueberry.mixin.utils.tick;


import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.blackdev.Blueberry.Blueberry;

@Mixin(MinecraftClient.class)
public abstract class ClientTickMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        Blueberry.instance().system().tick();
    }

}
