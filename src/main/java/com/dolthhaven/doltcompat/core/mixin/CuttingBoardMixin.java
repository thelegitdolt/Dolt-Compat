package com.dolthhaven.doltcompat.core.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;

@Mixin(CuttingBoardBlock.class)
public class CuttingBoardMixin {

    @Inject(method = "Lvectorwing/farmersdelight/common/block/CuttingBoardBlock;getAnalogOutputSignal(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)I",
            at = @At("RETURN"), cancellable = true)
    private void DoltCompat$ToolDurabilityCuttingBoardComparatorVal(BlockState blockState, Level level, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        BlockEntity enty = level.getBlockEntity(pos);
        if (enty instanceof CuttingBoardBlockEntity CBE && CBE.isItemCarvingBoard()) {
            ItemStack item = CBE.getStoredItem();
            cir.setReturnValue(
                    15 - (int) (((float) item.getDamageValue() / (float) item.getMaxDamage()) * 15)
            );
        }
    }
}
