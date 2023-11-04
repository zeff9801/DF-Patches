package com.mitchej123.hodgepodge.mixins.early.minecraft.sound;


import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SoundHandler.class)
public interface MixinSoundHandlerAccessor {

    @Accessor
    SoundManager getSndManager();

}
