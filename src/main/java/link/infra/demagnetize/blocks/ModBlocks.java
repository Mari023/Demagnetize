package link.infra.demagnetize.blocks;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {
	@ObjectHolder(registryName = "block", value = "demagnetize:demagnetizer")
	public static Demagnetizer DEMAGNETIZER;

	@ObjectHolder(registryName = "block", value = "demagnetize:demagnetizer_advanced")
	public static Demagnetizer DEMAGNETIZER_ADVANCED;

	@ObjectHolder(registryName = "block_entity_type", value = "demagnetize:demagnetizer")
	static BlockEntityType<DemagnetizerTileEntity> DEMAGNETIZER_TILE_ENTITY;

	@ObjectHolder(registryName = "block_entity_type", value = "demagnetize:demagnetizer_advanced")
	static BlockEntityType<DemagnetizerTileEntity> DEMAGNETIZER_ADVANCED_TILE_ENTITY;

	@ObjectHolder(registryName = "menu", value = "demagnetize:demagnetizer")
	public static MenuType<DemagnetizerContainer> DEMAGNETIZER_CONTAINER;
}