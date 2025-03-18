package xyz.blackdev.Blueberry.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Registry<T> {

    private final ConcurrentLinkedQueue<T> entiries = new ConcurrentLinkedQueue<>();

    public boolean register(@NotNull T key) {
        return entiries.add(key);
    }
    public boolean unregister(@NotNull T key) {
        return entiries.remove(key);
    }

    public boolean contains(@NotNull T key) {
        return entiries.contains(key);
    }

    public @Unmodifiable Collection<T> entries() {
        return Collections.unmodifiableCollection(this.entiries);
    }


}
