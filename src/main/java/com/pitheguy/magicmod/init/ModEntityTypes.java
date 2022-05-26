package com.pitheguy.magicmod.init;

import com.pitheguy.magicmod.entities.FluffyMagician;
import com.pitheguy.magicmod.entities.FluffyMagicianBare;
import com.pitheguy.magicmod.entities.MagicFriend;
import com.pitheguy.magicmod.entities.MagicPearlEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
            "magicmod");
    public static final RegistryObject<EntityType<MagicFriend>> MAGIC_FRIEND = ENTITY_TYPES
            .register("magic_friend", () -> EntityType.Builder.of(MagicFriend::new, MobCategory.CREATURE)
                    .sized(1.6f, 1.4f).build(new ResourceLocation("magicmod", "magic_friend").toString()));
    public static final RegistryObject<EntityType<FluffyMagician>> FLUFFY_MAGICIAN = ENTITY_TYPES
            .register("fluffy_magician", () -> EntityType.Builder.of(FluffyMagician::new, MobCategory.CREATURE)
                    .sized(1f, 0.8f).build(new ResourceLocation("magicmod", "fluffy_magician").toString()));
    public static final RegistryObject<EntityType<FluffyMagicianBare>> FLUFFY_MAGICIAN_BARE = ENTITY_TYPES
            .register("fluffy_magician_bare", () -> EntityType.Builder.of(FluffyMagicianBare::new, MobCategory.CREATURE)
                    .sized(0.8f, 0.8f).build(new ResourceLocation("magicmod", "fluffy_magician_bare").toString()));
    public static final RegistryObject<EntityType<MagicPearlEntity>> MAGIC_PEARL = ENTITY_TYPES
            .register("magic_pearl", () -> EntityType.Builder.<MagicPearlEntity>of(MagicPearlEntity::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f).build(new ResourceLocation("magicmod", "magic_pearl").toString()));

}
