package com.dolthhaven.doltcompat.core.mixin;

import com.dolthhaven.doltcompat.core.DoltCompatConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Monster;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @Inject(method = "bedInRange(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z",
    at = @At("RETURN"), cancellable = true)
    private void DoltCompat$NoMoreTooFarAwayBS(BlockPos pos, Direction dir, CallbackInfoReturnable<Boolean> cir) {
        if (DoltCompatConfig.Common.COMMON.ReliableSleep.get()) cir.setReturnValue(true);
    }

    @Inject(method = "lambda$startSleepInBed$7(Lnet/minecraft/world/entity/monster/Monster;)Z", at = @At("RETURN"), cancellable = true, remap = false)
    private void DoltCompat$NoMoreMonsterNearbyBS(Monster monster, CallbackInfoReturnable<Boolean> cir) {
        if (DoltCompatConfig.Common.COMMON.ReliableSleep.get()) cir.setReturnValue(false);
    }
}
