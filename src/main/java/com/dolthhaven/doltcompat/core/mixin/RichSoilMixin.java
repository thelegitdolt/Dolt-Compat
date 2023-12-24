package com.dolthhaven.doltcompat.core.mixin;

import com.dolthhaven.doltcompat.common.registry.DoltCompatBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.quark.content.world.module.GlimmeringWealdModule;
import vectorwing.farmersdelight.common.block.RichSoilBlock;

@Mixin(RichSoilBlock.class)
public class RichSoilMixin {

    @Inject(method = "Lvectorwing/farmersdelight/common/block/RichSoilBlock;randomTick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V",
    at = @At("HEAD"))
    private void DoltCompat$GrowGlowshroom(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand, CallbackInfo ci) {
        if (!level.isClientSide) {
            BlockPos abovePos = pos.above();
            BlockState aboveState = level.getBlockState(abovePos);
            Block aboveBlock = aboveState.getBlock();

            if (aboveBlock == GlimmeringWealdModule.glow_shroom) {
                level.setBlockAndUpdate(pos.above(), DoltCompatBlocks.GLOWSHROOM_COLONY.get().defaultBlockState());
            }
        }
    }
}
