package com.pitheguy.magicmod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pitheguy.magicmod.container.MagicCrateContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class MagicCrateScreen extends ContainerScreen<MagicCrateContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("magicmod", "textures/gui/magic_crate.png");
    public MagicCrateScreen(MagicCrateContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 212;
        this.ySize = 255;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.font.drawString(this.title.getFormattedText(), 8, 7, 0x404040);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8, 163, 0x404040);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}