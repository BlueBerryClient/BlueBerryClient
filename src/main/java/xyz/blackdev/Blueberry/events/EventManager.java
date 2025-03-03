package xyz.blackdev.Blueberry.events;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventManager {

    private static final Map<Class<? extends Event>, List<EventData>> REGISTRY_MAP = new ConcurrentHashMap<>();

    /**
     * Sorts the event data list for the given event class based on priority.
     */
    private static void sortListValue(final Class<? extends Event> clazz) {
        List<EventData> list = REGISTRY_MAP.get(clazz);
        if (list != null) {
            list.sort(Comparator.comparingInt(EventData::priority));
        }
    }

    /**
     * Checks if the given method is invalid for event registration.
     */
    private static boolean isMethodBad(final @NotNull Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventHandler.class);
    }

    /**
     * Checks if the method is invalid and doesn't match the expected event class.
     */
    private static boolean isMethodBad(final Method method, final Class<? extends Event> clazz) {
        return isMethodBad(method) || !method.getParameterTypes()[0].equals(clazz);
    }

    /**
     * Gets the list of registered event data for the given event class.
     */
    public static List<EventData> get(final Class<? extends Event> clazz) {
        return REGISTRY_MAP.getOrDefault(clazz, Collections.emptyList());
    }

    /**
     * Cleans the registry map, removing empty lists if specified.
     */
    public static void cleanMap(final boolean removeOnlyEmptyValues) {
        REGISTRY_MAP.entrySet().removeIf(entry -> !removeOnlyEmptyValues || entry.getValue().isEmpty());
    }

    /**
     * Unregisters all event handlers for a specific object and event class.
     */
    public static void unregister(final Object toUnregister, final Class<? extends Event> clazz) {
        List<EventData> list = REGISTRY_MAP.get(clazz);
        if (list != null) {
            list.removeIf(methodData -> methodData.src().equals(toUnregister));
            cleanMap(true);
        }
    }

    /**
     * Unregisters all event handlers for a specific object across all events.
     */
    public static void unregister(final Object toUnregister) {
        REGISTRY_MAP.values().forEach(list -> list.removeIf(methodData -> methodData.src().equals(toUnregister)));
        cleanMap(true);
    }

    /**
     * Registers a method for an event.
     */
    public static void register(final @NotNull Method method, final Object o) {
        final Class<?> clazz = method.getParameterTypes()[0];
        final EventData methodData = new EventData(o, method, method.getAnnotation(EventHandler.class).value());

        if (!methodData.target().isAccessible()) {
            methodData.target().setAccessible(true);
        }

        REGISTRY_MAP.computeIfAbsent((Class<? extends Event>) clazz, k -> new CopyOnWriteArrayList<>()).add(methodData);
        sortListValue((Class<? extends Event>) clazz);
    }

    /**
     * Registers a batch of event classes.
     */
    public static void registerEvent(Class<?> @NotNull ... events) {
        for (Class<?> eventTarget : events) {
            register(eventTarget);
        }
    }

    /**
     * Registers all valid methods in the given object for a specific event class.
     */
    public static void register(final @NotNull Object targetObj, final Class<? extends Event> eventClass) {
        for (final Method method : targetObj.getClass().getMethods()) {
            if (!isMethodBad(method, eventClass)) {
                register(method, targetObj);
            }
        }
    }

    /**
     * Registers all valid methods in the given object for all event classes.
     */
    public static void register(@NotNull Object targetObj) {
        for (final Method method : targetObj.getClass().getMethods()) {
            if (!isMethodBad(method)) {
                register(method, targetObj);
            }
        }
    }
}
