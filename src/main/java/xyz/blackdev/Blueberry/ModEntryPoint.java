package xyz.blackdev.Blueberry;

import meteordevelopment.discordipc.DiscordIPC;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.FontManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.Identifier;
import xyz.blackdev.Blueberry.discord.DiscordRPC;
import xyz.blackdev.Blueberry.events.Events.ConnectEventListener;
import xyz.blackdev.Blueberry.events.Events.RenderEvent;
import xyz.blackdev.Blueberry.keys.KeyInputRegestry;
import xyz.blackdev.Blueberry.utils.ApiClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ModEntryPoint implements ModInitializer, ClientModInitializer {

    private static Blueberry blueBarryEntryPoint;

    @Override
    public void onInitialize() {
        blueBarryEntryPoint = new Blueberry();
        ServerPlayConnectionEvents.INIT.register(new ConnectEventListener());
        ServerPlayConnectionEvents.DISCONNECT.register(new ConnectEventListener());
        initRender();
    }

    public static void tick() {

    }

    @Override
    public void onInitializeClient() {
        DiscordRPC.Start();
        KeyInputRegestry.register();
        blueBarryEntryPoint.init();
        System.out.println("Blueberry has been initialized!");
        MinecraftClient client = MinecraftClient.getInstance();
    }

    protected static Blueberry getBlueBarryEntryPoint() {
        return blueBarryEntryPoint;
    }


    public void initRender() {

        HudRenderCallback.EVENT.register(((drawContext, v) -> {
            renderHUD();
        }));
    }

    public static void renderHUD() {
        DrawContext context = new DrawContext(MinecraftClient.getInstance(), MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers());
        RenderEvent event = new RenderEvent(context, MinecraftClient.getInstance());

        event.call();
    }

}
