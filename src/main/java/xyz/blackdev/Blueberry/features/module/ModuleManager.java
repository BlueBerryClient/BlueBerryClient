package xyz.blackdev.Blueberry.features.module;

import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;
import xyz.blackdev.Blueberry.utils.Manager;

import java.util.concurrent.ConcurrentHashMap;

public class ModuleManager extends Manager<Class<? extends Module>, Module> {

    public boolean register(Module module) {
        return register(module.getClass(), module);
    }

    @Override
    public boolean register(@NotNull Class<? extends Module> type, @NotNull Module module) {
        if (entries.contains(module.getClass()))
            unregister(module);

        module.start();
        return super.register(type, module);
    }

    public boolean unregister(Module system) {
       return unregister(system.getClass());
    }

    public boolean unregister(@NotNull Class<? extends Module> module) {
        Module stopped = entries.remove(module);
        if (stopped == null)
        return false;
        stopped.stop();
        return true;
    }

    public void stop() {
        entries.values().forEach(Module::stop);
        entries.clear();
    }

    public void render(DrawContext context) {
        entries.values().forEach(system -> system.render(context));
    }

    public void tick() {
        entries.values().forEach(Module::tick);
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T get(Class<T> system) {
        return (T) entries.get(system);
    }
}
