package com.dolthhaven.doltcompat.core.mixin;

import com.teamabnormals.environmental.common.block.DoubleCattailBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DoubleCattailBlock.class)
public class DoubleCattailBlockMixin {

    @Inject(at = @At("RETURN"), method = "Lcom/teamabnormals/environmental/common/block/DoubleCattailBlock;isValidBonemealTarget(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)Z",
    cancellable = true)
    private void DoltCompat$AlwaysValidBonemeal(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(state.getValue(DoubleCattailBlock.AGE) < 1);
    }

    @Redirect(method = "Lcom/teamabnormals/environmental/common/block/DoubleCattailBlock;tick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V",
    at = @At(value = "INVOKE", target = "Lnet/minecraftforge/common/ForgeHooks;onCropsGrowPre(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)Z"))
    private boolean DoltCompat$onCropsGrowPre(Level level, BlockPos pos, BlockState state, boolean def) {
        return ForgeHooks.onCropsGrowPre(level, pos, state, def) && level.getBlockState(pos).getValue(DoubleCattailBlock.FAKE_WATERLOGGED);
    }
}
