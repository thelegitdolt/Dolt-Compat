package com.dolthhaven.doltcompat.core.mixin;

import com.dolthhaven.doltcompat.core.DoltCompatConfig;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.orcinus.goodending.init.GoodEndingBlocks;
import net.orcinus.goodending.world.gen.features.DuckweedFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DuckweedFeature.class)
public class DuckweedFeatureMixin {
    @ModifyArg(method = "Lnet/orcinus/goodending/world/gen/features/DuckweedFeature;generateDuckweed(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"), index = 1)
    private static BlockState DoltCompat$ReplaceDuckweed(BlockState state) {
        return state.is(GoodEndingBlocks.DUCKWEED.get()) && DoltCompatConfig.Common.COMMON.DuckweedReplacement.get() ? EnvironmentalBlocks.DUCKWEED.get().defaultBlockState() : state;
    }
}
