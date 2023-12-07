package com.mitchej123.hodgepodge.mixins.late.jrmcore;

import JinRyuu.JRMCore.entity.EntityEnergyAtt;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityEnergyAtt.class)
public abstract class MixinEntityEnergyAtt{


    @Shadow public abstract boolean isWave();

    @Shadow public boolean shooterHolds;

    @Shadow public abstract float strtX();

    @Shadow private float strtY;

    @Shadow private float strtZ;

    @Overwrite (remap = false)
    public long getPower(Entity entity) {
        return 0;
    }

    /**
     * @author 1ost_
     * @reason Fix the ear rape
     */
    @Overwrite (remap = false)
    private void playSoundAtEntity(Entity entity, String s, float f, float f1) {
        entity.worldObj.playSoundAtEntity(entity, s, 0.3f, f1);
        if (this.isWave() && this.shooterHolds) {
            entity.worldObj.playSoundEffect((double)this.strtX(), (double)this.strtY, (double)this.strtZ, s, 0.3f, f1);
        }

    }

}
