package com.mitchej123.hodgepodge.mixins.early.minecraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.world.EnumDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FoodStats.class)
public abstract class FoodStatsMixin {

    @Shadow private int prevFoodLevel;
    @Shadow private int foodLevel;
    @Shadow private float foodExhaustionLevel;
    @Shadow private float foodSaturationLevel;
    @Shadow private int foodTimer;

    @Shadow public abstract void addExhaustion(float p_75113_1_);

    @Overwrite
    public void onUpdate(EntityPlayer p_75118_1_) {
        EnumDifficulty enumdifficulty = p_75118_1_.worldObj.difficultySetting;
        this.prevFoodLevel = this.foodLevel;

        if (p_75118_1_.worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration") && this.foodLevel >= 18 && p_75118_1_.shouldHeal()) {
            ++this.foodTimer;

            if (this.foodTimer >= 80) {
                p_75118_1_.heal(1.0F);
                this.addExhaustion(3.0F);
                this.foodTimer = 0;
            }
        } else if (this.foodLevel <= 0) {
            ++this.foodTimer;

            if (this.foodTimer >= 80) {
                if (p_75118_1_.getHealth() > 10.0F || enumdifficulty == EnumDifficulty.HARD || p_75118_1_.getHealth() > 1.0F && enumdifficulty == EnumDifficulty.NORMAL) {
                    p_75118_1_.attackEntityFrom(DamageSource.starve, 1.0F);
                }

                this.foodTimer = 0;
            }
        } else {
            this.foodTimer = 0;
        }
    }
}
