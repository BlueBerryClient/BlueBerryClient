package xyz.blackdev.Blueberry.basesystem;

import net.minecraft.client.gui.DrawContext;

import java.util.concurrent.ConcurrentHashMap;

public class SystemManager {

    private final ConcurrentHashMap<Class<? extends BSystem>, BSystem> systems = new ConcurrentHashMap<>();
    public void register(BSystem system) {
        if (systems.contains(system.getClass()))
            unregister(system);

        system.start();
        systems.put(system.getClass(), system);
    }

    public void unregister(BSystem system) {
       unregister(system.getClass());
    }

    public void unregister(Class<? extends BSystem> system) {
        BSystem stopped = systems.remove(system);
        if (stopped == null) return;
        stopped.stop();

    }

    public void stop() {
        systems.values().forEach(BSystem::stop);
        systems.clear();
    }

    public void render(DrawContext context) {
        systems.values().forEach(system -> system.render(context));
    }

    public void tick() {
        systems.values().forEach(BSystem::tick);
    }

    @SuppressWarnings("unchecked")
    public <T extends BSystem> T get(Class<T> system) {
        return (T) systems.get(system);
    }
}
