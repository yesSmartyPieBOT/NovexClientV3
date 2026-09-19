package net.novex.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.novex.client.api.Category;
import net.novex.client.api.Module;
import net.novex.client.api.Setting;
import net.novex.client.config.ConfigManager;
import net.novex.client.module.ModuleManager;

import java.util.List;

public class NovexGuiScreen extends Screen {
    private Category selectedCategory = Category.HUD;
    private Module selectedModule = null;
    
    // Panel Dimensions
    private int guiLeft;
    private int guiTop;
    private final int guiWidth = 420;
    private final int guiHeight = 260;

    public NovexGuiScreen() {
        super(Text.literal("NOVEX CLIENT Control Center"));
    }

    @Override
    protected void init() {
        super.init();
        this.guiLeft = (this.width - this.guiWidth) / 2;
        this.guiTop = (this.height - this.guiHeight) / 2;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Darken background
        this.renderBackground(context, mouseX, mouseY, delta);

        // Main Window Frame (Dark Theme with Accent Border)
        context.fill(guiLeft, guiTop, guiLeft + guiWidth, guiTop + guiHeight, 0xF0121218);
        context.drawBorder(guiLeft, guiTop, guiWidth, guiHeight, 0xFF2A2A38);

        // Header Title
        context.fill(guiLeft, guiTop, guiLeft + guiWidth, guiTop + 28, 0xFF181822);
        context.drawCenteredTextWithShadow(this.textRenderer, "NOVEX CLIENT - CONTROL CENTER", guiLeft + guiWidth / 2, guiTop + 10, 0xFF55FF55);

        // Render Sidebar (Categories)
        renderSidebar(context, mouseX, mouseY);

        // Render Module List for Selected Category
        renderModuleList(context, mouseX, mouseY);

        // Render Module Settings Panel if selected
        renderSettingsPanel(context, mouseX, mouseY);

        super.render(context, mouseX, mouseY, delta);
    }

    private void renderSidebar(DrawContext context, int mouseX, int mouseY) {
        int sidebarWidth = 110;
        context.fill(guiLeft, guiTop + 28, guiLeft + sidebarWidth, guiTop + guiHeight, 0xFF181822);
        context.fill(guiLeft + sidebarWidth - 1, guiTop + 28, guiLeft + sidebarWidth, guiTop + guiHeight, 0xFF2A2A38);

        int catY = guiTop + 38;
        for (Category cat : Category.values()) {
            boolean isSelected = (cat == selectedCategory);
            boolean isHovered = mouseX >= guiLeft && mouseX <= guiLeft + sidebarWidth && mouseY >= catY && mouseY < catY + 24;

            int bgColor = isSelected ? 0xFF2A2A3A : (isHovered ? 0xFF20202C : 0x00000000);
            context.fill(guiLeft + 4, catY, guiLeft + sidebarWidth - 4, catY + 22, bgColor);

            String iconStr = getCategoryIcon(cat);
            int textColor = isSelected ? 0xFF55FF55 : (isHovered ? 0xFFFFFFFF : 0xFFAAAAAA);
            context.drawTextWithShadow(this.textRenderer, iconStr + " " + cat.getDisplayName(), guiLeft + 10, catY + 7, textColor);

            catY += 26;
        }
    }

    private String getCategoryIcon(Category cat) {
        return switch (cat) {
            case HUD -> "[H]";
            case VISUAL -> "[V]";
            case PERFORMANCE -> "[P]";
            case GAMEPLAY -> "[G]";
        };
    }

