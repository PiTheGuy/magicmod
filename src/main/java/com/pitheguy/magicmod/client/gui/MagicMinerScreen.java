package com.pitheguy.magicmod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pitheguy.magicmod.container.MagicMinerContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MagicMinerScreen extends AbstractContainerScreen<MagicMinerContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("magicmod", "textures/gui/magic_miner.png");
    public MagicMinerScreen(MagicMinerContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);

        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 201;
        this.imageHeight = 186;
        this.inventoryLabelY = 94;
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.menu.tileEntity.updateStatus();
        this.minecraft.getTextureManager().bindForSetup(TEXTURE);
        this.blit(stack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        this.blit(stack, this.leftPos + 116, this.topPos + 93, 0, 188, this.menu.getMineCooldownScaled(), 16);

    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack stack, int mouseX, int mouseY) {
        this.font.draw(stack, this.title.plainCopy().append(String.format(" (Status: %s)", this.menu.tileEntity.status.getMessage())), (float) this.titleLabelX, (float) this.titleLabelY, 0x404040);
        this.font.draw(stack, this.playerInventoryTitle, (float) this.inventoryLabelX, (float) this.inventoryLabelY, 0x404040);
    }
}
