package xyz.blackdev.Blueberry.events;

import java.lang.reflect.Method;

public record EventData(Object src, Method target, byte priority) {
}
