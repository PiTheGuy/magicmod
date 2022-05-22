package com.pitheguy.magicmod.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.pitheguy.magicmod.container.MagicMinerContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class MagicMinerScreen extends ContainerScreen<MagicMinerContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("magicmod", "textures/gui/magic_miner.png");
    public MagicMinerScreen(MagicMinerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 201;
        this.imageHeight = 186;
    }

    @Override
    protected void renderBg(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.menu.tileEntity.updateStatus();
        this.minecraft.getTextureManager().bind(TEXTURE);
        this.blit(stack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        this.blit(stack, this.leftPos + 116, this.topPos + 93, 0, 188, this.menu.getMineCooldownScaled(), 16);

    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
    }
}
