package xyz.blackdev.Blueberry.mixin.screenmixins.titlescreen;

import meteordevelopment.discordipc.DiscordIPC;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerWarningScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import xyz.blackdev.Blueberry.screens.NoBetaScreen;
import xyz.blackdev.Blueberry.screens.alts.AltScreen;
import xyz.blackdev.Blueberry.utils.connection.ApiClient;

import java.util.Objects;

@Mixin(TitleScreen.class)
public class TitleScreenButtonMixin extends Screen {

    @Shadow @Final private LogoDrawer logoDrawer;

    protected TitleScreenButtonMixin(Text title) {
        super(title);
    }

    /**
     * @author BlackDev
     * @reason remove the buttons
     */
    @Overwrite
    public void init() {

        boolean beta = ApiClient.checkUserHasRole(DiscordIPC.getUser().id);
        if (!beta) {
            if (!Objects.equals(DiscordIPC.getUser().id, "624957481948413982")) {
                assert this.client != null;
                this.client.setScreen(new NoBetaScreen(Text.literal("BlueBerry NoBeta Screen")));
            }
        }



        int i = this.textRenderer.getWidth(Text.translatable("© Mojang"));
        int j = this.width - i - 2;
        int l = this.height / 4 + 48;
            l = this.addNormalWidgets(l, 24);
        l += 36;
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.options"), (button) -> this.client.setScreen(new OptionsScreen(this, this.client.options))).dimensions(this.width / 2 - 100, l, 98, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.quit"), (button) -> this.client.scheduleStop()).dimensions(this.width / 2 + 2, l, 98, 20).build());
    }

    private int addNormalWidgets(int y, int spacingY) {
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.singleplayer"), (button) -> this.client.setScreen(new SelectWorldScreen(this))).dimensions(this.width / 2 - 100, y, 200, 20).build());
        Text text = Text.translatable("");
        boolean bl = true;
        Tooltip tooltip = text != null ? Tooltip.of(text) : null;
        int var6;
        ((ButtonWidget)this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.multiplayer"), (button) -> {
            Screen screen = (Screen)(this.client.options.skipMultiplayerWarning ? new MultiplayerScreen(this) : new MultiplayerWarningScreen(this));
            this.client.setScreen(screen);
        }).dimensions(this.width / 2 - 100, var6 = y + spacingY, 200, 20).tooltip(tooltip).build())).active = bl;
        Screen TitleScreen = new TitleScreen();
        ((ButtonWidget)this.addDrawableChild(ButtonWidget.builder(Text.translatable("Test"), (button) -> this.client.setScreen(new AltScreen(TitleScreen))).dimensions(this.width / 2 - 100, y = var6 + spacingY, 200, 20).tooltip(tooltip).build())).active = bl;
        return y;
    }



}
