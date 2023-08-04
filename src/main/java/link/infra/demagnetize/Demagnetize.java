package link.infra.demagnetize;

import link.infra.demagnetize.blocks.*;
import link.infra.demagnetize.items.BlockItemClearConfiguration;
import link.infra.demagnetize.items.ModItems;
import link.infra.demagnetize.network.PacketHandler;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod(Demagnetize.MODID)
public class Demagnetize {
	public static final String MODID = "demagnetize";

	public Demagnetize() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_CONFIG);
		ConfigHandler.loadConfig(ConfigHandler.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("demagnetize-common.toml"));
	}

	private void setupClient(final FMLClientSetupEvent event) {
		MenuScreens.register(ModBlocks.DEMAGNETIZER_CONTAINER, DemagnetizerGui::new);
	}

	private void setupCommon(final FMLCommonSetupEvent event) {
		PacketHandler.registerMessages();
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		@SubscribeEvent
		public static void onRegistry(final RegisterEvent event) {
			event.register(ForgeRegistries.Keys.BLOCKS,
					helper -> {
						helper.register(new ResourceLocation(MODID, "demagnetizer"), new Demagnetizer(false));
						helper.register(new ResourceLocation(MODID, "demagnetizer_advanced"), new Demagnetizer(true));
					}
			);

			if(event.getRegistryKey().equals(Registries.CREATIVE_MODE_TAB))
				ModItems.initCreativeModeTab(event.getVanillaRegistry());

			event.register(ForgeRegistries.Keys.ITEMS,
					helper -> {
						helper.register(new ResourceLocation(MODID, "demagnetizer"), new BlockItemClearConfiguration(ModBlocks.DEMAGNETIZER));
						helper.register(new ResourceLocation(MODID, "demagnetizer_advanced"), new BlockItemClearConfiguration(ModBlocks.DEMAGNETIZER_ADVANCED));
					}
			);

			event.register(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES,
					helper -> {
						// For some reason the parameter to build() is marked as @Nonnull
						//noinspection ConstantConditions
						helper.register(new ResourceLocation(MODID, "demagnetizer"), BlockEntityType.Builder.of((blockPos, blockState) -> new DemagnetizerTileEntity(false, blockPos, blockState), ModBlocks.DEMAGNETIZER).build(null));
						//noinspection ConstantConditions
						helper.register(new ResourceLocation(MODID, "demagnetizer_advanced"), BlockEntityType.Builder.of((blockPos, blockState) -> new DemagnetizerTileEntity(true, blockPos, blockState), ModBlocks.DEMAGNETIZER_ADVANCED).build(null));
					}
			);

			event.register(ForgeRegistries.Keys.MENU_TYPES,
					helper -> helper.register(new ResourceLocation(MODID, "demagnetizer"), IForgeMenuType.create((windowId, inv, data) -> {
						BlockPos pos = data.readBlockPos();
						return new DemagnetizerContainer(windowId, inv.player.level(), pos, inv);
					}))
			);
		}
	}
}
