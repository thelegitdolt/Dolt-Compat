package com.dolthhaven.doltcompat.core.compat;

import com.teamabnormals.environmental.core.registry.EnvironmentalBiomes;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.orcinus.goodending.init.GoodEndingBiomes;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DoltCompatVillaging {
    public static void registerAllVillaging() {
        VillagerType.BY_BIOME.put(GoodEndingBiomes.MARSHY_SWAMP_KEY,
                VillagerType.byBiome(ForgeRegistries.BIOMES.getHolder(EnvironmentalBiomes.MARSH.get()).get()));
    }
}
