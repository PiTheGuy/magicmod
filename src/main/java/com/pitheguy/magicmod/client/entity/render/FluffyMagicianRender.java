package com.pitheguy.magicmod.client.entity.render;

import com.pitheguy.magicmod.client.entity.model.FluffyMagicianModel;
import com.pitheguy.magicmod.entities.FluffyMagician;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FluffyMagicianRender extends MobRenderer<FluffyMagician, FluffyMagicianModel<FluffyMagician>> {

    public FluffyMagicianRender(EntityRendererProvider.Context context) {
        super(context, new FluffyMagicianModel<>(context.bakeLayer(FluffyMagicianModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(FluffyMagician entity) {
        return new ResourceLocation("magicmod","textures/entities/fluffy_magician.png");
    }
}
