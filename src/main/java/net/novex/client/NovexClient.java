package net.novex.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.novex.client.config.ConfigManager;
import net.novex.client.hud.HudRenderer;
import net.novex.client.module.ModuleManager;
import org.lwjgl.glfw.GLFW;

public class NovexClient implements ClientModInitializer {
    public static final String MOD_ID = "novex-client";
    private static KeyBinding openGuiKey;

    @Override
    public void onInitializeClient() {
        ConfigManager.load();
        ModuleManager.init();
        
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.novex.open_gui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.novex.general"
        ));

        HudRenderCallback.EVENT.register(new HudRenderer());

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openGuiKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new net.novex.client.gui.NovexGuiScreen());
                }
            }
            ModuleManager.getModules().forEach(module -> {
                if (module.isEnabled()) {
                    module.onTick();
                }
            });
        });
    }
}
