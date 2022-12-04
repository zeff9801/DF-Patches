package com.mitchej123.hodgepodge.mixins.late.jrmcore;

import JinRyuu.JRMCore.entity.EntityEnergyAtt;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityEnergyAtt.class)
public class MixinEntityEnergyAtt {
    @Overwrite (remap = false)
    public long getPower(Entity entity) {
        return 0;
    }
}
