package com.mitchej123.hodgepodge.mixins.late.dbc;

import JinRyuu.DragonBC.common.Npcs.RenderAuraRing;
import me.aegous.dfclient.main.DFClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderAuraRing.class)
public class MixinRenderAuraRing {
    @Inject(at = @At("HEAD"), method = "func_76986_a", remap = false, cancellable = true)
    public void injectDisableAuraRing(Entity par1Entity, double par2, double par4, double par6, float par8, float par9, CallbackInfo info){
        if (DFClient.optionConfig.get("Options Config", "disableAura", 0).getInt() == 1) {
            info.cancel();
        }
    }
}
