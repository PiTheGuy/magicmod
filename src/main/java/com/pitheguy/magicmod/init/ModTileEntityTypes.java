package com.pitheguy.magicmod.init;

import com.pitheguy.magicmod.tileentity.MagicCrateTileEntity;
import com.pitheguy.magicmod.tileentity.MagicEnergizerTileEntity;
import com.pitheguy.magicmod.tileentity.MagicInfuserTileEntity;
import com.pitheguy.magicmod.tileentity.MagicPressTileEntity;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, "magicmod");
    public static final RegistryObject<TileEntityType<MagicInfuserTileEntity>> MAGIC_INFUSER = TILE_ENTITY_TYPES.register("magic_infuser",() -> TileEntityType.Builder.create(MagicInfuserTileEntity::new, RegistryHandler.MAGIC_INFUSER.get()).build(null));
    public static final RegistryObject<TileEntityType<MagicCrateTileEntity>> MAGIC_CRATE = TILE_ENTITY_TYPES.register("magic_crate",() -> TileEntityType.Builder.create(MagicCrateTileEntity::new, RegistryHandler.MAGIC_CRATE.get()).build(null));
    public static final RegistryObject<TileEntityType<MagicPressTileEntity>> MAGIC_PRESS = TILE_ENTITY_TYPES.register("magic_press",() -> TileEntityType.Builder.create(MagicPressTileEntity::new, RegistryHandler.MAGIC_PRESS.get()).build(null));
    public static final RegistryObject<TileEntityType<MagicEnergizerTileEntity>> MAGIC_ENERGIZER = TILE_ENTITY_TYPES.register("magic_energizer",() -> TileEntityType.Builder.create(MagicEnergizerTileEntity::new, RegistryHandler.MAGIC_ENERGIZER.get()).build(null));
}