package com.pitheguy.magicmod.world;

import com.pitheguy.magicmod.world.gen.oregen.ModOreGen;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "magicmod")
public class ModWorldEvents {
    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        ModOreGen.generateOres(event);
    }
}
