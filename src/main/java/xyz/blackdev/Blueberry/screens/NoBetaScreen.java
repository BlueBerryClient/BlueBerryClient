package xyz.blackdev.Blueberry.screens;

import meteordevelopment.discordipc.DiscordIPC;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.CreditsAndAttributionScreen;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.text.Text;
import xyz.blackdev.Blueberry.notification.Notification;
import xyz.blackdev.Blueberry.notification.NotificationManager;
import xyz.blackdev.Blueberry.notification.NotificationType;
import xyz.blackdev.Blueberry.utils.CustomClientButton;
import xyz.blackdev.Blueberry.utils.OpenLinkUtil;
import xyz.blackdev.Blueberry.utils.RenderUtils;
import xyz.blackdev.Blueberry.utils.WebhookSender;

import java.io.IOException;

public class NoBetaScreen extends Screen {


    public NoBetaScreen(Text title) {
        super(title);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
    }

    boolean done = false;

    @Override
    protected void init() {
        super.init();
        this.addDrawableChild(new PressableTextWidget(200, 110, 50, 10, Text.literal("Discord"), (button) -> OpenLinkUtil.openLink("https://discord.gg/sUq8CCXfCQ"), this.textRenderer));
if (done == false) {
    try {
        if (client.player == null) {
            WebhookSender.sendEmbed("Illegal Client Start Detected", " Without Beta Access his discord id is " + DiscordIPC.getUser().id + " .", WebhookSender.LogLevel.ERROR);
        } else {
            WebhookSender.sendEmbed("Illegal Client Start Detected", "The Client got started by " + client.player.getName() + " Without Beta Access his discord id is " + DiscordIPC.getUser().id + " .", WebhookSender.LogLevel.ERROR);
        }
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    done = true;
}

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        RenderUtils.drawtext("I Dont know how you got this Client, But the Client is still in Beta.", 60, 70, 0xFFFFFFFF, true);
        RenderUtils.drawtext("If you believe this is a error please report to the BlueBerry Staff.", 45, 90, 0xFFFFFFFF, true);

    }
}
