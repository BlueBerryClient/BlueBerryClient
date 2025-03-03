package xyz.blackdev.Blueberry.mixin.cosmetics.capes;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.blackdev.Blueberry.Blueberry;

@Mixin(AbstractClientPlayerEntity.class)
public class CapeLayerMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/PlayerListEntry;getSkinTextures()Lnet/minecraft/client/util/SkinTextures;"), method = "getSkinTextures")
    public SkinTextures getSkinTextures(PlayerListEntry instance) {
        String id = MinecraftClient.getInstance().getGameProfile().getId().toString();
        SkinTextures skinTextures = instance.getSkinTextures();

        Identifier cape = Blueberry.cape;

        if (instance.getProfile().getId().toString().equals(id)) {
            return new SkinTextures(skinTextures.texture(), skinTextures.textureUrl(), cape, cape, skinTextures.model(), skinTextures.secure());
        }
        return skinTextures;
    }
}