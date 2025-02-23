package xyz.blackdev.Blueberry;

import xyz.blackdev.Blueberry.basesystem.SystemManager;
import xyz.blackdev.Blueberry.discord.DiscordRPC;
import xyz.blackdev.Blueberry.skija.core.SkiaRenderer;

public class Blueberry {

    public static String defaultdir = "blueberry";

    public static String name = "Blueberry" , version = "v1", author = "BlackDev";
    private final SystemManager manager;

    public Blueberry() {
        this.manager = new SystemManager();
    }

    public static Blueberry instance() {
        return ModEntryPoint.getBlueBarryEntryPoint();
    }

    protected void init() {

    }

    public SystemManager system() {
        return manager;
    }

    public void onPostInit() {
        DiscordRPC.Start();
        SkiaRenderer.createSurface();
        SkiaRenderer.initialize();
    }
}
