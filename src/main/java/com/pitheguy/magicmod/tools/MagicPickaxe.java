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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class MagicPickaxe extends PickaxeItem {
    public MagicPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        int blocksBroken = 1;
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityLiving;
            IBlockReader reader = player.level.getChunkForCollisions(player.level.getChunkAt(pos).getPos().x, player.level.getChunkAt(pos).getPos().z);
            double d = player.getDigSpeed(state, pos) >= 1 ? 0.02 : 0.1;
            if (Math.random() < EnchantmentHelper.getEnchantmentLevel(RegistryHandler.MAGIC_FINDER.get(), player) * d) {
                player.level.addFreshEntity(new ItemEntity(player.level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(RegistryHandler.MAGIC_POWDER.get())));
            }
            int veinminerLevel = EnchantmentHelper.getEnchantmentLevel(RegistryHandler.VEINMINER.get(), player);
            if (veinminerLevel > 0 && state.canHarvestBlock(reader, pos, player)) {
                for (int x = -veinminerLevel; x <= veinminerLevel; x++) {
                    for (int y = -veinminerLevel; y <= veinminerLevel; y++) {
                        for (int z = -veinminerLevel; z <= veinminerLevel; z++) {
                            BlockPos currBlock = new BlockPos(Vector3d.atCenterOf(pos).add(x, y, z));
                            if (worldIn.getBlockState(currBlock).getBlock() == state.getBlock()) {
                                worldIn.destroyBlock(currBlock, true, player);
                                blocksBroken++;
                            }
                        }
                    }
                }
            }
        }
        if (!worldIn.isClientSide && state.getDestroySpeed(worldIn, pos) != 0.0F) {
           stack.hurtAndBreak(blocksBroken, entityLiving, entity -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        }

        return true;
    }
}
