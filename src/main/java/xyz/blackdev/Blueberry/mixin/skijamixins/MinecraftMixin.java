package xyz.blackdev.Blueberry.mixin.skijamixins;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.blackdev.Blueberry.Blueberry;
import xyz.blackdev.Blueberry.skija.core.SkiaRenderer;

/**
 * MinecraftMixin modifies the Minecraft client behavior to integrate Skija rendering capabilities
 * and handle UI interactions through a custom panel.
 *
 * @author quantamyt
 *
 * This code is released under the Creative Commons Attribution 4.0 International License (CC BY 4.0).
 * You are free to share and adapt this code, provided appropriate credit is given to the original author.
 * For more details, visit: <a href="https://creativecommons.org/licenses/by/4.0/deed.en">Creative Commons</a>
 *
 */
@Mixin(MinecraftClient.class)
public class MinecraftMixin {

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;addResourcePackProfilesToManager(Lnet/minecraft/resource/ResourcePackManager;)V"))
    private void initializeGame(CallbackInfo ci) {
        Blueberry.instance().onPostInit();
    }
    @Inject(method = "onFinishedLoading", at = @At("RETURN"))
    public void startGame$RETURN(CallbackInfo ci) {
        SkiaRenderer.initialize();
        SkiaRenderer.createSurface();
    }
}
