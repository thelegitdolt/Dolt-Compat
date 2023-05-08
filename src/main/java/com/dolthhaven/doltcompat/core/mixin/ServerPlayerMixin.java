package com.dolthhaven.doltcompat.core.mixin;

import com.dolthhaven.doltcompat.core.DoltCompatConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @Inject(method = "Lnet/minecraft/server/level/ServerPlayer;bedInRange(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z",
    at = @At("RETURN"), cancellable = true)
    private void DoltCompat$NoMoreTooFarAwayBS(BlockPos pos, Direction dir, CallbackInfoReturnable<Boolean> cir) {
        if (DoltCompatConfig.Common.COMMON.ReliableSleep.get()) cir.setReturnValue(true);
    }
}
