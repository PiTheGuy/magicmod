package com.pitheguy.magicmod.client.entity.render;

import com.pitheguy.magicmod.client.entity.model.FluffyMagicianBareModel;
import com.pitheguy.magicmod.client.entity.model.FluffyMagicianModel;
import com.pitheguy.magicmod.entities.FluffyMagicianBare;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FluffyMagicianBareRender extends MobRenderer<FluffyMagicianBare, FluffyMagicianBareModel<FluffyMagicianBare>> {

    public FluffyMagicianBareRender(EntityRendererProvider.Context context) {
        super(context, new FluffyMagicianBareModel<>(context.bakeLayer(FluffyMagicianModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(FluffyMagicianBare entity) {
        return new ResourceLocation("magicmod","textures/entities/fluffy_magician_bare.png");
    }
}
