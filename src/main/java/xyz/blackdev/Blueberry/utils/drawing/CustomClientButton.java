package xyz.blackdev.Blueberry.utils.drawing;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class CustomClientButton extends ButtonWidget {

    public CustomClientButton(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION_SUPPLIER);
    }

    public CustomClientButton(int x, int y, Text message, PressAction onPress) {
        this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, message, onPress);
    }

    public static CustomButtonBuilder builder(Text message, PressAction onPress) {
        return new CustomButtonBuilder(message, onPress);
    }

    public static class CustomButtonBuilder extends Builder {
        private final Text message;
        private final PressAction onPress;
        @Nullable
        private Tooltip tooltip;
        private int x;
        private int y;
        private int width = DEFAULT_WIDTH;
        private int height = DEFAULT_HEIGHT;

        public CustomButtonBuilder(Text message, PressAction onPress) {
            super(message, onPress);
            this.message = message;
            this.onPress = onPress;
        }

        public CustomButtonBuilder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public CustomButtonBuilder width(int width) {
            this.width = width;
            return this;
        }

        public CustomButtonBuilder height(int height) {
            this.height = height;
            return this;
        }

        public CustomButtonBuilder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public CustomButtonBuilder tooltip(@Nullable Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public CustomClientButton build() {
            CustomClientButton button = new CustomClientButton(this.x, this.y, this.width, this.height, this.message, this.onPress);
            button.setTooltip(this.tooltip);
            return button;
        }
    }
}