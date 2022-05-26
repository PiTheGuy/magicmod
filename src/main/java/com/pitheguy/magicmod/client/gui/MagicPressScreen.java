package com.pitheguy.magicmod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pitheguy.magicmod.container.MagicPressContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MagicPressScreen extends AbstractContainerScreen<MagicPressContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("magicmod", "textures/gui/magic_press.png");
    public MagicPressScreen(MagicPressContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);

        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 162;
        this.inventoryLabelY = 70;
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.minecraft.getTextureManager().bindForSetup(TEXTURE);
        this.blit(stack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        this.blit(stack, this.leftPos + 49, this.topPos + 53, 0, 165, this.menu.getFuelScaled(), 16);
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
    }
}
