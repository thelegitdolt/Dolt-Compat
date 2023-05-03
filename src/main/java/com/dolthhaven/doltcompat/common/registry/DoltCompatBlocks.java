package com.dolthhaven.doltcompat.common.registry;


import com.dolthhaven.doltcompat.DoltCompat;
import com.dolthhaven.doltcompat.common.blocks.GlowshroomColonyBlock;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import vazkii.quark.content.world.module.GlimmeringWealdModule;

@Mod.EventBusSubscriber(modid = DoltCompat.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DoltCompatBlocks {
    public static final BlockSubRegistryHelper HELPER = DoltCompat.REGISTRY_HELPER.getBlockSubHelper();

    public static final RegistryObject<Block> GLOWSHROOM_COLONY = HELPER.createBlockNoItem("glowshroom_colony", () -> new
            GlowshroomColonyBlock(BlockBehaviour.Properties.copy(GlimmeringWealdModule.glow_shroom)));

}
