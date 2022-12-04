package com.mitchej123.hodgepodge.mixins.late.jrmcore;

import JinRyuu.JRMCore.JRMCoreKeyHandler;
import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(JRMCoreKeyHandler.class)
public interface IJRMCoreKeyHandler {
    @Accessor(value = "DS", remap = false)
    static KeyBinding getDS() {
        throw new AssertionError();
    }

    @Accessor(value = "infopanel", remap = false)
    static KeyBinding getInfopanel() {
        throw new AssertionError();
    }
}
