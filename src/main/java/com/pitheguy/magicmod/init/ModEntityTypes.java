package com.pitheguy.magicmod.init;

import com.pitheguy.magicmod.entities.MagicFriend;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES,
            "magicmod");
    public static final RegistryObject<EntityType<MagicFriend>> MAGIC_FRIEND = ENTITY_TYPES
            .register("magic_friend",
                    () -> EntityType.Builder.create(MagicFriend::new, EntityClassification.CREATURE)
                            .size(1.6f, 1.4f)
                            .build(new ResourceLocation("magicmod", "magic_friend").toString()));
}