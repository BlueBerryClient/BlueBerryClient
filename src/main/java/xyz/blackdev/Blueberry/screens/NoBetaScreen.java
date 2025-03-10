package xyz.blackdev.Blueberry.screens;

import me.x150.renderer.font.FontRenderer;
import meteordevelopment.discordipc.DiscordIPC;
import net.minecraft.client.font.FontLoader;
import net.minecraft.client.font.FontManager;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.font.TrueTypeFontLoader;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import xyz.blackdev.Blueberry.utils.keyboardutilitys.OpenLinkUtil;
import xyz.blackdev.Blueberry.utils.drawing.RenderUtils;
import xyz.blackdev.Blueberry.utils.WebhookSender;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class NoBetaScreen extends Screen {

    private LogoDrawer logoDrawer = new LogoDrawer(true);

    public NoBetaScreen(Text title) {
        super(title);
        this.logoDrawer = (LogoDrawer) Objects.requireNonNullElseGet(logoDrawer, () -> new LogoDrawer(false));
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
    }

    boolean done = false;

    @Override
    protected void init() {
        super.init();
        this.addDrawableChild(new PressableTextWidget(197, 130, 50, 10, Text.literal("Discord"), (button) -> OpenLinkUtil.openLink("https://discord.gg/sUq8CCXfCQ"), this.textRenderer));
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


    //File font = new File("resources/assets/blueberry/textures/font/Harabara.ttf");

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        RenderUtils.renderBorderedRect(context, 30, 60, this.width - 30, this.height - 30, 0x80000000, 0xFF000000);
        this.logoDrawer.draw(context, this.width, 1.0F);
        RenderUtils.drawtext("I Dont know how you got this Client, But the Client is still in Beta.", 90, 90, 1,1,1, 1);
        RenderUtils.drawtext("If you believe this is a error please report to the BlueBerry Staff.", 95, 110, 1,1,1, 1);
    }
}
