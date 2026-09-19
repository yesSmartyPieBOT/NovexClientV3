package net.novex.client.module;

import net.novex.client.api.Module;
import net.novex.client.module.hud.*;
import net.novex.client.module.visual.*;
import net.novex.client.module.gameplay.*;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private static final List<Module> modules = new ArrayList<>();

    public static void init() {
        register(new ArmorHudModule());
        register(new KeystrokesModule());
        register(new PotionHudModule());
        register(new FpsHudModule());
        register(new CrosshairModule());
        register(new LowFireModule());
        register(new AutoSprintModule());
        register(new ZoomModule());
    }

    private static void register(Module module) {
        modules.add(module);
    }

    public static List<Module> getModules() {
        return modules;
    }
}
