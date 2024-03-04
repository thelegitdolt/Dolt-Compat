package com.dolthhaven.doltcompat.core.mixin;

import com.teamabnormals.environmental.common.block.CattailBlock;
import com.teamabnormals.environmental.common.block.CattailStalkBlock;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.orcinus.goodending.init.GoodEndingBlocks;
import net.orcinus.goodending.world.gen.features.ShallowWaterFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ShallowWaterFeature.class)
public class ShallowWaterFeatureMixin {

    @Redirect(method = "placeSquare(Lnet/orcinus/goodending/world/gen/features/config/ShallowWaterConfig;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/util/RandomSource;I)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/DoublePlantBlock;placeAt(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;I)V"))
    private void DoltCompat$placeAt(LevelAccessor level, BlockState state, BlockPos pos, int weirdNumber) {
        if (state.is(GoodEndingBlocks.CATTAIL.get()) && ((level.isWaterAt(pos) || level.isEmptyBlock(pos)) && level.isEmptyBlock(pos.above())
                && EnvironmentalBlocks.CATTAIL.get().defaultBlockState().canSurvive(level, pos))) {

            RandomSource random = level.getRandom();
            int stalkCount = level.getRandom().nextInt(1, 3);
            boolean fluffy = level.getBlockState(pos.below()).is(Blocks.MUD);

            level.setBlock(pos, EnvironmentalBlocks.CATTAIL_STALK.get().defaultBlockState().setValue(CattailBlock.CATTAILS, stalkCount).setValue(BlockStateProperties.WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER), 2);

            if (level.isWaterAt(pos.above()) || (level.isEmptyBlock(pos.above(2)) && random.nextBoolean())) {
                level.setBlock(pos.above(), EnvironmentalBlocks.CATTAIL_STALK.get().defaultBlockState().setValue(CattailBlock.CATTAILS, stalkCount).setValue(CattailStalkBlock.BOTTOM, false).setValue(BlockStateProperties.WATERLOGGED, level.getFluidState(pos.above()).getType() == Fluids.WATER), 2);
                if (level.isWaterAt(pos.above()) && random.nextInt(3) == 0) {
                    level.setBlock(pos.above(2), EnvironmentalBlocks.CATTAIL_STALK.get().defaultBlockState().setValue(CattailBlock.CATTAILS, stalkCount).setValue(CattailStalkBlock.BOTTOM, false).setValue(BlockStateProperties.WATERLOGGED, level.getFluidState(pos.above(2)).getType() == Fluids.WATER), 2);
                    level.setBlock(pos.above(3), EnvironmentalBlocks.CATTAIL.get().defaultBlockState().setValue(CattailBlock.CATTAILS, stalkCount).setValue(CattailBlock.AGE, 2).setValue(CattailBlock.FLUFFY, fluffy).setValue(BlockStateProperties.WATERLOGGED, level.getFluidState(pos.above(3)).getType() == Fluids.WATER).setValue(CattailBlock.TOP, true), 2);
                }
                else {
                    level.setBlock(pos.above(2), EnvironmentalBlocks.CATTAIL.get().defaultBlockState().setValue(CattailBlock.CATTAILS, stalkCount).setValue(CattailBlock.AGE, 2).setValue(CattailBlock.FLUFFY, fluffy).setValue(BlockStateProperties.WATERLOGGED, level.getFluidState(pos.above(2)).getType() == Fluids.WATER).setValue(CattailBlock.TOP, true), 2);
                }
            }
            else {
                level.setBlock(pos.above(), EnvironmentalBlocks.CATTAIL.get().defaultBlockState().setValue(CattailBlock.CATTAILS, stalkCount).setValue(CattailBlock.AGE, 2).setValue(CattailBlock.FLUFFY, fluffy).setValue(BlockStateProperties.WATERLOGGED, level.getFluidState(pos.above()).getType() == Fluids.WATER).setValue(CattailBlock.TOP, true), 2);
            }
        }
        else {
            DoublePlantBlock.placeAt(level, state, pos, weirdNumber);
        }
    }

}
