package com.pitheguy.magicmod.tools;

import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class MagicPickaxe extends PickaxeItem {
    public MagicPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        int blocksBroken = 1;
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityLiving;
            double d = state.getPlayerRelativeBlockHardness(player, player.world.getBlockReader(player.world.getChunkAt(pos).getPos().x, player.world.getChunkAt(pos).getPos().z), pos) >= 1 ? 0.02 : 0.1;
            if (Math.random() < EnchantmentHelper.getEnchantmentLevel(RegistryHandler.MAGIC_FINDER.get(), player.getHeldItemMainhand()) * d) {
                player.world.addEntity(new ItemEntity(player.world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(RegistryHandler.MAGIC_POWDER.get())));
            }
            int veinminerLevel = EnchantmentHelper.getEnchantmentLevel(RegistryHandler.VEINMINER.get(), player.getHeldItemMainhand());
            if (veinminerLevel > 0 && state.getHarvestTool() == ToolType.PICKAXE) {
                for (int x = -veinminerLevel; x <= veinminerLevel; x++) {
                    for (int y = -veinminerLevel; y <= veinminerLevel; y++) {
                        for (int z = -veinminerLevel; z <= veinminerLevel; z++) {
                            if (worldIn.getBlockState(pos.add(x, y, z)).getBlock() == state.getBlock()) {
                                worldIn.destroyBlock(pos.add(x,y,z), true, player);
                                blocksBroken++;
                            }
                        }
                    }
                }
            }
        }
        if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
           stack.damageItem(blocksBroken, entityLiving, entity -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        }

        return true;
    }
}
