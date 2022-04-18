package com.pitheguy.magicmod.client.entity.render;

import com.pitheguy.magicmod.client.entity.model.FluffyMagicianBareModel;
import com.pitheguy.magicmod.entities.FluffyMagicianBare;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class FluffyMagicianBareRender extends MobRenderer<FluffyMagicianBare, FluffyMagicianBareModel<FluffyMagicianBare>> {

    public FluffyMagicianBareRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new FluffyMagicianBareModel<>(), 0.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(FluffyMagicianBare entity) {
        return new ResourceLocation("magicmod","textures/entities/fluffy_magician_bare.png");
    }
}
