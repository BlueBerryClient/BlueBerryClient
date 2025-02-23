package xyz.blackdev.Blueberry.skija.front;



import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import xyz.blackdev.Blueberry.skija.core.SkiaHelper;
import xyz.blackdev.Blueberry.skija.core.ui.SkijaUI;
import xyz.blackdev.Blueberry.skija.core.ui.util.UIWindow;
import xyz.blackdev.Blueberry.skija.core.ui.util.UIWindowBuilder;
import xyz.blackdev.Blueberry.skija.core.util.font.FontUtils;

import java.awt.*;

import static java.awt.Color.black;

/**
 * Panel represents a UI panel that is rendered using the Skija graphics library.
 * It displays a window with a shadow and a gradient background, along with centered text.
 *
 * @author quantamyt
 *
 * This code is released under the Creative Commons Attribution 4.0 International License (CC BY 4.0).
 * You are free to share and adapt this code, provided appropriate credit is given to the original author.
 * For more details, visit: <a href="https://creativecommons.org/licenses/by/4.0/deed.en">Creative Commons</a>
 */
public class Panel extends SkijaUI {
    private UIWindow uiWindow;
    
    Window Display = MinecraftClient.getInstance().getWindow();

    public Panel(Text title) {
        super(title);
    }

    @Override
    public void renderUI(float mouseX, float mouseY) {
        //this.uiWindow = new UIWindow(Minecraft.getMinecraft().displayWidth - 100, Minecraft.getMinecraft().displayHeight - 100);
        this.uiWindow = new UIWindowBuilder().width(Display.getWidth() - 100).height(Display.getHeight() - 100).centerOnScreen().build();

        // Draw shadow for the UI window
        SkiaHelper.shadow(this.uiWindow.x, this.uiWindow.y, Display.getWidth(), Display.getHeight(), 10f, new Color(0xFF121212), 6f, 0, 0);

        // Draw rounded rectangle for the UI window
        SkiaHelper.rrect(this.uiWindow.x, this.uiWindow.y, Display.getWidth(), Display.getHeight(), 10f, SkiaHelper.paint(black));

        // Draw centered text inside the UI window
        SkiaHelper.centeredText(this.uiWindow.x + Display.getWidth() / 2, this.uiWindow.y, "Panel made in Skija!", FontUtils.greycliffBold(20f), SkiaHelper.paint(Color.white));
    }

    @Override
    public void mousePressed(float mouseX, float mouseY, int mouseButton) {
        // Handle mouse press events here if needed
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY) {
        // Handle mouse release events here if needed
    }
}
