package com.pitheguy.magicmod.client.entity.render;

import com.pitheguy.magicmod.client.entity.model.MagicFriendModel;
import com.pitheguy.magicmod.entities.MagicFriend;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MagicFriendRender extends MobRenderer<MagicFriend, MagicFriendModel<MagicFriend>> {

    public static final ResourceLocation TEXTURE = new ResourceLocation("magicmod","textures/entities/magic_friend.png");

    public MagicFriendRender(EntityRendererProvider.Context context) {
        super(context, new MagicFriendModel<>(context.bakeLayer(MagicFriendModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MagicFriend entity) {
        return TEXTURE;
    }
}
