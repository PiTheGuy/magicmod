package com.pitheguy.magicmod.init;

import com.pitheguy.magicmod.tileentity.*;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, "magicmod");
    public static final RegistryObject<TileEntityType<MagicInfuserTileEntity>> MAGIC_INFUSER = TILE_ENTITY_TYPES.register("magic_infuser",() -> TileEntityType.Builder.of(MagicInfuserTileEntity::new, RegistryHandler.MAGIC_INFUSER.get()).build(null));
    public static final RegistryObject<TileEntityType<MagicCrateTileEntity>> MAGIC_CRATE = TILE_ENTITY_TYPES.register("magic_crate",() -> TileEntityType.Builder.of(MagicCrateTileEntity::new, RegistryHandler.MAGIC_CRATE.get()).build(null));
    public static final RegistryObject<TileEntityType<MagicPressTileEntity>> MAGIC_PRESS = TILE_ENTITY_TYPES.register("magic_press",() -> TileEntityType.Builder.of(MagicPressTileEntity::new, RegistryHandler.MAGIC_PRESS.get()).build(null));
    public static final RegistryObject<TileEntityType<MagicEnergizerTileEntity>> MAGIC_ENERGIZER = TILE_ENTITY_TYPES.register("magic_energizer",() -> TileEntityType.Builder.of(MagicEnergizerTileEntity::new, RegistryHandler.MAGIC_ENERGIZER.get()).build(null));
    public static final RegistryObject<TileEntityType<MagicMinerTileEntity>> MAGIC_MINER = TILE_ENTITY_TYPES.register("magic_miner",() -> TileEntityType.Builder.of(MagicMinerTileEntity::new, RegistryHandler.MAGIC_MINER.get()).build(null));
    public static final RegistryObject<TileEntityType<MagicLoggerTileEntity>> MAGIC_LOGGER = TILE_ENTITY_TYPES.register("magic_logger",() -> TileEntityType.Builder.of(MagicLoggerTileEntity::new, RegistryHandler.MAGIC_LOGGER.get()).build(null));
}