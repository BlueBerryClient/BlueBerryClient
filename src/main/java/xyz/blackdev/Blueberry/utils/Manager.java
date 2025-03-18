package xyz.blackdev.Blueberry.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Manager<T,S> {
    
    protected final ConcurrentHashMap<T,S> entries = new ConcurrentHashMap<>();
    
    public boolean register(@NotNull T key,@NotNull S value) {
        return entries.putIfAbsent(key,value) == null;
    }
    public boolean unregister(@NotNull T key) {
        return entries.remove(key) != null;
    }
    
    public @Nullable S get(@NotNull T key) {
        return entries.get(key);
    }
    
    public boolean contains(@NotNull T key) {
        return entries.containsKey(key);
    }
    
    public Collection<S> values() {
        return entries.values();
    }
    
    public Set<Map.Entry<T,S>> entries() {
        return entries.entrySet();
    }
    
    public Set<T> keys() {
        return entries.keySet();
    }
    
}
