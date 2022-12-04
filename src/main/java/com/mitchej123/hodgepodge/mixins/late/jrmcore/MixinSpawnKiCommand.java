package com.mitchej123.hodgepodge.mixins.late.jrmcore;

import JinRyuu.DragonBC.common.Gui.ComDbcSpawnKi;
import JinRyuu.JRMCore.JRMCoreConfig;
import JinRyuu.JRMCore.JRMCoreH;
import JinRyuu.JRMCore.entity.EntityEnergyAtt;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

import static net.minecraft.command.CommandBase.getPlayer;

@Mixin(ComDbcSpawnKi.class)
public class MixinSpawnKiCommand {
    @Overwrite(remap = false)
    public void func_71515_b(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length < 10) {
            throw new WrongUsageException("Use '/dbcspawnki (Type) (Speed) (Damage) (Effect) (Color) (Density) (Sound: Move) (Charge Percentage) [User Position X] [User Position Y] [User Position Z]' to spawn a Ki Attack.");
        }
        boolean spawn = true;
        boolean found = false;
        EntityLivingBase entityplayermp;
        if (par2ArrayOfStr.length == 11) {
            final double minx = Double.parseDouble(par2ArrayOfStr[8]) - 1.0;
            final double miny = Double.parseDouble(par2ArrayOfStr[9]) - 1.0;
            final double minz = Double.parseDouble(par2ArrayOfStr[10]) - 1.0;
            final double maxx = minx + 2.0;
            final double maxy = miny + 2.0;
            final double maxz = minz + 2.0;
            final List var6 = par1ICommandSender.getEntityWorld().getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(minx, miny, minz, maxx, maxy, maxz));
            Entity var7 = null;
            for (Object o : var6) {
                var7 = (Entity) o;
                if (var7 instanceof EntityLivingBase && var7.canBeCollidedWith()) {
                    found = true;
                    break;
                }
            }
            if (found) {
                entityplayermp = (EntityLivingBase) var7;
            } else {
                entityplayermp = null;
            }
        } else {
            if (par2ArrayOfStr.length != 12) {
                return;
            }
            entityplayermp = getPlayer(par1ICommandSender, par2ArrayOfStr[11]);
            spawn = true;
            found = true;
        }
        EntityEnergyAtt mr;
        boolean spawned = false;
        if (spawn && found && entityplayermp != null) {
            byte type = Byte.parseByte(par2ArrayOfStr[0]);
            if (JRMCoreConfig.dat5695[type]) {
                if (type < 0) {
                    type = 0;
                }
                if (type > 8) {
                    type = 8;
                }
                byte speed = Byte.parseByte(par2ArrayOfStr[1]);
                if (speed < 0) {
                    speed = 0;
                }
                int dam1 = Integer.parseInt(par2ArrayOfStr[2]);
                if (dam1 < 0) {
                    dam1 = 0;
                }
                byte effect = Byte.parseByte(par2ArrayOfStr[3]);
                if (effect < 0) {
                    effect = 0;
                }
                if (effect > 1) {
                    effect = 1;
                }
                byte color = Byte.parseByte(par2ArrayOfStr[4]);
                if (color < 0) {
                    color = 0;
                }
                if (color > JRMCoreH.techCol.length - 1) {
                    color = (byte) (JRMCoreH.techCol.length - 1);
                }
                byte density = Byte.parseByte(par2ArrayOfStr[5]);
                if (density < 0) {
                    density = 0;
                }
                byte sndMv = Byte.parseByte(par2ArrayOfStr[6]);
                if (sndMv < 0) {
                    sndMv = 0;
                }
                if (sndMv > 1) {
                    sndMv = 1;
                }
                byte chrg = Byte.parseByte(par2ArrayOfStr[7]);
                if (chrg < 0) {
                    chrg = 0;
                }
                if (chrg > 100) {
                    chrg = 100;
                }
                final byte[] sts = JRMCoreH.techDBCstatsDefault;
                entityplayermp.worldObj.playSoundAtEntity(entityplayermp, "jinryuudragonbc:DBC2.basicbeam_fire", 0.5f, 1.0f);
                mr = new EntityEnergyAtt(entityplayermp, type, speed, 50, effect, color, density, (byte) 0, (byte) 0, sndMv, chrg, dam1, 0, sts, (byte) 0);
                mr.setDamage(dam1);
                entityplayermp.worldObj.spawnEntityInWorld(mr);
            }
        }
    }
}
