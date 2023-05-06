package com.dolthhaven.doltcompat.core.mixin;

import com.dolthhaven.doltcompat.core.DoltCompatConfig;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.orcinus.goodending.init.GoodEndingBlocks;
import net.orcinus.goodending.world.gen.features.MuddyOakTreeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MuddyOakTreeFeature.class)
public class MuddyOakFeatureMixin {
    @ModifyArg(method = "Lnet/orcinus/goodending/world/gen/features/MuddyOakTreeFeature;place(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"),
    index = 1)
    private BlockState DoltCompat$OakToWillow(BlockState state) {
        if (DoltCompatConfig.Common.COMMON.WillowReplacement.get()) {
            if (state.is(GoodEndingBlocks.MUDDY_OAK_LOG.get())) {
                return EnvironmentalBlocks.WILLOW_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            } else if (state.is(Blocks.OAK_LEAVES)) {
                return EnvironmentalBlocks.WILLOW_LEAVES.get().defaultBlockState()
                        .setValue(BlockStateProperties.DISTANCE, state.getValue(BlockStateProperties.DISTANCE));
            }
        }
        return state;
    }

    @ModifyArg(method = "lambda$generateHangingVines$3(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/core/BlockPos;)Z",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private static Block DoltCompat$WillowCanFall(Block block) {
        return block.equals(Blocks.OAK_LEAVES) && DoltCompatConfig.Common.COMMON.WillowReplacement.get() ? EnvironmentalBlocks.WILLOW_LEAVES.get() : block;
    }
}
