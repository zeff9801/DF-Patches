package com.mitchej123.hodgepodge.mixins.early.minecraft.sound;

import net.minecraft.client.audio.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import paulscode.sound.SoundSystem;

@Mixin(SoundManager.class)
public interface MixinSoundManagerAccessor {
//    @Accessor
//    SoundManager.SoundSystemStarterThread getSndSystem();


}
