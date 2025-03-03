package xyz.blackdev.Blueberry.screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Icons;
import net.minecraft.resource.ResourcePack;
import net.minecraft.text.Text;
import xyz.blackdev.Blueberry.mixin.guicomponents.widgets.MixinPressableWidget;
import xyz.blackdev.Blueberry.notification.Notification;
import xyz.blackdev.Blueberry.notification.NotificationManager;
import xyz.blackdev.Blueberry.notification.NotificationType;
import xyz.blackdev.Blueberry.screens.impl.*;
import xyz.blackdev.Blueberry.utils.CustomClientButton;
import xyz.blackdev.Blueberry.utils.RenderUtils;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class ModSelectionScreen extends Screen {
    private final LogoDrawer logoDrawer;
    private final MinecraftClient client = MinecraftClient.getInstance();
    public ModSelectionScreen(Text title, LogoDrawer logoDrawer) {
        super(title);
        this.logoDrawer = (LogoDrawer) Objects.requireNonNullElseGet(logoDrawer, () -> new LogoDrawer(false));
    }

    @Override
    protected void init() {
        super.init();
        CustomClientButton HUDButton = CustomClientButton.builder(Text.literal("HUD"), (button) -> {
                    client.setScreen(new HudSelectionScreen(Text.of("HUD"), logoDrawer));
        }).position(this.width / 2 - 180, this.height / 2  - 37 -5)
                .size(60,20)
                .build();
        CustomClientButton CosmeticButton = CustomClientButton.builder(Text.literal("Cosmetics"), (button) -> {
            client.setScreen(new CosmeticsSelectionScreen(Text.of("Cosmetics"), logoDrawer));
            NotificationManager.show(new Notification(NotificationType.INFORMATION, "Cosmetics", "Cosmetics Button Clicked", 5000));
        }).position(this.width / 2 - 180, this.height / 2  - 13 -5).size(60, 20).build();
        CustomClientButton ModsMutton = CustomClientButton.builder(Text.literal("Mods"), (button) -> {
            client.setScreen(new ModsSelectionScreen(Text.of("Mods"), logoDrawer));
        }).position(this.width / 2 - 180, this.height / 2   + 11 -5).size(60, 20).build();
        CustomClientButton GUIButton = CustomClientButton.builder(Text.literal("GUI"), (button) -> {
           client.setScreen(new GUISelectionScreen(Text.of("GUI"), logoDrawer));
        }).position(this.width / 2 - 180, this.height / 2   + 35 -5).size(60, 20).build();
        CustomClientButton ServerButton = CustomClientButton.builder(Text.literal("Server"), (button) -> {
            client.setScreen(new ServerSelectionScreen(Text.of("Server"), logoDrawer));
        }).position(this.width / 2 - 180, this.height / 2   + 59 -5).size(60, 20).build();
        CustomClientButton AdminButton = CustomClientButton.builder(Text.literal("Admin"), (button) -> {
          if (Objects.equals(client.player.getUuidAsString(), "6488c79e-f4e4-4b3a-90f4-21bc6be4cde6")) {
              client.setScreen(new AdminSelectionScreen(Text.of("Admin"), logoDrawer));
          } else {
              client.setScreen(new ModSelectionScreen(Text.of("Default"), logoDrawer));
          }
        }).position(this.width / 2 - 180, this.height / 2    + 83 -5).size(60, 20).build();
        this.addDrawableChild(HUDButton);
        this.addDrawableChild(CosmeticButton);
        this.addDrawableChild(ModsMutton);
        this.addDrawableChild(GUIButton);
        this.addDrawableChild(ServerButton);
        this.addDrawableChild(AdminButton);
    }

    @Override
    public void renderInGameBackground(DrawContext context) {

    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        RenderUtils.renderBorderedRect(context, 30, 60, this.width - 30, this.height - 30, 0x80000000, 0xFF000000);
        RenderUtils.renderBorderedRect(context, 30, 60, this.width - 330, this.height - 30, 0x80000000, 0xFF000000);
        if (MinecraftClient.getInstance().getWindow().getScaledHeight() > 250) {
            if (client.getWindow().getScaledHeight() < 250) {
                return;
                 } else {
                RenderUtils.drawtext("BlueBerry", 40, 70, 0xFFFFFFFF, true);
            }
        }
        this.logoDrawer.draw(context, this.width, 1.0F);
    }
}