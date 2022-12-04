package com.mitchej123.hodgepodge.mixins.late.dbc;

import JinRyuu.DragonBC.common.Npcs.EntityAura2;
import JinRyuu.JRMCore.client.config.jrmc.JGConfigClientSettings;
import JinRyuu.JRMCore.entity.EntityCusPar;
import JinRyuu.JRMCore.entity.EntityCusPars;
import JinRyuu.JRMCore.mod_JRMCore;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityAura2.class)
public abstract class MixinEntityAura2 extends Entity{
    @Shadow @Final public int number_of_lightVerts;
    @Shadow public long[] lightVert;
    @Shadow private int lightLivingTime;
    @Shadow public int rm;
    @Shadow private String mot;
    @Shadow private boolean rot;
    @Shadow private int Age;
    @Shadow private int color;
    @Shadow private int colorl2;
    @Shadow private int colorl3;
    @Shadow private float state;
    @Shadow private float state2;
    @Shadow private int crel;
    @Shadow private float yaw;
    @Shadow private float pitch;
    @Shadow private float alpha;
    @Shadow private String tex;
    @Shadow private String texl2;
    @Shadow private String texl3;
    @Shadow private int speed;
    @Shadow private boolean inner;
    @Shadow private int rendpass;
    @Shadow private boolean bol;
    @Shadow private boolean bol2;
    @Shadow private boolean bol3;
    @Shadow private boolean bol4;
    @Shadow private boolean bol4a;
    @Shadow private byte bol6;
    @Shadow private boolean bol7;

