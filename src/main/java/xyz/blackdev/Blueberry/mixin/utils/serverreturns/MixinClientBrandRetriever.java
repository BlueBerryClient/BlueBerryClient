package xyz.blackdev.Blueberry.mixin.utils.serverreturns;

import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.blackdev.Blueberry.Blueberry;

@Mixin(value = ClientBrandRetriever.class, remap = false)
public abstract class MixinClientBrandRetriever {

    @Unique
    private static final String BRANDING_VANILLA = "vanilla";
    @Unique
    private static final String BRANDING_BMC = Blueberry.name + " | " + Blueberry.version + " | " + Blueberry.author;

    @Inject(method = "getClientModName", at = @At("RETURN"), cancellable = true)
    private static void appendBMCBranding(CallbackInfoReturnable<String> CBackInfoReturnable)
    {
        String branding = CBackInfoReturnable.getReturnValue();
        if (MixinClientBrandRetriever.BRANDING_VANILLA.equals(branding))
        {
            CBackInfoReturnable.setReturnValue(MixinClientBrandRetriever.BRANDING_BMC);
        }
        else
        {
            CBackInfoReturnable.setReturnValue(MixinClientBrandRetriever.BRANDING_BMC);
        }
    }
}