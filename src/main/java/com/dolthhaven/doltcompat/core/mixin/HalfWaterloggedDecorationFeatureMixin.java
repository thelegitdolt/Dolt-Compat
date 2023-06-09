package com.dolthhaven.doltcompat.core.mixin;

import com.teamabnormals.environmental.common.block.CattailBlock;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.orcinus.goodending.init.GoodEndingBlocks;
import net.orcinus.goodending.world.gen.features.HalfWaterloggedDecorationFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


// Mixin HalfWaterloggedDecoratorFeature to always spawn environmental cattails

// Will break if HalfWaterloggedDecoratorFeature is reused
@Mixin(HalfWaterloggedDecorationFeature.class)
public class HalfWaterloggedDecorationFeatureMixin {

    @Redirect(method = "Lnet/orcinus/goodending/world/gen/features/HalfWaterloggedDecorationFeature;place(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/DoublePlantBlock;placeAt(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;I)V"))
    private void DoltCompat$placeAt(LevelAccessor world, BlockState state, BlockPos pos, int weirdNumber) {
        if (state.is(GoodEndingBlocks.CATTAIL.get())) {
            if ((world.isWaterAt(pos) || world.isEmptyBlock(pos)) && world.isEmptyBlock(pos.above()) && EnvironmentalBlocks.CATTAIL.get().defaultBlockState().canSurvive(world, pos)) {
                ((CattailBlock) EnvironmentalBlocks.CATTAIL.get()).placeAt(world, pos, weirdNumber);
            }
        }
        else {
            DoublePlantBlock.placeAt(world, state, pos, weirdNumber);
        }
    }

}