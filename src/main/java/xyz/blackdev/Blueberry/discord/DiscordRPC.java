package xyz.blackdev.Blueberry.discord;
import meteordevelopment.discordipc.DiscordIPC;
import meteordevelopment.discordipc.RichPresence;
import net.minecraft.client.MinecraftClient;
import xyz.blackdev.Blueberry.Blueberry;

import java.time.Instant;

public class DiscordRPC {

    public static String returnName() {
        if (MinecraftClient.getInstance().player == null) {
            return "BlackDev";
        } else {
            return MinecraftClient.getInstance().player.getName().toString();
        }
    }

    public static String returnHead() {
        if (MinecraftClient.getInstance().player == null) {
            return "https://api.mineatar.io/face/6488c79ef4e44b3a90f421bc6be4cde6?scale=32";
        } else {
            return "https://api.mineatar.io/face/" + MinecraftClient.getInstance().player.getUuidAsString() + "?scale=32";
        }
    }

    public static void Start() {
        System.out.println("Starting Discord IPC");

        if (!DiscordIPC.start(1342121532352233582L, () -> System.out.println("Logged in account: " + DiscordIPC.getUser().username))) {
            System.out.println("Failed to start Discord IPC");
            return;
        }



        RichPresence presence = new RichPresence();
        presence.setDetails(Blueberry.name + " - " + Blueberry.version);
        presence.setState("by " + Blueberry.author);
        presence.setLargeImage("large", Blueberry.name + " - " + Blueberry.version + " - " + Blueberry.author);
        presence.setSmallImage(returnHead(), returnName());
        presence.setStart(Instant.now().getEpochSecond());

        DiscordIPC.setActivity(presence);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
