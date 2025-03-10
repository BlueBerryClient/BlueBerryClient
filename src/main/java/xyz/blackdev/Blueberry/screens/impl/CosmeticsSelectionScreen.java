package xyz.blackdev.Blueberry.screens.impl;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import xyz.blackdev.Blueberry.notification.Notification;
import xyz.blackdev.Blueberry.notification.NotificationManager;
import xyz.blackdev.Blueberry.notification.NotificationType;
import xyz.blackdev.Blueberry.utils.drawing.CustomClientButton;
import xyz.blackdev.Blueberry.utils.drawing.RenderUtils;

import java.util.Objects;

public class CosmeticsSelectionScreen extends Screen {
    private final LogoDrawer logoDrawer;
    private final MinecraftClient client = MinecraftClient.getInstance();
    public CosmeticsSelectionScreen(Text title, LogoDrawer logoDrawer) {
        super(title);
        this.logoDrawer = (LogoDrawer) Objects.requireNonNullElseGet(logoDrawer, () -> new LogoDrawer(false));
    }

    @Override
    protected void init() {
        super.init();
        CustomClientButton HUDButton = CustomClientButton.builder(Text.literal("HUD"), (button) -> {
                    NotificationManager.show(new Notification(NotificationType.INFORMATION, "HUD", "HUD Button Clicked", 5000));
                }).position(this.width / 2 - 180, this.height / 2  - 37 -5)
                .size(60,20)
                .build();
        CustomClientButton CosmeticButton = CustomClientButton.builder(Text.literal("Cosmetics"), (button) -> {
            System.out.println("Button Clicked");
        }).position(this.width / 2 - 180, this.height / 2  - 13 -5).size(60, 20).build();
        CustomClientButton ModsMutton = CustomClientButton.builder(Text.literal("Mods"), (button) -> {
            System.out.println("Button Clicked");
        }).position(this.width / 2 - 180, this.height / 2   + 11 -5).size(60, 20).build();
        CustomClientButton GUIButton = CustomClientButton.builder(Text.literal("GUI"), (button) -> {
            System.out.println("Button Clicked");
        }).position(this.width / 2 - 180, this.height / 2   + 35 -5).size(60, 20).build();
        CustomClientButton ServerButton = CustomClientButton.builder(Text.literal("Server"), (button) -> {
            System.out.println("Button Clicked");
        }).position(this.width / 2 - 180, this.height / 2   + 59 -5).size(60, 20).build();
        CustomClientButton AdminButton = CustomClientButton.builder(Text.literal("Admin"), (button) -> {
            System.out.println("Button Clicked");
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
        RenderUtils.renderBorderedRect(context, 30, 60, this.width - 330, this.height - 30, 0x40000000, 0xFF000000);
        if (MinecraftClient.getInstance().getWindow().getScaledHeight() > 250) {
            if (client.getWindow().getScaledHeight() < 250) {
                return;
            } else {
                RenderUtils.drawtext("Cosmetics", 40, 70, 1,1,1,0 );
            }
        }
        this.logoDrawer.draw(context, this.width, 1.0F);
    }
}