package xyz.blackdev.Blueberry.utils.drawing;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import org.joml.Matrix4f;
import xyz.blackdev.Blueberry.utils.math.MathUtils;

public class RoundedRects {

    public static void drawRoundedRect(int left, int top, int right, int bottom, int color) {
        drawRoundedRect(left, top, right, bottom, color, 2);
    }

    public static void drawRoundedRect(int left, int top, int right, int bottom, int color, int rad) {
        drawRoundedRect(left, top, right, bottom, color, rad, 36);
    }
    public static void drawRoundedRect(int left, int top, int right, int bottom, int color, int rad, int points) {
        drawRoundedRectOuter(left + rad, top + rad, right - rad, bottom - rad, color, rad, points);
    }
    public static void drawRoundedRectOuter(int left, int top, int right, int bottom, int color, int rad, int points) {
        int j;
        if (left < right) {
            j = left;
            left = right;
            right = j;
        }

        if (top < bottom) {
            j = top;
            top = bottom;
            bottom = j;
        }


        float a = (float)(color >> 24 & 255) / 255.0F;
        float r = (float)(color >> 16 & 255) / 255.0F;
        float g = (float)(color >> 8 & 255) / 255.0F;
        float b = (float)(color & 255) / 255.0F;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        Matrix4f matrix = new Matrix4f();
        drawCircleQuarter(matrix, left, top, rad, 36, 1, r, g, b, a);
        drawCircleQuarter(matrix, left, bottom, rad, 36, 2, r, g, b, a);
        drawCircleQuarter(matrix, right, bottom, rad, 36, 3, r, g, b, a);
        drawCircleQuarter(matrix, right, top, rad, 36, 4, r, g, b, a);
        drawRectPart(matrix, left, top + rad, right, bottom - rad, r, g, b, a);
        drawRectPart(matrix, right, top, right - rad, bottom, r, g, b, a);
        drawRectPart(matrix, left + rad, top, left, bottom, r, g, b, a);
        RenderSystem.disableBlend();
    }

    private static void drawRectPart(Matrix4f matrix, int left, int top, int right, int bottom, float r, float g, float b, float a) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, (float)left, (float)bottom, 0.0F).color(r, g, b, a);
        bufferBuilder.vertex(matrix, (float)right, (float)bottom, 0.0F).color(r, g, b, a);
        bufferBuilder.vertex(matrix, (float)right, (float)top, 0.0F).color(r, g, b, a);
        bufferBuilder.vertex(matrix, (float)left, (float)top, 0.0F).color(r, g, b, a);
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
    }

    private static void drawCircleQuarter(Matrix4f matrix, int x, int y, int rad, int points, int dir, float r, float g, float b, float a) {
        int quarter = (int)((double)points / 4.0D);
        int start = dir * quarter - quarter;
        int end = dir * quarter;
        drawCirclePart(matrix, x, y, rad, points, start, end, r, g, b, a);
    }
    private static void drawCirclePart(Matrix4f matrix, int x, int y, int rad, int points, int start, int end, float r, float g, float b, float a) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, (float)x, (float)y, 0.0F).color(r, g, b, a);

        for(int ii = start; ii <= end; ++ii) {
            float theta = 6.283185F * (float)ii / (float)points;
            float cx = (float)rad * MathUtils.cos(theta);
            float cy = (float)rad * MathUtils.sin(theta);
            bufferBuilder.vertex(matrix, (float)x + cy, (float)y + cx, 0.0F).color(r, g, b, a);
        }

        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
    }


}
