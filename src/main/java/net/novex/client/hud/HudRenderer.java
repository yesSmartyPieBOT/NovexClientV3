package net.novex.client.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.novex.client.module.ModuleManager;

public class HudRenderer implements HudRenderCallback {
    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.options.hudHidden) return;

        ModuleManager.getModules().stream()
            .filter(m -> m.getCategory() == net.novex.client.api.Category.HUD && m.isEnabled())
            .forEach(m -> {
                // Renders active HUD modules
            });
    }
}
