package com.pitheguy.magicmod;

import com.pitheguy.magicmod.client.entity.model.*;
import com.pitheguy.magicmod.client.entity.render.*;
import com.pitheguy.magicmod.client.gui.*;
import com.pitheguy.magicmod.entities.FluffyMagician;
import com.pitheguy.magicmod.entities.MagicFriend;
import com.pitheguy.magicmod.init.*;
import com.pitheguy.magicmod.util.RegistryHandler;
import com.pitheguy.magicmod.world.gen.ModOreGen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("magicmod")
@Mod.EventBusSubscriber(modid = "magicmod", bus = EventBusSubscriber.Bus.MOD)
public class MagicMod {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "magicmod";

    public MagicMod() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        RegistryHandler.init();

        ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        ModContainerTypes.CONTAINER_TYPES.register(modEventBus);
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModOreGen::registerOres);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        MenuScreens.register(ModContainerTypes.MAGIC_INFUSER.get(), MagicInfuserScreen::new);
        MenuScreens.register(ModContainerTypes.MAGIC_CRATE.get(), MagicCrateScreen::new);
        MenuScreens.register(ModContainerTypes.MAGIC_PRESS.get(), MagicPressScreen::new);
        MenuScreens.register(ModContainerTypes.MAGIC_ENERGIZER.get(), MagicEnergizerScreen::new);
        MenuScreens.register(ModContainerTypes.MAGIC_MINER.get(), MagicMinerScreen::new);
        MenuScreens.register(ModContainerTypes.MAGIC_LOGGER.get(), MagicLoggerScreen::new);
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.MAGIC_VEIN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.MAGIC_WEB.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void loadCompleteEvent(FMLLoadCompleteEvent event) {
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MagicFriendModel.LAYER_LOCATION, MagicFriendModel::createBodyLayer);
        event.registerLayerDefinition(FluffyMagicianModel.LAYER_LOCATION, FluffyMagicianModel::createBodyLayer);
        event.registerLayerDefinition(FluffyMagicianBareModel.LAYER_LOCATION, FluffyMagicianBareModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.MAGIC_FRIEND.get(), MagicFriendRender::new);
        event.registerEntityRenderer(ModEntityTypes.FLUFFY_MAGICIAN.get(), FluffyMagicianRender::new);
        event.registerEntityRenderer(ModEntityTypes.FLUFFY_MAGICIAN_BARE.get(), FluffyMagicianBareRender::new);
    }

    @SubscribeEvent
    public static void onRegisterEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.FLUFFY_MAGICIAN.get(), FluffyMagician.createAttributes());
        event.put(ModEntityTypes.FLUFFY_MAGICIAN_BARE.get(), FluffyMagician.createAttributes());
        event.put(ModEntityTypes.MAGIC_FRIEND.get(), MagicFriend.createAttributes());
    }

    public static final CreativeModeTab TAB = new CreativeModeTab("magicTab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RegistryHandler.MAGIC_GEM.get());
        }
    };

}
