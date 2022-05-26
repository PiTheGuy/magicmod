package com.pitheguy.magicmod.world.gen;

import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModOreGen {
    public static final List<ConfiguredFeature<?, ?>> OVERWORLD_ORES = new ArrayList<>();
    public static void registerOres() {
        ConfiguredFeature<?, ?> magicOre = Feature.ORE
                .configured(new OreConfiguration(List.of(
                        OreConfiguration.target(OreConfiguration.Predicates.STONE_ORE_REPLACEABLES, RegistryHandler.MAGIC_ORE.get().defaultBlockState()),
                        OreConfiguration.target(OreConfiguration.Predicates.DEEPSLATE_ORE_REPLACEABLES, RegistryHandler.DEEPSLATE_MAGIC_ORE.get().defaultBlockState())
                ), 4)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(15)).squared().count(4);
        OVERWORLD_ORES.add(register("magic_ore", magicOre));
    }

    private static <Config extends FeatureConfiguration> ConfiguredFeature<Config, ?> register(String name, ConfiguredFeature<Config, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation("magicmod", name),
                configuredFeature);
    }

    @Mod.EventBusSubscriber(modid = "magicmod", bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeBusSubscriber {
        @SubscribeEvent
        public static void biomeLoading(BiomeLoadingEvent event) {
            List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration()
                    .getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);

            if (event.getCategory() != Biome.BiomeCategory.THEEND && event.getCategory() != Biome.BiomeCategory.NETHER)
                OVERWORLD_ORES.forEach(ore -> features.add(() -> ore));
        }
    }
}