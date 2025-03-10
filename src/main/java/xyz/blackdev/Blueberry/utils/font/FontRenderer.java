package xyz.blackdev.Blueberry.utils.font;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FontRenderer {
    private ByteBuffer fontBuffer;
    private STBTTFontinfo fontInfo;
    private float scale;

    public FontRenderer(String fontPath, float fontSize) {
        try {
            byte[] fontData = Files.readAllBytes(Paths.get(fontPath));
            fontBuffer = MemoryUtil.memAlloc(fontData.length);
            fontBuffer.put(fontData).flip();

            fontInfo = STBTTFontinfo.create();
            if (!STBTruetype.stbtt_InitFont(fontInfo, fontBuffer)) {
                throw new RuntimeException("Failed to initialize font");
            }
            scale = STBTruetype.stbtt_ScaleForPixelHeight(fontInfo, fontSize);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load font file", e);
        }
    }

    public void drawString(MatrixStack matrices, String text, float x, float y, int color) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer ascent = stack.mallocInt(1);
            IntBuffer descent = stack.mallocInt(1);
            IntBuffer lineGap = stack.mallocInt(1);
            STBTruetype.stbtt_GetFontVMetrics(fontInfo, ascent, descent, lineGap);

            y += ascent.get(0) * scale;

            for (char c : text.toCharArray()) {
                IntBuffer advanceWidth = stack.mallocInt(1);
                IntBuffer leftSideBearing = stack.mallocInt(1);
                STBTruetype.stbtt_GetCodepointHMetrics(fontInfo, c, advanceWidth, leftSideBearing);

                x += advanceWidth.get(0) * scale;
            }
        }
    }
}
