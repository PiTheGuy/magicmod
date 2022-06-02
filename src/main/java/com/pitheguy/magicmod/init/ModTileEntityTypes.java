package com.pitheguy.magicmod.init;

import com.pitheguy.magicmod.blockentity.*;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.*;

public class ModTileEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, "magicmod");
    public static final RegistryObject<BlockEntityType<MagicInfuserBlockEntity>> MAGIC_INFUSER = TILE_ENTITY_TYPES.register("magic_infuser",() -> BlockEntityType.Builder.of(MagicInfuserBlockEntity::new, RegistryHandler.MAGIC_INFUSER.get()).build(null));
    public static final RegistryObject<BlockEntityType<MagicCrateBlockEntity>> MAGIC_CRATE = TILE_ENTITY_TYPES.register("magic_crate",() -> BlockEntityType.Builder.of(MagicCrateBlockEntity::new, RegistryHandler.MAGIC_CRATE.get()).build(null));
    public static final RegistryObject<BlockEntityType<MagicPressBlockEntity>> MAGIC_PRESS = TILE_ENTITY_TYPES.register("magic_press",() -> BlockEntityType.Builder.of(MagicPressBlockEntity::new, RegistryHandler.MAGIC_PRESS.get()).build(null));
    public static final RegistryObject<BlockEntityType<MagicEnergizerBlockEntity>> MAGIC_ENERGIZER = TILE_ENTITY_TYPES.register("magic_energizer",() -> BlockEntityType.Builder.of(MagicEnergizerBlockEntity::new, RegistryHandler.MAGIC_ENERGIZER.get()).build(null));
    public static final RegistryObject<BlockEntityType<MagicMinerBlockEntity>> MAGIC_MINER = TILE_ENTITY_TYPES.register("magic_miner",() -> BlockEntityType.Builder.of(MagicMinerBlockEntity::new, RegistryHandler.MAGIC_MINER.get()).build(null));
    public static final RegistryObject<BlockEntityType<MagicLoggerBlockEntity>> MAGIC_LOGGER = TILE_ENTITY_TYPES.register("magic_logger",() -> BlockEntityType.Builder.of(MagicLoggerBlockEntity::new, RegistryHandler.MAGIC_LOGGER.get()).build(null));
}