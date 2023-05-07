package com.dolthhaven.doltcompat.core.mixin;

import com.dolthhaven.doltcompat.core.DoltCompatConfig;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.orcinus.goodending.init.GoodEndingBlocks;
import net.orcinus.goodending.world.gen.features.FallenLogFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FallenLogFeature.class)
public class FallenLogFeatureMixin {
    @ModifyArg(method = "Lnet/orcinus/goodending/world/gen/features/FallenLogFeature;place(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"), index = 1)
    private BlockState DoltCompat$ReplaceWillowLog2(BlockState state) {
        return state.is(GoodEndingBlocks.MUDDY_OAK_LOG.get()) && DoltCompatConfig.Common.COMMON.WillowReplacement.get() ?
                EnvironmentalBlocks.WILLOW_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)) : state;
    }

}
