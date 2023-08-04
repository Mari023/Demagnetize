package link.infra.demagnetize.items;

import link.infra.demagnetize.Demagnetize;
import link.infra.demagnetize.blocks.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItems {
    public static void initCreativeModeTab(Registry<CreativeModeTab> registry) {
        var tab = CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.demagnetize"))
                .icon(() -> new ItemStack(ModBlocks.DEMAGNETIZER))
                .displayItems(ModItems::buildDisplayItems)
                .build();
        Registry.register(registry, new ResourceLocation(Demagnetize.MODID, "main"), tab);
    }

    private static void buildDisplayItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters,
                                          CreativeModeTab.Output output) {
        output.accept(ModBlocks.DEMAGNETIZER);
        output.accept(ModBlocks.DEMAGNETIZER_ADVANCED);
    }
}
