package com.dolthhaven.doltcompat.core.mixin;

import com.teamabnormals.neapolitan.core.registry.NeapolitanBlocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(at = @At("RETURN"), method = "isInWaterOrRain", cancellable = true)
    private void DoltCompat$YouAreNowWetInMilk(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) ((Object) this);
        BlockState state = entity.level.getBlockState(entity.blockPosition());
        if (state.is(NeapolitanBlocks.MILK_CAULDRON.get()) && state.getValue(LayeredCauldronBlock.LEVEL) > 0 && ModList.get().isLoaded("allurement"))
            cir.setReturnValue(true);
    }

}