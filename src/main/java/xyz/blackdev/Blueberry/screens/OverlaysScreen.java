package xyz.blackdev.Blueberry.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import xyz.blackdev.Blueberry.features.gui.overlay.Overlay;
import xyz.blackdev.Blueberry.features.gui.overlay.OverlayInitializer;

import java.awt.*;

public class OverlaysScreen extends Screen {

    @Nullable
    private Overlay movingOverlay = null;

    private double mouseOffsetX = 0;
    private double mouseOffsetY = 0;

    private final Screen last;


    public OverlaysScreen(Screen last) {
        super(Text.empty());
        this.last = last;
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (button != 0 && button != 1 && button != 2) return super.mouseClicked(x, y, button);

        for (Overlay overlay : OverlayInitializer.overlayManager.entries()) {
            if (x < overlay.getX()) continue;
            if (y < overlay.getY()) continue;
            if (x > overlay.getX() + overlay.width(textRenderer)) continue;
            if (y > overlay.getY() + overlay.height(textRenderer)) continue;

            if (button == 1) {
                overlay.setActive(!overlay.isActive());
                break;
            }

            mouseOffsetX = overlay.getX() - x;
            mouseOffsetY = overlay.getY() - y;

            movingOverlay = overlay;
            break;
        }

        return super.mouseClicked(x, y, button);
    }

    @Override
    public boolean mouseReleased(double x, double y, int button) {
        if (button == 0) {
            mouseOffsetX = mouseOffsetY = 0;
            movingOverlay = null;
        }
        return super.mouseReleased(x, y, button);
    }

    @Override
    public void render(DrawContext graphics, int mouseX, int mouseY, float deltaFrameTime) {
        this.renderBackground(graphics, mouseX, mouseY, deltaFrameTime);
        super.render(graphics, mouseX, mouseY, deltaFrameTime);
        OverlayInitializer.overlayManager.render(graphics);

        for (Overlay overlay : OverlayInitializer.overlayManager.entries()) {
            graphics.drawBorder(overlay.getX() - 2, overlay.getY() - 2, overlay.width(textRenderer) + 2, overlay.height(textRenderer) + 2,
                    overlay.isActive() ? Color.WHITE.getRGB() : Color.DARK_GRAY.getRGB());
        }
    }

    @Override
    public boolean mouseDragged(double x, double y, int button, double distanceX, double distanceY) {
        if (movingOverlay == null) return super.mouseDragged(x, y, button, distanceX, distanceY);

        movingOverlay.setX((int) (x + mouseOffsetX));
        movingOverlay.setY((int) (y + mouseOffsetY));

        return super.mouseDragged(x, y, button, distanceX, distanceY);
    }

    @Override
    protected void renderDarkening(DrawContext context) {
        super.renderDarkening(context);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {

    }
}
