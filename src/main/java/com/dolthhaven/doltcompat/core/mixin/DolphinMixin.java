package com.dolthhaven.doltcompat.core.mixin;

import com.dolthhaven.doltcompat.core.DoltCompatConfig;
import com.dolthhaven.doltcompat.core.SecretCode;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Dolphin.class)
public abstract class DolphinMixin extends LivingEntity {
    public DolphinMixin(EntityType<? extends Dolphin> p_28316_, Level p_28317_) {
        super(p_28316_, p_28317_);
    }

    @Shadow public abstract int getMaxAirSupply();

    @Inject(method = "Lnet/minecraft/world/entity/animal/Dolphin;tick()V", at = @At("HEAD"))
    private void DoltCompat$DolphinNoNeedToBreath(CallbackInfo ci) {
        if (DoltCompatConfig.Common.COMMON.UnrealisticDolphins.get()) {
            this.setAirSupply(this.getMaxAirSupply());
        }
    }
}