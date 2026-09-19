package net.novex.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class NovexGuiScreen extends Screen {
    public NovexGuiScreen() {
        super(Text.literal("NOVEX CLIENT Control Center"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, "NOVEX CLIENT Control Center", this.width / 2, 20, 0xFFFFFF);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
