package xyz.blackdev.Blueberry.keys;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import xyz.blackdev.Blueberry.keys.impl.CopyCordsKey;
import xyz.blackdev.Blueberry.keys.impl.MainMenuKey;

import java.util.ArrayList;
import java.util.List;

public class KeyInputRegestry {

    public static final String KEY_CATEGORY = "Blueberry";
    public static List<Key> keys = new ArrayList<>();

    public static void register() {
        MainMenuKey mainMenuKey = new MainMenuKey();
        registerkey(mainMenuKey);
        CopyCordsKey copyCordsKey = new CopyCordsKey();
        registerkey(copyCordsKey);
    }

    private static void registerkey(Key... keyArray) {
        keys.addAll(List.of(keyArray));
        for (Key key : keyArray) {
            KeyBinding binding = KeyBindingHelper.registerKeyBinding(new KeyBinding(key.getName(), InputUtil.Type.KEYSYM, key.getKey(), KEY_CATEGORY));

        regiserkeyinput(binding, key);

        }
    }

    private static void regiserkeyinput(KeyBinding binding, Key key) {
        final boolean[] wasPressed = {false};
        ClientTickEvents.END_CLIENT_TICK.register(tick -> {
            if (binding.wasPressed()) {
                key.pressAction();
            }
            boolean isPressed = binding.isPressed();
            if (isPressed) {
                key.holdAction();
            }else if (wasPressed[0]) {
                key.releaseAction();
            }
            wasPressed[0] = isPressed;
        });
    }

}
