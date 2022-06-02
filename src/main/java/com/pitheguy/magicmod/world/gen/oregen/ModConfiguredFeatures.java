package com.pitheguy.magicmod.world.gen.oregen;

import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.List;

public class ModConfiguredFeatures {
    public static final List<OreConfiguration.TargetBlockState> OVERWORLD_MAGIC_ORES = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, RegistryHandler.MAGIC_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, RegistryHandler.DEEPSLATE_MAGIC_ORE.get().defaultBlockState()));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> MAGIC_ORE = FeatureUtils.register("magic_ore",
            Feature.ORE, new OreConfiguration(OVERWORLD_MAGIC_ORES, 4));
}