    public MixinEntityAura2(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    @Shadow public abstract int getLightLivingTime();

    @Shadow public abstract boolean shouldRenderInPass(int pass);

    @Shadow public abstract float func_70053_R();

    @Shadow public abstract boolean getRot();

    @Shadow public abstract float getYaw();

    @Shadow public abstract float getPitch();

    @Shadow public abstract int getAge();

    @Shadow public abstract float getState();

    @Shadow public abstract float getState2();

    @Shadow public abstract float getCRel();

    @Shadow public abstract int getCol();

    @Shadow public abstract void setCol(int c);

    @Shadow public abstract int getColL2();

    @Shadow public abstract void setColL2(int c);

    @Shadow public abstract int getColL3();

    @Shadow public abstract void setColL3(int c);

    @Shadow public abstract float getAlp();

    @Shadow public abstract void setAlp(float f);

    @Shadow public abstract String getTex();

    @Shadow public abstract void setTex(String s);

    @Shadow public abstract String getTexL2();

    @Shadow public abstract void setTexL2(String s);

    @Shadow public abstract String getTexL3();

    @Shadow public abstract void setTexL3(String s);

    @Shadow public abstract int getSpd();

    @Shadow public abstract void setSpd(int s);

    @Shadow public abstract boolean getInner();

    @Shadow public abstract void setInner(boolean s);

    @Shadow public abstract void setRendPass(int s);

    @Shadow public abstract String getmot();

    @Shadow public abstract void setBol(boolean b);

    @Shadow public abstract void setBol2(boolean b);

    @Shadow public abstract void setBol3(boolean b);

    @Shadow public abstract void setBol4(boolean b);

    @Shadow public abstract void setBol4a(boolean b);

    @Shadow public abstract void setBol6(int b);

    @Shadow public abstract void setBol7(boolean b);

    @Shadow public abstract boolean getBol();

    @Shadow public abstract boolean getBol2();

    @Shadow public abstract boolean getBol3();

    @Shadow public abstract boolean getBol4();

    @Shadow public abstract boolean getBol4a();

    @Shadow public abstract byte getBol6();

    @Shadow public abstract boolean getBol7();

    @Shadow public abstract void func_70071_h_();

    @Shadow public abstract boolean getCanSpawnHere();

    @Shadow public abstract void onLivingUpdate();

    @Shadow protected abstract void func_70037_a(NBTTagCompound par1);

    @Shadow protected abstract void func_70014_b(NBTTagCompound par1);

    @Shadow protected abstract void func_70088_a();

    /**
     * @Reason Remove the 2D aura particles as they are big performance killers
     */
    @Overwrite(remap = false)
    public void onUpdate() {
        if (this.mot.length() > 1) {
            Entity other = this.worldObj.getPlayerEntityByName(this.mot);
            if (other != null) {
                int sneak = other.isSneaking() ? 0 : 1;

                if (JGConfigClientSettings.CLIENT_GR1 || JGConfigClientSettings.CLIENT_GR8 || JGConfigClientSettings.CLIENT_GR9) {
                    for(int k = 0; k < JGConfigClientSettings.get_da1(); ++k) {
                        int[] tavolsagok = new int[]{5, 6, 8, 10, 20};
                        int tav = 20;
                        boolean b1 = false;
                        boolean b2 = false;
                        String[] s = new String[]{"dirt", "grass", "rock", "stone"};

                        for(int j = 0; j < 5; ++j) {
                            if (!b2
                                    && this.worldObj
                                    .getBlock((int)this.posX, (int)this.posY - (sneak + j), (int)this.posZ)
                                    .getUnlocalizedName()
                                    .toLowerCase()
                                    .contains("grass")) {
                                b2 = true;
                                tav = tavolsagok[j];
                            }

                            for(int i = 0; i < s.length; ++i) {
                                if (!b1
                                        && this.worldObj
                                        .getBlock((int)this.posX, (int)this.posY - (sneak + j), (int)this.posZ)
                                        .getUnlocalizedName()
                                        .toLowerCase()
                                        .contains(s[i])) {
                                    b1 = true;
                                    tav = tavolsagok[j];
                                }
                            }
                        }

                        if (this.ticksExisted == 1) {
                            b1 = false;
                            b2 = false;
                            boolean b2x = false;
                            String[] sx = new String[]{"dirt", "grass", "rock", "stone"};

                            for(int j = 0; j < 5; ++j) {
                                if (!b2x
                                        && (this.bol2 || this.bol)
                                        && this.worldObj
                                        .getBlock((int)this.posX, (int)this.posY - (sneak + j), (int)this.posZ)
                                        .getUnlocalizedName()
                                        .toLowerCase()
                                        .contains("grass")) {
                                    b2x = true;
                                    b1 = j == 1;
                                }

                                for(int i = 0; i < sx.length; ++i) {
                                    if (!b2
                                            && (this.bol2 || this.bol)
                                            && this.worldObj
                                            .getBlock((int)this.posX, (int)this.posY - (sneak + j), (int)this.posZ)
                                            .getUnlocalizedName()
                                            .toLowerCase()
                                            .contains(sx[i])) {
                                        b2 = true;
                                        b1 = j == 1;
                                    }
                                }
                            }

                            if (JGConfigClientSettings.CLIENT_GR8 && b2x) {
                                float spe = 10.0F;
                                Entity entity;
                                if (!JGConfigClientSettings.CLIENT_GR11) {
                                    double x = Math.random() * (double)spe - (double)(spe / 2.0F);
                                    double y = (double)(-0.2F - (b1 == true ? 1 : 0));
                                    double z = Math.random() * (double)spe - (double)(spe / 2.0F);
                                    entity = new EntityCusPar(
                                            "jinryuudragonbc:bens_particles.png",
                                            this.worldObj,
                                            1.0F,
                                            1.0F,
                                            super.posX,
                                            super.posY,
                                            super.posZ,
                                            x,
                                            y,
                                            z,
                                            0.01 + Math.random() * 0.1F - 0.05F,
                                            0.05 + Math.random() * 0.2F,
                                            0.01 + Math.random() * 0.1F - 0.05F,
                                            (float)(Math.random() * 0.001F) - 5.0E-4F,
                                            (int)(Math.random() * 8.0) + 16,
                                            16,
                                            8,
                                            32,
                                            true,
                                            0.5F,
                                            false,
                                            0.0F,
                                            1,
                                            "",
                                            160,
                                            0,
                                            0.005F + (float)(Math.random() * 0.01F),
                                            0.0F,
                                            0.0F,
                                            0,
                                            255.0F,
                                            255.0F,
                                            255.0F,
                                            0.0F,
                                            0.0F,
                                            0.0F,
                                            0.0F,
                                            0.0F,
                                            0.0F,
                                            2,
                                            0.7F,
                                            0.0F,
                                            0.95F,
                                            1.0F,
                                            0.01F,
                                            false,
                                            -1,
                                            false,
                                            null
                                    );
                                } else {
                                    double x = Math.random() * (double)spe - (double)(spe / 2.0F);
                                    double y = (double)(-0.2F - (b1 == true ? 1 : 0));
                                    double z = Math.random() * (double)spe - (double)(spe / 2.0F);
                                    entity = new EntityCusPar(
                                            "jinryuudragonbc:bens_particles.png",
                                            this.worldObj,
                                            1.0F,
                                            1.0F,
                                            super.posX,
                                            super.posY,
                                            super.posZ,
                                            x,
                                            y,
                                            z,
                                            0.01 + Math.random() * 0.1F - 0.05F,
                                            0.05 + Math.random() * 0.2F,
                                            0.01 + Math.random() * 0.1F - 0.05F,
                                            (float)(Math.random() * 0.001F) - 5.0E-4F,
                                            (int)(Math.random() * 8.0) + 16,
                                            16,
                                            8,
                                            32,
                                            true,
                                            0.5F,
                                            false,
                                            0.0F,
                                            1,
                                            "",
                                            160,
                                            0,
                                            0.005F + (float)(Math.random() * 0.01F),
                                            0.0F,
                                            0.0F,
                                            0,
                                            255.0F,
                                            255.0F,
                                            255.0F,
                                            0.0F,
                                            0.0F,
                                            0.0F,
                                            0.0F,
                                            0.0F,
                                            0.0F,
                                            2,
                                            0.7F,
                                            0.0F,
                                            0.95F,
                                            1.0F,
                                            0.01F,
                                            true,
                                            0,
                                            false,
                                            null
                                    );
                                }

                                entity.worldObj.spawnEntityInWorld(entity);
                            }

                            if (JGConfigClientSettings.CLIENT_GR1 && b2) {
                                float spe2 = 10.0F;
                                if (!JGConfigClientSettings.CLIENT_GR11) {
                                    double x = Math.random() * (double)spe2 - (double)(spe2 / 2.0F);
                                    double y = (double)(-0.2F - (b1 ? 1 : 0));
                                    double z = Math.random() * (double)spe2 - (double)(spe2 / 2.0F);
                                    Entity entity = new EntityCusPar(
                                            "jinryuudragonbc:bens_particles.png",
                                            this.worldObj,
                                            1.0F,
                                            1.0F,
                                            super.posX,
                                            super.posY,
                                            super.posZ,
                                            x,
                                            y,
                                            z,
                                            0.0 + Math.random() * 0.1F - 0.05F,
                                            0.05 + (double)((float)(Math.random() * 0.2F)),
                                            0.0 + Math.random() * 0.1F - 0.05F,
                                            0.0F,
                                            (int)(Math.random() * 13.0),
                                            0,
                                            13,
                                            32,
                                            true,
                                            0.5F,
                                            false,
                                            0.0F,
                                            1,
                                            "",
                                            160,
                                            0,
                                            0.005F + (float)(Math.random() * 0.02F),
                                            0.0F,
                                            0.0F,
                                            0,
                                            255.0F,
                                            255.0F,
                                            255.0F,
                                            0.0F,
                                            0.0F,
                                            0.0F,
                                            0.0F,
                                            0.0F,
                                            0.0F,
                                            2,
                                            0.7F,
                                            0.0F,
                                            0.95F,
                                            1.0F,
                                            0.01F,
                                            false,
                                            -1,
                                            false,
                                            null
                                    );
                                    entity.worldObj.spawnEntityInWorld(entity);
                                } else {
                                    double x = Math.random() * (double)spe2 - (double)(spe2 / 2.0F);
                                    double y = (double)(-0.2F - (b1 ? 1 : 0));
                                    double z = Math.random() * (double)spe2 - (double)(spe2 / 2.0F);
                                    Entity entity = new EntityCusPar(
                                            "jinryuudragonbc:bens_particles.png",
                                            this.worldObj,
                                            1.0F,
                                            1.0F,
                                            super.posX,
                                            super.posY,
                                            super.posZ,
                                            x,
                                            y,
                                            z,
                                            0.0 + Math.random() * 0.1F - 0.05F,
                                            0.05 + (double)((float)(Math.random() * 0.2F)),
                                            0.0 + Math.random() * 0.1F - 0.05F,
                                            0.0F,
                                            (int)(Math.random() * 13.0),
                                            0,
                                            13,
                                            32,
                                            true,
                                            0.5F,
                                            false,
                                            0.0F,
                                            1,
                                            "",
                                            160,
                                            0,
                                            0.005F + (float)(Math.random() * 0.02F),
                                            0.0F,
                                            0.0F,
                                            0,
                                            255.0F,
                                            255.0F,
                                            255.0F,
                                            0.0F,
                                            0.0F,
                                            0.0F,
                                            0.0F,
                                            0.0F,
                                            0.0F,
                                            2,
                                            0.7F,
                                            0.0F,
                                            0.95F,
                                            1.0F,
                                            0.01F,
                                            true,
                                            1,
                                            false,
                                            null
                                    );
                                    entity.worldObj.spawnEntityInWorld(entity);
                                }
                            }
                        }

                        b1 = JGConfigClientSettings.CLIENT_GR9;
                        if (this.bol4) {
                            b1 = (int)(Math.random() * 3.0) == 0;
                        }

                        if (b1 && this.ticksExisted % tav == 0) {
                            b2 = false;
                            boolean b1x = false;
                            boolean b2x = false;
                            String[] sx = new String[]{"dirt", "grass", "rock", "stone"};

                            for(int j = 0; j < 5; ++j) {
                                if (!b2x
                                        && (this.bol2 || this.bol)
                                        && this.worldObj
                                        .getBlock((int)this.posX, (int)this.posY - (sneak + j), (int)this.posZ)
                                        .getUnlocalizedName()
                                        .toLowerCase()
                                        .contains("grass")) {
                                    b2x = true;
                                    b2 = j == 1;
                                }

                                for(int i = 0; i < sx.length; ++i) {
                                    if (!b1x
                                            && (this.bol3 || this.bol2 || this.bol)
                                            && this.worldObj
                                            .getBlock((int)this.posX, (int)this.posY - (sneak + j), (int)this.posZ)
                                            .getUnlocalizedName()
                                            .toLowerCase()
                                            .contains(sx[i])) {
                                        b1x = true;
                                        b2 = j == 1;
                                    }
                                }
                            }

                            if (JGConfigClientSettings.CLIENT_GR9 && (b1x || b2x)) {
                                float spe = (5.0F - (b2 ? 1 : 0)) / 10.0F;
                                mod_JRMCore.proxy
                                        .func_gcp(
                                                this,
                                                EntityCusPars.PART6,
                                                0.0,
                                                (double)(-0.2F - (b2 ? 1 : 0)),
                                                0.0,
                                                Math.random() * (double)spe - (double)(spe / 2.0F),
                                                0.0,
                                                Math.random() * (double)spe - (double)(spe / 2.0F),
                                                64
                                        );
                            }
                        }
                    }
                } //Grass particles

                if (JGConfigClientSettings.CLIENT_DA11 && !other.onGround && (other.lastTickPosX != other.posX || other.lastTickPosZ != other.posZ)) {
                    float a = this.alpha;
                    float h1 = 1.0F;
                    float red;
                    float green;
                    float blue;
                    if (this.bol4) {
                        red = 215.0F;
                        green = 152.0F;
                        blue = 219.0F;
                        a /= 2.0F;
                    } else {
                        float h2 = (float)(this.color >> 16 & 0xFF) / 255.0F;
                        float h3 = (float)(this.color >> 8 & 0xFF) / 255.0F;
                        float h4 = (float)(this.color & 0xFF) / 255.0F;
                        red = h1 * h2;
                        green = h1 * h3;
                        blue = h1 * h4;
                    }

                    double x = -other.motionX * 2.5 + (double)(other.motionX > 0.0 ? -0.2F : 0.2F);
                    double y = (double)(other.height * 0.25F + (other instanceof EntityPlayerSP ? -1.6F : 0.0F))
                            - other.motionY * 2.5
                            + (double)(other.motionY > 0.0 ? -0.1F : 0.1F);
                    double z = -other.motionZ * 2.5 + (double)(other.motionZ > 0.0 ? -0.2F : 0.2F);
                    Entity entity = new EntityCusPar(
                            "jinryuumodscore:bens_particles.png",
                            this.worldObj,
                            1.0F,
                            1.0F,
                            other.posX,
                            other.posY,
                            other.posZ,
                            x,
                            y,
                            z,
                            0.0,
                            -0.02,
                            0.0,
                            0.0F,
                            8,
                            11,
                            1,
                            32,
                            false,
                            0.0F,
                            false,
                            0.0F,
                            1,
                            "",
                            15,
                            1,
                            0.09F,
                            0.001F,
                            -0.0045F,
                            0,
                            red,
                            green,
                            blue,
                            0.0F,
                            0.0F,
                            0.0F,
                            red,
                            green,
                            blue,
                            1,
                            0.2F,
                            0.0F,
                            0.0F,
                            0.0F,
                            -0.001F,
                            false,
                            -1,
                            true,
                            null
                    );
                    entity.worldObj.spawnEntityInWorld(entity);
                    if (this.bol4) {
                        red = 141.0F;
                        green = 158.0F;
                        blue = 210.0F;
                    } else {
                        float h2 = (float)(this.colorl3 >> 16 & 0xFF) / 255.0F;
                        float h3 = (float)(this.colorl3 >> 8 & 0xFF) / 255.0F;
                        float h4 = (float)(this.colorl3 & 0xFF) / 255.0F;
                        red = h1 * h2;
                        green = h1 * h3;
                        blue = h1 * h4;
                    }

                    x = -other.motionX * 2.5 + (double)(other.motionX > 0.0 ? -0.2F : 0.2F);
                    y = (double)(other.height * 0.25F + (other instanceof EntityPlayerSP ? -1.6F : 0.0F))
                            - other.motionY * 2.5
                            + (double)(other.motionY > 0.0 ? -0.1F : 0.1F);
                    z = -other.motionZ * 2.5 + (double)(other.motionZ > 0.0 ? -0.2F : 0.2F);
                    Entity entity2 = new EntityCusPar(
                            "jinryuumodscore:bens_particles.png",
                            this.worldObj,
                            1.0F,
                            1.0F,
                            other.posX,
                            other.posY,
                            other.posZ,
                            x,
                            y,
                            z,
                            0.0,
                            -0.02,
                            0.0,
                            0.0F,
                            8,
                            11,
                            1,
                            32,
                            false,
                            0.0F,
                            false,
                            0.0F,
                            1,
                            "",
                            15,
                            1,
                            0.06F,
                            0.001F,
                            -0.003F,
                            0,
                            red,
                            green,
                            blue,
                            0.0F,
                            0.0F,
                            0.0F,
                            red,
                            green,
                            blue,
                            1,
                            0.1F,
                            0.0F,
                            0.0F,
                            0.0F,
                            -0.001F,
                            false,
                            -1,
                            true,
                            null
                    );
                    entity.worldObj.spawnEntityInWorld(entity2);
                } //Flying trail

                if (this.rot) {
                    this.yaw = other.rotationYaw;
                    this.pitch = other.rotationPitch;
                }

                this.setPositionAndRotation(
                        other.posX, other.posY + (double)(other instanceof EntityPlayerSP ? -1.6F : 0.0F), other.posZ, other.rotationYaw, other.rotationPitch
                );
                if (this.getAge() < this.getLightLivingTime() && this.getState() > 4.0F && this.getState() < 7.0F && this.getAge() == 2) {
                    other.playSound("jinryuudragonbc:1610.spark", 0.0375F, 0.85F + (float)this.lightLivingTime * 0.05F);
                }
            } else {
                this.setDead();
            }
        }

        if (this.Age++ >= this.speed) {
            this.setDead();
        }

    }
}
