package com.pitheguy.magicmod.client.entity.render;

import com.pitheguy.magicmod.client.entity.model.FluffyMagicianModel;
import com.pitheguy.magicmod.entities.FluffyMagician;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class FluffyMagicianRender extends MobRenderer<FluffyMagician, FluffyMagicianModel<FluffyMagician>> {

    public FluffyMagicianRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new FluffyMagicianModel<>(), 0.5f);
        //this.addLayer(new PowderLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(FluffyMagician entity) {
        return entity.hasPowder ? new ResourceLocation("magicmod","textures/entities/fluffy_magician.png") : new ResourceLocation("magicmod","textures/entities/fluffy_magician_bare.png");
    }
}
