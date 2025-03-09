package xyz.blackdev.Blueberry.keys.impl;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import xyz.blackdev.Blueberry.keys.Key;
import xyz.blackdev.Blueberry.utils.config.ConfigManager;

public class CopyCordsKey implements Key {
    @Override
    public int getKey() {
        return GLFW.GLFW_KEY_H;
    }

    ConfigManager configManager = new ConfigManager();

    @Override
    public void pressAction() {
        if (configManager.getConfigValue("COPYCOORDS").equals("false")) {
            return;
        }else {
            if (MinecraftClient.getInstance().player != null) {
                MinecraftClient.getInstance().keyboard.setClipboard(MinecraftClient.getInstance().player.getBlockPos().toShortString());
            }
        }
    }

    @Override
    public void holdAction() {

    }

    @Override
    public String getName() {
        return "CopyCoords";
    }

    @Override
    public void onScrollUp() {
        Key.super.onScrollUp();
    }

    @Override
    public void onScrollDown() {
        Key.super.onScrollDown();
    }

    @Override
    public boolean isPressed() {
        return false;
    }

    @Override
    public void setPressed(boolean pressed) {

    }

    @Override
    public void releaseAction() {

    }
}
