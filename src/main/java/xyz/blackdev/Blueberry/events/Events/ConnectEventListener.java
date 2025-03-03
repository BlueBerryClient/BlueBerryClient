package xyz.blackdev.Blueberry.events.Events;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.jetbrains.annotations.NotNull;

public class ConnectEventListener implements ServerPlayConnectionEvents.Init, ServerPlayConnectionEvents.Disconnect {
    @Override
    public void onPlayDisconnect(ServerPlayNetworkHandler handler, MinecraftServer server) {

    }

    @Override
    public void onPlayInit(ServerPlayNetworkHandler handler, @NotNull MinecraftServer server) {
        MinecraftClient.getInstance().execute(() -> {
            if (server.isSingleplayer()) {

            } else {

            }
        });
    }

}
