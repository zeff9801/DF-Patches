package com.mitchej123.hodgepodge.mixins.early.minecraft.sound;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import paulscode.sound.Library;
import paulscode.sound.SoundSystem;

@Mixin(SoundSystem.class)
public interface MixinSoundSystemAccessor {

    @Accessor
    Library getSoundLibrary();
}
