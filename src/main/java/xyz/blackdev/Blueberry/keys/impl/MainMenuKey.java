package xyz.blackdev.Blueberry.keys.impl;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import xyz.blackdev.Blueberry.keys.Key;
import xyz.blackdev.Blueberry.screens.ModSelectionScreen;

public class MainMenuKey implements Key {

    @Override
    public int getKey() {
        return GLFW.GLFW_KEY_RIGHT_SHIFT;
    }

    @Override
    public void pressAction() {
        MinecraftClient.getInstance().setScreen(new ModSelectionScreen(Text.literal("Mod Selection"),drawer));
    }

    @Override
    public void holdAction() {

    }

    @Override
    public String getName() {
        return "ClientMenu";
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


    LogoDrawer drawer = new LogoDrawer(true);

}
