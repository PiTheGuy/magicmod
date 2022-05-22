package com.pitheguy.magicmod.client.entity.render;

import com.pitheguy.magicmod.client.entity.model.MagicFriendModel;
import com.pitheguy.magicmod.entities.MagicFriend;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class MagicFriendRender extends MobRenderer<MagicFriend, MagicFriendModel<MagicFriend>> {

    public static final ResourceLocation TEXTURE = new ResourceLocation("magicmod","textures/entities/magic_friend.png");

    public MagicFriendRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new MagicFriendModel<>(), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MagicFriend entity) {
        return TEXTURE;
    }
}
