package com.mitchej123.hodgepodge.mixins.late.jrmcore;

import JinRyuu.JRMCore.JRMCoreComTickH;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(JRMCoreComTickH.class)
public interface IJRMCoreComTickH {
    @Accessor(value = "height", remap = false)
    static float getHeight() {
        throw new AssertionError();
    }

    @Accessor(value = "width", remap = false)
    static float getWidth() {
        throw new AssertionError();
    }

    @Invoker(value = "sS", remap = false)
    static void invokeSS(EntityPlayer p, float par1, float par2) {
        throw new AssertionError();
    }
}
