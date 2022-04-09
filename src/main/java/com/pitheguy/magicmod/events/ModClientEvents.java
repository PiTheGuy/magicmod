package com.pitheguy.magicmod.events;

import com.pitheguy.magicmod.util.ArmorHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "magicmod", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void onDamageWithMagicArmor(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (ArmorHandler.isWearingMagicArmor(player)) {
                event.setAmount(event.getAmount() * 0.6f);
            } else if (ArmorHandler.isWearingReinforcedMagicArmor(player)) {
                event.setAmount(event.getAmount() * 0.2f);
            } else if (ArmorHandler.isWearingObsidianPlatedReinforcedMagicArmor(player)) {
                event.setAmount(event.getAmount() * 0.05f);
            }
        }
    }
}
