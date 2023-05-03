package com.dolthhaven.doltcompat.core.mixin;

import com.teamabnormals.environmental.common.block.CattailBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(CattailBlock.class)
public class CattailBlockMixin {
    @ModifyArgs(method = "Lcom/teamabnormals/environmental/common/block/CattailBlock;placeAt(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;I)V",
    at = @At(value = "INVOKE", target = "Lcom/teamabnormals/environmental/common/block/DoubleCattailBlock;placeAt(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;IZ)V"), remap = false)
    private void DoltCompat$EnsureMudAndWaterlog(Args args) {
        LevelAccessor level = args.get(0);
        BlockPos pos = args.get(1);

        args.set(3, level.getBlockState(pos.below()).is(Blocks.MUD) && level.getBlockState(pos).is(Blocks.WATER));
    }
}
