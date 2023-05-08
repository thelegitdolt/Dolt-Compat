package com.dolthhaven.doltcompat.core.mixin;

import com.dolthhaven.doltcompat.core.DoltCompatConfig;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Monster.class)
public class MonsterMixin {
    @Inject(method = "isPreventingPlayerRest(Lnet/minecraft/world/entity/player/Player;)Z",
    at = @At("RETURN"), cancellable = true)
    private void DoltCompat$NoMoreMonsterNearbyBS(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (DoltCompatConfig.Common.COMMON.ReliableSleep.get()) cir.setReturnValue(false);
    }
}
