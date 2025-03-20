package xyz.blackdev.Blueberry.utils.config.configs;

import java.util.HashMap;
import java.util.Map;

public class OverlayConfig {
    private Map<String, OverlayElement> overlays;


    public OverlayConfig() {
        this.overlays = new HashMap<>();
        overlays.put("fps", new OverlayElement(5, 5, true));
    }


    public OverlayElement getOverlay(String name) {
        return overlays.getOrDefault(name, new OverlayElement(0, 0, false));
    }

    public void setOverlay(String name, OverlayElement element) {
        overlays.put(name, element);
    }


    public Map<String, OverlayElement> getAllOverlays() {
        return overlays;
    }


    public static class OverlayElement {
        private int x;
        private int y;
        private boolean visible;

        public OverlayElement(int x, int y, boolean visible) {
            this.x = x;
            this.y = y;
            this.visible = visible;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }
    }
}