    private void renderModuleList(DrawContext context, int mouseX, int mouseY) {
        int startX = guiLeft + 115;
        int startY = guiTop + 35;
        int listWidth = 145;

        List<Module> categoryModules = ModuleManager.getModules().stream()
                .filter(m -> m.getCategory() == selectedCategory)
                .toList();

        int modY = startY;
        for (Module module : categoryModules) {
            boolean isHovered = mouseX >= startX && mouseX <= startX + listWidth && mouseY >= modY && mouseY < modY + 28;
            boolean isSelected = (module == selectedModule);

            int bg = isSelected ? 0xFF282838 : (isHovered ? 0xFF202030 : 0xFF1B1B26);
            context.fill(startX, modY, startX + listWidth, modY + 26, bg);
            context.drawBorder(startX, modY, listWidth, 26, isSelected ? 0xFF55FF55 : 0xFF2A2A38);

            // Module Name
            context.drawTextWithShadow(this.textRenderer, module.getName(), startX + 8, modY + 9, 0xFFFFFFFF);

            // Toggle Switch
            int toggleX = startX + listWidth - 26;
            int toggleY = modY + 7;
            int toggleColor = module.isEnabled() ? 0xFF55FF55 : 0xFFFF5555;
            context.fill(toggleX, toggleY, toggleX + 18, toggleY + 12, toggleColor);
            context.drawTextWithShadow(this.textRenderer, module.isEnabled() ? "ON" : "OFF", toggleX + 2, toggleY + 2, 0xFF000000);

            modY += 30;
        }
    }

    private void renderSettingsPanel(DrawContext context, int mouseX, int mouseY) {
        int startX = guiLeft + 268;
        int startY = guiTop + 35;
        int panelWidth = 145;

        context.fill(startX, startY, startX + panelWidth, guiTop + guiHeight - 10, 0xFF181822);
        context.drawBorder(startX, startY, panelWidth, guiHeight - 45, 0xFF2A2A38);

        if (selectedModule == null) {
            context.drawCenteredTextWithShadow(this.textRenderer, "Select a Module", startX + panelWidth / 2, startY + 20, 0xFF888888);
            return;
        }

        context.drawTextWithShadow(this.textRenderer, selectedModule.getName(), startX + 8, startY + 8, 0xFF55FF55);
        context.fill(startX + 8, startY + 20, startX + panelWidth - 8, startY + 21, 0xFF333344);

        int setY = startY + 28;
        for (Setting<?> setting : selectedModule.getSettings()) {
            context.drawTextWithShadow(this.textRenderer, setting.getName(), startX + 8, setY, 0xDDD0D0D0);
            
            // Render basic value button
            context.fill(startX + 8, setY + 11, startX + panelWidth - 8, setY + 25, 0xFF252535);
            context.drawBorder(startX + 8, setY + 11, panelWidth - 16, 14, 0xFF3A3A4C);
            context.drawCenteredTextWithShadow(this.textRenderer, setting.getValue().toString(), startX + panelWidth / 2, setY + 14, 0xFF55FFFF);

            setY += 32;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int sidebarWidth = 110;

        // Category Clicks
        if (mouseX >= guiLeft && mouseX <= guiLeft + sidebarWidth) {
            int catY = guiTop + 38;
            for (Category cat : Category.values()) {
                if (mouseY >= catY && mouseY < catY + 24) {
                    selectedCategory = cat;
                    selectedModule = null;
                    return true;
                }
                catY += 26;
            }
        }

        // Module Clicks & Toggles
        int startX = guiLeft + 115;
        int startY = guiTop + 35;
        int listWidth = 145;

        List<Module> categoryModules = ModuleManager.getModules().stream()
                .filter(m -> m.getCategory() == selectedCategory)
                .toList();

        int modY = startY;
        for (Module module : categoryModules) {
            if (mouseX >= startX && mouseX <= startX + listWidth && mouseY >= modY && mouseY < modY + 26) {
                // Click on toggle box specifically or item
                int toggleX = startX + listWidth - 26;
                if (mouseX >= toggleX && mouseX <= toggleX + 18) {
                    module.toggle();
                    ConfigManager.save();
                } else {
                    selectedModule = module;
                }
                return true;
            }
            modY += 30;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
