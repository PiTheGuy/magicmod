package com.pitheguy.magicmod;

import com.pitheguy.magicmod.client.entity.render.*;
import com.pitheguy.magicmod.client.gui.MagicCrateScreen;
import com.pitheguy.magicmod.client.gui.MagicInfuserScreen;
import com.pitheguy.magicmod.client.gui.MagicPressScreen;
import com.pitheguy.magicmod.init.ModContainerTypes;
import com.pitheguy.magicmod.init.ModEntityTypes;
import com.pitheguy.magicmod.init.ModTileEntityTypes;
import com.pitheguy.magicmod.items.ModSpawnEggItem;
import com.pitheguy.magicmod.util.RegistryHandler;
import com.pitheguy.magicmod.world.gen.ModOreGen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("magicmod")
@Mod.EventBusSubscriber(modid = "magicmod", bus = EventBusSubscriber.Bus.MOD)
public class MagicMod
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "magicmod";

    public MagicMod() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        //modEventBus.addListener(this::setup);

        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        RegistryHandler.init();

        ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        ModContainerTypes.CONTAINER_TYPES.register(modEventBus);
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

    }

    /*private void setup(final FMLCommonSetupEvent event)
    {

    }*/

    private void doClientStuff(final FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ModContainerTypes.MAGIC_INFUSER.get(), MagicInfuserScreen::new);
        ScreenManager.registerFactory(ModContainerTypes.MAGIC_CRATE.get(), MagicCrateScreen::new);
        ScreenManager.registerFactory(ModContainerTypes.MAGIC_PRESS.get(), MagicPressScreen::new);
        RenderTypeLookup.setRenderLayer(RegistryHandler.MAGIC_VEIN.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(RegistryHandler.MAGIC_WEB.get(), RenderType.getCutout());
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.MAGIC_FRIEND.get(), MagicFriendRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.FLUFFY_MAGICIAN.get(), FluffyMagicianRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.FLUFFY_MAGICIAN_BARE.get(), FluffyMagicianBareRender::new);
    }

    @SubscribeEvent
    public static void loadCompleteEvent(FMLLoadCompleteEvent event) {
        ModOreGen.generateOre();
    }

    @SubscribeEvent
    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
        ModSpawnEggItem.initSpawnEggs();
    }

    public static final ItemGroup TAB = new ItemGroup("magicTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(RegistryHandler.MAGIC_GEM.get());
        }
    };

}
