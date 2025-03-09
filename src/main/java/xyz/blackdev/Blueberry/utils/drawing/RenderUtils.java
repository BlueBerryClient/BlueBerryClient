package xyz.blackdev.Blueberry.utils.drawing;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.Text;
import org.joml.Matrix4f;

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
    //fr.draw("BlueBerry", 40, 70, 0xFFFFFFFF, false, matrix, vertexConsumerProvider, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
    public static void drawtext(String text, int x,int y, int color, boolean shadow) {
        Matrix4f matrix = new Matrix4f();
        VertexConsumerProvider.Immediate vertexConsumerProvider = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        MinecraftClient.getInstance().textRenderer.draw(text,x,y,color,shadow,matrix,vertexConsumerProvider, TextRenderer.TextLayerType.NORMAL,0,15728880);
    }
}