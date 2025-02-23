package xyz.blackdev.Blueberry.mixin.renderers;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.blackdev.Blueberry.Blueberry;


import java.awt.*;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin implements AutoCloseable{

    MinecraftClient client;

    @Inject(method = "render", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/hud/InGameHud;render(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"))
    public void render(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci, @Local DrawContext context) {
        Blueberry.instance().system().render(context);
    }

}
