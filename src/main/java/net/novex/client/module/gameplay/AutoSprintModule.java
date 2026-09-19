package net.novex.client.module.gameplay;

import net.novex.client.api.Category;
import net.novex.client.api.Module;

public class AutoSprintModule extends Module {
    public AutoSprintModule() {
        super("Auto Sprint", "Automatically holds down the sprint key.", Category.GAMEPLAY);
    }

    @Override
    public void onTick() {
        if (mc.player != null && mc.options != null) {
            mc.options.sprintKey.setPressed(true);
        }
    }
}
