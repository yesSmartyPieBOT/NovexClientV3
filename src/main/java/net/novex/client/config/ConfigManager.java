package net.novex.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.novex.client.api.Module;
import net.novex.client.module.ModuleManager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_DIR = FabricLoader.getInstance().getConfigDir().resolve("novex-client").toFile();
    private static final File CONFIG_FILE = new File(CONFIG_DIR, "config.json");

    public static void save() {
        try {
            if (!CONFIG_DIR.exists()) {
                CONFIG_DIR.mkdirs();
            }
            JsonObject json = new JsonObject();
            for (Module module : ModuleManager.getModules()) {
                JsonObject modJson = new JsonObject();
                modJson.addProperty("enabled", module.isEnabled());
                modJson.addProperty("keybind", module.getKeybind());
                json.add(module.getName(), modJson);
            }
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(json, writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        if (!CONFIG_FILE.exists()) return;
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);
            if (json == null) return;
            for (Module module : ModuleManager.getModules()) {
                if (json.has(module.getName())) {
                    JsonObject modJson = json.getAsJsonObject(module.getName());
                    if (modJson.has("enabled")) {
                        module.setEnabled(modJson.get("enabled").getAsBoolean());
                    }
                    if (modJson.has("keybind")) {
                        module.setKeybind(modJson.get("keybind").getAsInt());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
