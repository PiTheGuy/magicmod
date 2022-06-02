package com.pitheguy.magicmod.world.gen.oregen;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModPlacedFeatures {
    public static final Holder<PlacedFeature> MAGIC_ORE_PLACED = PlacementUtils.register("magic_ore_placed",
            ModConfiguredFeatures.MAGIC_ORE, ModOrePlacement.commonOrePlacement(4, // VeinsPerChunk
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-32), VerticalAnchor.aboveBottom(32))));
}
