package xyz.blackdev.Blueberry;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import xyz.blackdev.Blueberry.discord.DiscordRPC;
import xyz.blackdev.Blueberry.skija.core.SkiaRenderer;

public class ModEntryPoint implements ModInitializer, ClientModInitializer {

    private static Blueberry blueBarryEntryPoint;

    @Override
    public void onInitialize() {
        blueBarryEntryPoint = new Blueberry();
    }

    public static void tick() {

    }

    @Override
    public void onInitializeClient() {
        blueBarryEntryPoint.init();
        DiscordRPC.Start();
        System.out.println("Blueberry has been initialized!");
    }

    protected static Blueberry getBlueBarryEntryPoint() {
        return blueBarryEntryPoint;
    }
}
