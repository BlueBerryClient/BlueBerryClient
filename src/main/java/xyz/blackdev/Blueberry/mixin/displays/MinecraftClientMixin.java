package xyz.blackdev.Blueberry.mixin.displays;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.blackdev.Blueberry.Blueberry;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    /**
     * @author BlackDev
     * @reason Change the window title
     */

    @Overwrite
    private String getWindowTitle() {
        return Blueberry.name + " - " + Blueberry.version + " - " + Blueberry.author;
        }



}
