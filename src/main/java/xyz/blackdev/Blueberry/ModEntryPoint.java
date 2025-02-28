package xyz.blackdev.Blueberry;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.FontManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.Identifier;
import xyz.blackdev.Blueberry.discord.DiscordRPC;
import xyz.blackdev.Blueberry.keys.KeyInputRegestry;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

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
        KeyInputRegestry.register();
        blueBarryEntryPoint.init();
        DiscordRPC.Start();
        System.out.println("Blueberry has been initialized!");
        MinecraftClient client = MinecraftClient.getInstance();

    }

    protected static Blueberry getBlueBarryEntryPoint() {
        return blueBarryEntryPoint;
    }
}
