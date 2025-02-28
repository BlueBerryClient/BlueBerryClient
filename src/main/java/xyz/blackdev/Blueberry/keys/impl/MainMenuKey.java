package xyz.blackdev.Blueberry.keys.impl;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import xyz.blackdev.Blueberry.keys.Key;
import xyz.blackdev.Blueberry.screens.ModSelectionScreen;

public class MainMenuKey implements Key {
    @Override
    public int getkey() {
        return GLFW.GLFW_KEY_RIGHT_SHIFT;
    }

    @Override
    public String getName() {
        return "ClientMenu";
    }

    @Override
    public void releaseaction() {

    }

    LogoDrawer drawer = new LogoDrawer(true);

    @Override
    public void pressaction() {
        MinecraftClient.getInstance().setScreen(new ModSelectionScreen(Text.literal("Mod Selection"),drawer));
    }

    @Override
    public void holdaction() {

    }

    @Override
    public void onscrollup() {

    }

    @Override
    public void onscrolldown() {

    }
}
