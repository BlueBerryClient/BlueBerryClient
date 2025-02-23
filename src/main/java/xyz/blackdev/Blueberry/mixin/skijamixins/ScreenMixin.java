package xyz.blackdev.Blueberry.mixin.skijamixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.blackdev.Blueberry.skija.core.SkiaRenderer;

@Mixin(Screen.class)
public class ScreenMixin {

    @Inject(method = "resize", at = @At("RETURN"))
    public void resize(MinecraftClient client, int width, int height, CallbackInfo ci) {
        SkiaRenderer.onResize(width, height); // Adjust Skia rendering surface to new window size
    }
}
