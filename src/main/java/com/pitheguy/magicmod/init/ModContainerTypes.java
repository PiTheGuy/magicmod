package com.pitheguy.magicmod.init;

import com.pitheguy.magicmod.container.MagicInfuserContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(
            ForgeRegistries.CONTAINERS, "magicmod");
    public static final RegistryObject<ContainerType<MagicInfuserContainer>> MAGIC_INFUSER = CONTAINER_TYPES
            .register("example_furnace", () -> IForgeContainerType.create(MagicInfuserContainer::new));
}
