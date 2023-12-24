package com.dolthhaven.doltcompat.core.mixin.client;

import com.dolthhaven.doltcompat.core.DoltCompatConfig;
import com.teamabnormals.blueprint.client.renderer.block.BlueprintChestBlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlueprintChestBlockEntityRenderer.class)
public class BlueprintChestRendererMixin {
    @Shadow public boolean isChristmas;

    @Inject(at=@At("TAIL"), method = "Lcom/teamabnormals/blueprint/client/renderer/block/BlueprintChestBlockEntityRenderer;<init>(Lnet/minecraft/client/renderer/blockentity/BlockEntityRendererProvider$Context;)V")
    private void DoltCompat$destroyChristmasTextures(BlockEntityRendererProvider.Context context, CallbackInfo ci) {
        if (DoltCompatConfig.Common.COMMON.BahHumbug.get()) {
            this.isChristmas = false;
        }
    }
}
