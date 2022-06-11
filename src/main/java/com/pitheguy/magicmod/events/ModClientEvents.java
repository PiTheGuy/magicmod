package com.pitheguy.magicmod.events;

import com.pitheguy.magicmod.util.ArmorHandler;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "magicmod", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void onDamageWithMagicArmor(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof Player player) {
            if (ArmorHandler.isWearingMagicArmor(player)) {
                event.setAmount(event.getAmount() * 0.6f);
            } else if (ArmorHandler.isWearingReinforcedMagicArmor(player)) {
                event.setAmount(event.getAmount() * 0.2f);
            } else if (ArmorHandler.isWearingObsidianPlatedReinforcedMagicArmor(player)) {
                event.setAmount(event.getAmount() * 0.05f);
            }
        }
    }

    @SubscribeEvent
    public static void harvestCheck(PlayerEvent.HarvestCheck event) {
        if (event.getTargetBlock().getBlock() == RegistryHandler.MAGIC_OBSIDIAN.get() && event.getPlayer().getMainHandItem().getItem() instanceof PickaxeItem pickaxe && pickaxe.getTier().getLevel() < 5) {
            event.setCanHarvest(false);
        }
    }
}
