package com.dolthhaven.doltcompat.core.mixin.client;

import com.dolthhaven.doltcompat.core.DoltCompatConfig;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ChestRenderer.class)
public class ChestRendererMixin {
    @Shadow private boolean xmasTextures;

    @Inject(method = "Lnet/minecraft/client/renderer/blockentity/ChestRenderer;<init>(Lnet/minecraft/client/renderer/blockentity/BlockEntityRendererProvider$Context;)V",
            at = @At(value = "TAIL"))
    private void DoltCompat$noChristmasChest(BlockEntityRendererProvider.Context context, CallbackInfo ci) {

        if (DoltCompatConfig.Common.COMMON.BahHumbug.get()) {
            this.xmasTextures = false;
        }
    }
}
