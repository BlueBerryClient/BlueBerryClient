package xyz.blackdev.Blueberry.utils.drawing;

import me.x150.renderer.font.FontRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.joml.Matrix4f;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class RenderUtils extends Screen{

    protected RenderUtils(Text title) {
        super(title);
    }

    public static void renderBorderedRect(DrawContext guiGraphics, int x, int y, int width, int height, int color, int borderColor) {
        // BASE
        guiGraphics.fill(x, y, width, height, color);

        //Left & Right
        guiGraphics.fill(x, y-1, x-1, height+1, borderColor);
        guiGraphics.fill(width, y-1, width+1, height+1, borderColor);
        //Top & Bottom
        guiGraphics.fill(x, y, width, y-1, borderColor);
        guiGraphics.fill(x, height, width, height+1, borderColor);
    }

    public static void renderBorderedRectWithoutCorners(DrawContext guiGraphics, int x, int y, int width, int height, int color, int borderColor) {
        // BASE
        guiGraphics.fill(x, y, width, height, color);

        //Left & Right
        guiGraphics.fill(x, y, x-1, height, borderColor);
        guiGraphics.fill(width, y, width+1, height, borderColor);
        //Top & Bottom
        guiGraphics.fill(x, y, width, y-1, borderColor);
        guiGraphics.fill(x, height, width, height+1, borderColor);
    }

    static Font font;

    static {
        try {
            // Load the font from the file path
            File fontFile = new File("resources/assets/blueberry/textures/font/font.ttf");
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);

            // Derive the font with the desired size
            font = baseFont.deriveFont(Font.BOLD, 18);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // Fallback to a default font in case of an error
            font = new Font("Serif", Font.PLAIN, 18);
        }
    }

    public static FontRenderer fr = new FontRenderer(font, 9f);

    //fr.draw("BlueBerry", 40, 70, 0xFFFFFFFF, false, matrix, vertexConsumerProvider, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
    public static void drawtext(String text, int x,int y, float r,float g, float b, float a) {
        MatrixStack matrix = new MatrixStack();
        VertexConsumerProvider.Immediate vertexConsumerProvider = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        fr.drawString(matrix,text,x,y,r,g,b,a);
    }

    public static void text(Text text, int x,int y, float r,float g, float b, float a) {
        MatrixStack matrix = new MatrixStack();
        VertexConsumerProvider.Immediate vertexConsumerProvider = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        fr.drawCenteredText(matrix,text,x,y,a);
    }

}