package com.dolthhaven.doltcompat.common.registry;

import com.dolthhaven.doltcompat.DoltCompat;
import com.dolthhaven.doltcompat.core.SecretCode;
import com.teamabnormals.blueprint.core.util.registry.AbstractSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.MushroomColonyItem;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = DoltCompat.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DoltCompatItems {
    public static final ItemSubRegistryHelper HELPER = DoltCompat.REGISTRY_HELPER.getItemSubHelper();

    public static final RegistryObject<Item> GLOWSHROOM_COLONY = HELPER.createItem("glowshroom_colony", () -> new MushroomColonyItem(DoltCompatBlocks.GLOWSHROOM_COLONY.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
}
