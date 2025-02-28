package xyz.blackdev.Blueberry.mixin.guicomponents.hotbar;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import xyz.blackdev.Blueberry.utils.RenderUtils;

//@Mixin(InGameHud.class)
public class InGameHudMixin {

    private static final Identifier HOTBAR_ATTACK_INDICATOR_BACKGROUND_TEXTURE = Identifier.ofVanilla("hud/hotbar_attack_indicator_background");
    private static final Identifier HOTBAR_ATTACK_INDICATOR_PROGRESS_TEXTURE = Identifier.ofVanilla("hud/hotbar_attack_indicator_progress");


    @Nullable
    private PlayerEntity getCameraPlayer() {
        Entity var2 = MinecraftClient.getInstance().getCameraEntity();
        PlayerEntity var10000;
        if (var2 instanceof PlayerEntity playerEntity) {
            var10000 = playerEntity;
        } else {
            var10000 = null;
        }

        return var10000;
    }
    private void renderHotbarItem(DrawContext context, int x, int y, RenderTickCounter tickCounter, PlayerEntity player, ItemStack stack, int seed) {
        if (!stack.isEmpty()) {
            float f = (float)stack.getBobbingAnimationTime() - tickCounter.getTickDelta(false);
            if (f > 0.0F) {
                float g = 1.0F + f / 5.0F;
                context.getMatrices().push();
                context.getMatrices().translate((float)(x + 8), (float)(y + 12), 0.0F);
                context.getMatrices().scale(1.0F / g, (g + 1.0F) / 2.0F, 1.0F);
                context.getMatrices().translate((float)(-(x + 8)), (float)(-(y + 12)), 0.0F);
            }

            context.drawItem(player, stack, x, y, seed);
            if (f > 0.0F) {
                context.getMatrices().pop();
            }

            context.drawStackOverlay(MinecraftClient.getInstance().textRenderer, stack, x, y);
        }
    }

    /**
     * @author
     * @reason
     */
    //@Overwrite
    private void renderHotbar(DrawContext context, RenderTickCounter tickCounter) {
        PlayerEntity playerEntity = this.getCameraPlayer();
        if (playerEntity != null) {
            ItemStack itemStack = playerEntity.getOffHandStack();
            Arm arm = playerEntity.getMainArm().getOpposite();
            int i = context.getScaledWindowWidth() / 2;
            int j = 182;
            int k = 91;
            context.getMatrices().push();
            context.getMatrices().translate(0.0F, 0.0F, -90.0F);
            //context.drawGuiTexture(RenderLayer::getGuiTextured, HOTBAR_TEXTURE, i - 91, context.getScaledWindowHeight() - 22, 182, 22);
            //context.drawGuiTexture(RenderLayer::getGuiTextured, HOTBAR_SELECTION_TEXTURE, i - 91 - 1 + playerEntity.getInventory().selectedSlot * 20, context.getScaledWindowHeight() - 22 - 1, 24, 23);
            //RenderUtils.renderBorderedRect(context,i - 91, context.getScaledWindowHeight() - 22, 182, 22,0x80000000,0xFF000000);
            //RenderUtils.renderBorderedRect(context, i - 91 - 1 + playerEntity.getInventory().selectedSlot * 20, context.getScaledWindowHeight() - 22 - 1, 24, 23,0x80000000,0xFF000000);


            if (!itemStack.isEmpty()) {
                if (arm == Arm.LEFT) {
                    //context.drawGuiTexture(RenderLayer::getGuiTextured, HOTBAR_OFFHAND_LEFT_TEXTURE, i - 91 - 29, context.getScaledWindowHeight() - 23, 29, 24);
                    //RenderUtils.renderBorderedRect(context,i - 91 - 29, context.getScaledWindowHeight() - 23, 29, 24,0x80000000,0xFF000000);
                } else {
                    //context.drawGuiTexture(RenderLayer::getGuiTextured, HOTBAR_OFFHAND_RIGHT_TEXTURE, i + 91, context.getScaledWindowHeight() - 23, 29, 24);
                    //RenderUtils.renderBorderedRect(context,i + 91, context.getScaledWindowHeight() - 23, 29, 24,0x80000000,0xFF000000);
                }
            }

            context.getMatrices().pop();
            int l = 1;

            for(int m = 0; m < 9; ++m) {
                int n = i - 90 + m * 20 + 2;
                int o = context.getScaledWindowHeight() - 16 - 3;
                this.renderHotbarItem(context, n, o, tickCounter, playerEntity, (ItemStack)playerEntity.getInventory().main.get(m), l++);
            }

            if (!itemStack.isEmpty()) {
                int m = context.getScaledWindowHeight() - 16 - 3;
                if (arm == Arm.LEFT) {
                    this.renderHotbarItem(context, i - 91 - 26, m, tickCounter, playerEntity, itemStack, l++);
                } else {
                    this.renderHotbarItem(context, i + 91 + 10, m, tickCounter, playerEntity, itemStack, l++);
                }
            }

            if (MinecraftClient.getInstance().options.getAttackIndicator().getValue() == AttackIndicator.HOTBAR) {
                float f = MinecraftClient.getInstance().player.getAttackCooldownProgress(0.0F);
                if (f < 1.0F) {
                    int n = context.getScaledWindowHeight() - 20;
                    int o = i + 91 + 6;
                    if (arm == Arm.RIGHT) {
                        o = i - 91 - 22;
                    }

                    int p = (int)(f * 19.0F);
                    context.drawGuiTexture(RenderLayer::getGuiTextured, HOTBAR_ATTACK_INDICATOR_BACKGROUND_TEXTURE, o, n, 18, 18);
                    context.drawGuiTexture(RenderLayer::getGuiTextured, HOTBAR_ATTACK_INDICATOR_PROGRESS_TEXTURE, 18, 18, 0, 18 - p, o, n + 18 - p, 18, p);
                }
            }

        }
    }

}
