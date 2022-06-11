package com.pitheguy.magicmod.world.gen.oregen;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.List;

public class ModOreGen {
    public static void generateOres(final BiomeLoadingEvent event) {
        if (event.getCategory() != BiomeCategory.THEEND && event.getCategory() != BiomeCategory.NETHER) {
            List<Holder<PlacedFeature>> base = event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);
            base.add(ModPlacedFeatures.MAGIC_ORE_PLACED);
        }
    }
}