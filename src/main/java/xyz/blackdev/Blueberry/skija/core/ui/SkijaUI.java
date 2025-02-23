package xyz.blackdev.Blueberry.skija.core.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;

/**
 * SkijaUI is an abstract class that serves as a base for creating UI screens
 * using the Skia graphics library. It extends the Minecraft GuiScreen class
 * to provide custom rendering and mouse interaction capabilities.
 *
 * Subclasses must implement the rendering and mouse handling logic.
 *
 * @author quantamyt
 *
 * This code is released under the Creative Commons Attribution 4.0 International License (CC BY 4.0).
 * You are free to share and adapt this code, provided appropriate credit is given to the original author.
 * For more details, visit: <a href="https://creativecommons.org/licenses/by/4.0/deed.en">Creative Commons</a>
 */
public abstract class SkijaUI extends Screen {
    protected SkijaUI(Text title) {
        super(title);
    }

    /**
     * Renders the UI elements at the specified mouse coordinates.
     *
     * @param mouseX The X coordinate of the mouse.
     * @param mouseY The Y coordinate of the mouse.
     */
    public abstract void renderUI(float mouseX, float mouseY);

    /**
     * Handles mouse press events.
     *
     * @param mouseX The X coordinate of the mouse.
     * @param mouseY The Y coordinate of the mouse.
     * @param mouseButton The mouse button that was pressed.
     */
    public abstract void mousePressed(float mouseX, float mouseY, int mouseButton);

    /**
     * Handles mouse release events.
     *
     * @param mouseX The X coordinate of the mouse.
     * @param mouseY The Y coordinate of the mouse.
     */
    public abstract void mouseReleased(float mouseX, float mouseY);

    Window Display = MinecraftClient.getInstance().getWindow();
    Mouse Mouse = MinecraftClient.getInstance().mouse;

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        super.render(context,mouseX, mouseY, partialTicks);
        xyz.blackdev.Blueberry.skija.core.SkiaRenderer.render((canvas) -> this.renderUI((float) Mouse.getX(), (float) ((float) Display.getHeight() - Mouse.getY())));
    }


    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.mousePressed((float) Mouse.getX(), (float) ((float) Display.getHeight() - Mouse.getY()), mouseButton);
    }


    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.mouseReleased((float) Mouse.getX(), (float) ((float) Display.getHeight() - Mouse.getY()));
    }
}
