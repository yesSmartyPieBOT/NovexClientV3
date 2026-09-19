package net.novex.client.module.hud;

import net.novex.client.api.Category;
import net.novex.client.api.Module;

public class FpsHudModule extends Module {
    public FpsHudModule() {
        super("FPS HUD", "Shows real-time FPS and ping overlay.", Category.HUD);
    }
}
