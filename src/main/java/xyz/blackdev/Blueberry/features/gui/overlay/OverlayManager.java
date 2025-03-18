package xyz.blackdev.Blueberry.features.gui.overlay;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import xyz.blackdev.Blueberry.features.module.Module;
import xyz.blackdev.Blueberry.utils.Registry;
import xyz.blackdev.Blueberry.utils.drawing.RenderUtils;

public class OverlayManager extends Registry<Overlay> implements Module {

    public void setActive(Overlay overlay,boolean active) {
        setActive(overlay.getClass(),active);
    }

    public void setActive(Class<? extends Overlay> overlay,boolean active ) {
        get(overlay).setActive(active);
    }

    public void toggleActive(Overlay overlay ) {
        toggleActive(overlay.getClass());
    }

    public void toggleActive(Class<? extends Overlay> overlay  ) {
        get(overlay).toggleActive();
    }

    public boolean isActive(Overlay overlay) {
        return isActive(overlay.getClass());
    }

    public boolean isActive(Class<? extends Overlay> overlay) {
        return get(overlay).isActive();
    }

    public Overlay get(Class<? extends Overlay> overlay) {
        return entries().stream().filter(overlay::isInstance).findFirst().orElseThrow();
    }

    @Override
    public void tick() {
        entries().forEach(overlay -> {
            if (overlay.isActive()) {
                overlay.tick();
            }
        });
    }
    @Override
    public void render(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!client.options.hudHidden) {
            entries().forEach(overlay -> {
                if (overlay.isActive() && overlay.shouldRenderInGui() || client.world != null) {
                    overlay.render(context, RenderUtils.fr);
                }
            });
        }
    }
}
