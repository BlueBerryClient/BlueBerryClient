package xyz.blackdev.Blueberry.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WebhookSender {
    private static final String DEFAULT_WEBHOOK_URL = "https://discord.com/api/webhooks/1346571399648772106/S0HTnZGCyaA6PbTTkxC0yD3Vv58i4jgEPnPaHjFWGEk11xPeokGRTSw7XiTLqdFSV18B";

    public enum LogLevel {
        INFO("00FF00"),
        WARN("FFFF00"),
        ERROR("FF0000"),
        CUSTOM("");

        private final String color;

        LogLevel(String color) {
            this.color = color;
        }

        public String getColor() {
            return color;
        }
    }

    public static void sendEmbed(String title, String description, @NotNull LogLevel logLevel) throws IOException {
        sendEmbed(title, description, logLevel.getColor());
    }

    public static void sendEmbed(String title, String description, String customColor) throws IOException {
        JsonObject embed = new JsonObject();
        embed.addProperty("title", title);
        embed.addProperty("description", description);
        embed.addProperty("color", Integer.parseInt(customColor, 16));

        JsonArray embeds = new JsonArray();
        embeds.add(embed);

        JsonObject payload = new JsonObject();
        payload.add("embeds", embeds);

        URL url = new URL(DEFAULT_WEBHOOK_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = payload.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK && responseCode != HttpURLConnection.HTTP_NO_CONTENT) {
            throw new IOException("Unexpected code " + responseCode);
        }
    }
}