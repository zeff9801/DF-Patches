package com.mitchej123.hodgepodge.mixins.late.jrmcore;

import JinRyuu.DragonBC.common.DBCConfig;
import JinRyuu.JRMCore.*;
import JinRyuu.JRMCore.entity.EntityEnergyAtt;
import JinRyuu.JRMCore.entity.EntityNPCshadow;
import JinRyuu.JRMCore.i.ExtendedPlayer;
import JinRyuu.JRMCore.server.config.dbc.JGConfigUltraInstinct;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(JRMCoreEH.class)
public abstract class MixinJRMCoreEH {

    @Shadow protected abstract float ApplyArmor(EntityLivingBase entity, ItemStack[] inventory, DamageSource source, double damage);

    @Shadow private boolean dbc;
    @Shadow private boolean nc;
    @Shadow private boolean saoc;

    @Shadow protected abstract float getUltraInstinctCounterStaminaCost(EntityPlayer targetPlayer, byte targetState2);

    @Shadow protected abstract void knockback(EntityLivingBase targetEntity, Entity attacker, int knockbackStrength);

    @Overwrite(remap = false)
    @SubscribeEvent
    public void Sd35MR(LivingHurtEvent event) {
        float amount = event.ammount;
        final DamageSource source = event.source;
        final EntityLivingBase targetEntity = event.entityLiving;
        if (targetEntity instanceof EntityPlayer && source != DamageSource.outOfWorld) {
            final String fusion = JRMCoreH.getString((EntityPlayer)targetEntity, "jrmcFuzion");
            if (fusion.contains(",")) {
                final String[] fusionArray = fusion.split(",");
                if (targetEntity.getCommandSenderName().equalsIgnoreCase(fusionArray[1])) {
                    event.setCanceled(true);
                    return;
                }
            }
        }
        if (targetEntity instanceof EntityNPCshadow) {
            final EntityNPCshadow e = (EntityNPCshadow)targetEntity;
            if (source.getEntity() instanceof EntityLivingBase && e.getSummoner() != source.getEntity()) {
                e.setDead();
            }
        }
        if (source.getDamageType().equals("EnergyAttack") && source.getSourceOfDamage() instanceof EntityEnergyAtt) {
        }
        if (targetEntity instanceof EntityPlayer && source.getEntity() instanceof EntityPlayer) {
            final String s = JRMCoreH.rwip(FMLCommonHandler.instance().getMinecraftServerInstance(), targetEntity.dimension + "");
            if (s.equalsIgnoreCase("false")) {
                return;
            }
        }
        if (!event.isCanceled() && (this.dbc || this.nc || this.saoc) && amount != 0.0f && source != Ds.OutOfBodyHealth && source != Ds.NotAlive && source != DamageSource.outOfWorld) {
            boolean playerAttacked = false;
            if (source.getEntity() != null && source.getEntity() instanceof EntityPlayer) {
                final EntityPlayer attacker = (EntityPlayer)source.getEntity();
                final String fusion2 = JRMCoreH.getString(attacker, "jrmcFuzion");
                if (fusion2.contains(",")) {
                    final String[] fusionArray2 = fusion2.split(",");
                    if (attacker.getCommandSenderName().equalsIgnoreCase(fusionArray2[0]) && targetEntity.getCommandSenderName().equalsIgnoreCase(fusionArray2[1])) {
                        event.setCanceled(true);
                        return;
                    }
                    if (attacker.getCommandSenderName().equalsIgnoreCase(fusionArray2[1]) && targetEntity.getCommandSenderName().equalsIgnoreCase(fusionArray2[0])) {
                        event.setCanceled(true);
                        return;
                    }
                    if (targetEntity.getCommandSenderName().equalsIgnoreCase(fusionArray2[1])) {
                        event.setCanceled(true);
                        return;
                    }
                }
                final boolean ultraInstinctCounter = source.getDamageType().equals("UICounter");
                final boolean Melee = ultraInstinctCounter || (source.getSourceOfDamage() == source.getEntity() && source.getDamageType().equals("player"));
                final boolean energyAtt = source.getDamageType().equals("EnergyAttack") && source.getSourceOfDamage() instanceof EntityEnergyAtt;
                final boolean Projectile = source.getSourceOfDamage() instanceof IProjectile && !energyAtt;
                final int powerType = JRMCoreH.getByte(attacker, "jrmcPwrtyp");
                final int race = JRMCoreH.getByte(attacker, "jrmcRace");
                final int state = JRMCoreH.getByte(attacker, "jrmcState");
                final int state2 = JRMCoreH.getByte(attacker, "jrmcState2");
                final int classID = JRMCoreH.getByte(attacker, "jrmcClass");
                final double release = JRMCoreH.getByte(attacker, "jrmcRelease");
                final String sklx = JRMCoreH.getString(attacker, "jrmcSSltX");
                final int resrv = JRMCoreH.getInt(attacker, "jrmcArcRsrv");
                final int[] PlyrAttrbts = JRMCoreH.PlyrAttrbts(attacker);
                final String[] PlyrSkills = JRMCoreH.getString(attacker, "jrmcSSlts").split(",");
                final String statusEffects = JRMCoreH.getString(attacker, "jrmcStatusEff");
                final boolean mj = JRMCoreH.StusEfcts(12, statusEffects);
                final boolean lg = JRMCoreH.StusEfcts(14, statusEffects);
                final boolean mc = JRMCoreH.StusEfcts(13, statusEffects);
                final boolean mn = JRMCoreH.StusEfcts(19, statusEffects);
                final int currentStamina = JRMCoreH.getInt(attacker, "jrmcStamina");
                int currentEnergy = JRMCoreH.getInt(attacker, "jrmcEnrgy");
                int STR = PlyrAttrbts[0];
                int DEX = PlyrAttrbts[1];
                float dam = amount;
                float den = 0.0f;
                final int ml = JRMCoreH.stat(0, attacker, 0, STR, 0.0f);
                int cst = (int)(ml * 0.1f);
                int cstF = 0;
                int cstF2 = 0;
                int handEffectID = 0;
                if (powerType == 1) {
                    final boolean c = JRMCoreH.StusEfcts(10, statusEffects) || JRMCoreH.StusEfcts(11, statusEffects);
                    if (Melee) {
                        final int sklkf = JRMCoreH.SklLvl(12, PlyrSkills);
                        final boolean sklkfe = !JRMCoreH.PlyrSettingsB(attacker, 9);
                        int sklks = 0;
                        int sklks2 = 0;
                        if (sklkf > 0 && sklkfe) {
                            final int SPI = PlyrAttrbts[5];
                            final int statSPI = JRMCoreH.stat((Entity)attacker, 5, powerType, 5, SPI, race, classID, JRMCoreH.SklLvl_KiBs(PlyrSkills, powerType));
                            sklks = (int)(sklkf * 0.0025 * statSPI * release * 0.01);
                            if (sklks > 0) {
                                cstF = (int)(sklks * DBCConfig.cnfKFc);
                                if (currentEnergy <= cstF) {
                                    sklks = 0;
                                    cstF = 0;
                                }
                                sklks *= (int)DBCConfig.cnfKFd;
                            }
                        }
                        STR = JRMCoreH.TransPwrModAtr(PlyrAttrbts, 0, state, state2, race, sklx, (int)release, resrv, lg, mj, mc, mn, powerType, PlyrSkills, c);
                        final int dmg = JRMCoreH.stat((Entity)attacker, 0, powerType, 0, STR, race, classID, 0.0f);
                        final double curAtr = dmg * release * 0.01 * JRMCoreH.weightPerc(0, attacker);
                        final boolean sklkfe2 = JRMCoreH.PlyrSettingsB(attacker, 13);
                        final boolean sklkfe3 = JRMCoreH.PlyrSettingsI(attacker, 13, 1);
                        final int skf = JRMCoreH.SklLvl(15, PlyrSkills);
                        final boolean hasKiWeaponEnabled = sklkf > 0 && skf > 0 && sklkfe2;
                        if (hasKiWeaponEnabled) {
                            final int WIL = JRMCoreH.TransPwrModAtr(PlyrAttrbts, 3, state, state2, race, sklx, (int)release, resrv, lg, mj, mc, mn, powerType, PlyrSkills, c);
                            attacker.worldObj.playSoundAtEntity((Entity)attacker, "jinryuudragonbc:DBC4.kiblade2", 1.0f, 1.0f);
                            int kiWeaponCost = 0;
                            int kiWeaponDamage = 0;
                            int dmg2 = (int)(JRMCoreH.stat((Entity)attacker, 3, powerType, 4, WIL, race, classID, 0.0f) * 0.01f);
                            float data1 = (float)(int)(0.005 * dmg2 * release * 0.01 * (sklkfe3 ? DBCConfig.cnfKCsd : DBCConfig.cnfKBld) * JRMCoreConfig.dat5699);
                            float data2 = (float)(int)(0.005 * dmg2 * release * 0.01 * (sklkfe3 ? DBCConfig.cnfKCsc : DBCConfig.cnfKBlc));
                            kiWeaponCost += (int)(data2 / ((sklkf > 1) ? (sklkf * 0.3f + 1.0f) : 1.0f));
                            kiWeaponDamage += (int)(sklkf * data1);
                            if (sklks2 > 0) {
                                cstF2 = sklks2;
                                if (currentEnergy <= cstF2) {
                                    sklks2 = 0;
                                    cstF2 = 0;
                                }
                                sklks2 = 0;
                            }
                            dmg2 = (int)(JRMCoreH.stat((Entity)attacker, 3, powerType, 4, WIL, race, classID, 0.0f) * 0.01f);
                            data1 = (float)(dmg2 * release * 0.009999999776482582 * JRMCoreH.weightPerc(1, attacker) * (sklkfe3 ? DBCConfig.cnfKCsd : DBCConfig.cnfKBld) * JRMCoreConfig.dat5700);
                            data2 = (float)(dmg2 * release * 0.009999999776482582 * JRMCoreH.weightPerc(1, attacker) * (sklkfe3 ? DBCConfig.cnfKCsc : DBCConfig.cnfKBlc));
                            kiWeaponCost += (int)(data2 / ((skf > 1) ? (skf * 0.3f + 1.0f) : 1.0f));
                            kiWeaponDamage += (int)(skf * data1);
                            if (sklks2 > 0) {
                                cstF2 = sklks2;
                                if (currentEnergy <= cstF2) {
                                    sklks2 = 0;
                                    cstF2 = 0;
                                }
                                sklks2 = 0;
                            }
                            if (kiWeaponCost > 0 && currentEnergy >= kiWeaponCost) {
                                dam += kiWeaponDamage;
                                currentEnergy -= kiWeaponCost;
                                if (!JRMCoreH.isInCreativeMode((Entity)attacker)) {
                                    JRMCoreH.setInt(currentEnergy - kiWeaponCost, attacker, "jrmcEnrgy");
                                }
                            }
                        }
                        if (JRMCoreConfig.DebugInfo) {
                            mod_JRMCore.logger.info(attacker.getCommandSenderName() + " attacks " + targetEntity.getCommandSenderName() + " with melee " + curAtr + "+" + sklks + "=" + (curAtr + sklks));
                        }
                        dam += (float)(curAtr + sklks);
                    }
                    else if (Projectile) {
                        cst = 1;
                        final int WIL2 = JRMCoreH.TransPwrModAtr(PlyrAttrbts, 3, state, state2, race, sklx, (int)release, resrv, lg, mj, mc, mn, powerType, PlyrSkills, c);
                        final int dmg3 = (int)(JRMCoreH.stat((Entity)attacker, 3, powerType, 4, WIL2, race, classID, 0.0f) * 0.01f);
                        final int skf2 = JRMCoreH.SklLvl(15, PlyrSkills);
                        dam += (float)(dmg3 * release * 0.004999999888241291 * skf2 * JRMCoreH.weightPerc(1, attacker));
                    }
                }
                else if (powerType == 2 && Melee) {
                    boolean effectDone = false;
                    int effectDamage = 0;
                    if (JRMCoreH.NC() && attacker != null) {
                        handEffectID = ExtendedPlayer.get(attacker).getHandEffect();
                        effectDamage = ExtendedPlayer.get(attacker).getEffect_used2();
                        if (handEffectID < 3 && handEffectID > 0 && attacker != null && attacker instanceof EntityPlayer && (handEffectID == 1 || handEffectID == 2)) {
                            JRMCoreH.newExpl(targetEntity.worldObj, (Entity)attacker, targetEntity.posX, targetEntity.posY, targetEntity.posZ, 0.0f, false, 0.0, (Entity)attacker, (byte)(2 + handEffectID));
                            effectDone = true;
                            ExtendedPlayer.get(attacker).setHandEffect(0);
                            ExtendedPlayer.get(attacker).setEffect_used(0);
                            ExtendedPlayer.get(attacker).setEffect_used2(0);
                        }
                    }
                    STR = JRMCoreH.TransPwrModAtr(PlyrAttrbts, 0, state, state2, race, sklx, (int)release, resrv, lg, mj, false, false, powerType, PlyrSkills, false);
                    final int ta = JRMCoreH.SklLvl(0, 2, PlyrSkills);
                    final int cj = JRMCoreH.SklLvlY(2, JRMCoreH.getString(attacker, "jrmcSSltY"));
                    den = ((classID == 1) ? (cj * JRMCoreConfig.hedp * 0.01f) : 0.0f);
                    final int dmg4 = JRMCoreH.stat((Entity)attacker, 0, powerType, 0, STR, race, classID, ta * 0.04f + state * 0.25f);
                    dam += (float)(dmg4 * release * 0.009999999776482582 + effectDamage);
                }
                else if (powerType == 3 && Melee) {
                    final int WeaponDamage = JRMCoreHSAC.getWeaponDamage(attacker.getCurrentEquippedItem(), STR);
                    STR += JRMCoreHSAC.getWeaponBonus(attacker.getCurrentEquippedItem(), 0);
                    DEX += JRMCoreHSAC.getWeaponBonus(attacker.getCurrentEquippedItem(), 1);
                    final int dmg5 = (int)JRMCoreHSAC.getDamage(WeaponDamage, STR, DEX);
                    dam += dmg5;
                    cst = 0;
                }
                if (ultraInstinctCounter) {
                    dam *= JGConfigUltraInstinct.CONFIG_UI_ATTACK_DAMAGE_PERCENTAGE[JRMCoreH.state2UltraInstinct(!mn, (byte)state2)] * 0.01f;
                }
                dam = ((dam <= 0.0f) ? 1.0f : dam);
                int UI_cost = 0;
                if (Melee && ultraInstinctCounter) {
                    UI_cost = (int)this.getUltraInstinctCounterStaminaCost(attacker, (byte)JRMCoreH.state2UltraInstinct(!mn, (byte)state2));
                }
                cst = (int)(cst * JRMCoreConfig.cnfPnchc + UI_cost);
                if (currentStamina > cst && dam > 0.0f) {
                    boolean doAttack = true;
                    if (this.dbc && JRMCoreConfig.sfzns) {
                        doAttack = !JRMCoreHDBC.JRMCoreEHonLivingHurtSafeZone(targetEntity);
                    }
                    if (doAttack) {
                        playerAttacked = true;
                        if (Melee) {
                            final boolean take_stamina = !ultraInstinctCounter || UI_cost > 0;
                            if (take_stamina) {
                                final int new_stamina = currentStamina - cst;
                                if (!JRMCoreH.isInCreativeMode((Entity)attacker)) {
                                    JRMCoreH.setInt(new_stamina, attacker, "jrmcStamina");
                                }
                            }
                            if (cstF > 0 && currentEnergy >= cstF && !JRMCoreH.isInCreativeMode((Entity)attacker)) {
                                JRMCoreH.setInt(currentEnergy - cstF, attacker, "jrmcEnrgy");
                            }
                        }
                        int dealt = (int)dam;
                        if (targetEntity instanceof EntityPlayer) {
                            final EntityPlayer targetPlayer = (EntityPlayer)targetEntity;
                            final int acc = JRMCoreH.getByte(targetPlayer, "jrmcAccept");
                            if (acc == 0 || JRMCoreH.getByte(targetPlayer, "jrmcPwrtyp") == 0) {
                                event.setCanceled(false);
                                return;
                            }
                            dam = this.damageEntity(targetPlayer, source, dam);
                            JRMCoreH.a1t3(targetPlayer);
                            final int epoch = (int)(System.currentTimeMillis() / 1000L);
                            JRMCoreH.setInt(epoch + 5, targetPlayer, "jrmcAttackLstPlyrTm");
                            JRMCoreH.setString(attacker.getUniqueID().toString(), targetPlayer, "jrmcAttackLstPlyrNam");
                            if (energyAtt) {
                                dealt = JRMCoreH.jrmcDam((Entity)targetPlayer, (int)dam, source, ((EntityEnergyAtt)source.getSourceOfDamage()).getType());
                            }
                            else if (powerType == 1 && Projectile) {
                                final int skf3 = JRMCoreH.SklLvl(15, PlyrSkills);
                                final int cost = (int)(dam * 0.005 * skf3 * DBCConfig.cnfKIc);
                                if (currentEnergy < cost) {
                                    event.setCanceled(false);
                                    return;
                                }
                                if (!JRMCoreH.isInCreativeMode((Entity)attacker)) {
                                    JRMCoreH.setInt(currentEnergy - cost, attacker, "jrmcEnrgy");
                                }
                                dealt = JRMCoreH.jrmcDam((Entity)targetPlayer, (int)(dam * DBCConfig.cnfKId), source);
                                this.knockback((EntityLivingBase)targetPlayer, (Entity)attacker, 1);
                            }
                            else {
                                dealt = JRMCoreH.jrmcDam((Entity)targetPlayer, (int)dam, source);
                                this.knockback((EntityLivingBase)targetPlayer, (Entity)attacker, 1);
                            }
                            if (this.nc && JRMCoreH.clsTypOn(attacker) == 1 && JRMCoreH.getByte(attacker, "jrmcPwrtyp") == 2) {
                                JRMCoreH.jrmcEnergyDam((Entity)targetPlayer, (int)(dam * den), source);
                            }
                        }
                        else if (!this.dbc || JRMCoreH.DGE((Entity)targetEntity)) {
                            if (powerType == 1 && Projectile) {
                                final int skf2 = JRMCoreH.SklLvl(15, PlyrSkills);
                                final int cost2 = (int)(dam * 0.005 * skf2 * DBCConfig.cnfKIc);
                                if (currentEnergy < cost2) {
                                    event.setCanceled(false);
                                    return;
                                }
                                if (!JRMCoreH.isInCreativeMode((Entity)attacker)) {
                                    JRMCoreH.setInt(currentEnergy - cost2, attacker, "jrmcEnrgy");
                                }
                                dealt = (int)dam;
                                this.damageEntity(targetEntity, source, (float)(int)(dam * DBCConfig.cnfKId));
                                this.knockback(targetEntity, (Entity)attacker, 1);
                                this.knockback(targetEntity, (Entity)attacker, 1);
                            }
                            else {
                                dealt = (int)dam;
                                this.damageEntity(targetEntity, source, dam);
                                if (handEffectID == 1) {
                                    this.damageEntity(targetEntity, source, dam);
                                    final float push = 1.0f;
                                    final EntityLivingBase entityLivingBase = targetEntity;
                                    entityLivingBase.motionX += ((attacker.posX > targetEntity.posX) ? (-push) : push);
                                    final EntityLivingBase entityLivingBase2 = targetEntity;
                                    entityLivingBase2.motionY += ((attacker.posY > targetEntity.posY) ? (-push) : push);
                                    final EntityLivingBase entityLivingBase3 = targetEntity;
                                    entityLivingBase3.motionZ += ((attacker.posZ > targetEntity.posZ) ? (-push) : push);
                                    targetEntity.velocityChanged = true;
                                }
                                else if (handEffectID == 0) {
                                    this.knockback(targetEntity, (Entity)attacker, 1);
                                }
                            }
                        }
                        if (!attacker.worldObj.isRemote && (!this.dbc || JRMCoreH.DGE((Entity)targetEntity)) && attacker != null) {
                            boolean giveTP = true;
                            if (source.getSourceOfDamage() != null && energyAtt) {
                                final EntityEnergyAtt kiAttack = (EntityEnergyAtt)source.getSourceOfDamage();
                                if (kiAttack.givenTP) {
                                    giveTP = false;
                                }
                                else {
                                    kiAttack.givenTP = true;
                                }
                            }
                            if (giveTP) {
                                int tp = 1;
                                if (targetEntity instanceof EntityPlayer) {
                                    final int[] PlyrAttrbtsTar = JRMCoreH.PlyrAttrbts((EntityPlayer)targetEntity);
                                    final int rltar = JRMCoreH.getByte(attacker, "jrmcRelease");
                                    final float tartp = PlyrAttrbtsTar[4] / (JRMCoreConfig.TpgnRt * JRMCoreConfig.TPGainRateRace[race]) * rltar * 0.01f;
                                    final float atttp = (float)(PlyrAttrbts[4] / (JRMCoreConfig.TpgnRt * JRMCoreConfig.TPGainRateRace[race]) * release);
                                    float mult = tartp / atttp;
                                    mult = ((mult > 2.0f) ? 2.0f : mult);
                                    tp += (int)(atttp * mult);
                                }
                                else if (targetEntity instanceof EntityNPCshadow) {
                                    final float atttp2 = PlyrAttrbts[4] / (JRMCoreConfig.TpgnRt * JRMCoreConfig.TPGainRateRace[race]);
                                    float mult2 = (float)(atttp2 / (atttp2 * release * 0.009999999776482582));
                                    mult2 = ((mult2 > 2.0f) ? 2.0f : mult2);
                                    tp += (int)(atttp2 * mult2);
                                }
                                else {
                                    final float atttp2 = (float)(PlyrAttrbts[4] / (JRMCoreConfig.TpgnRt * JRMCoreConfig.TPGainRateRace[race]) * release * 0.009999999776482582);
                                    tp += (int)atttp2;
                                }
                                JRMCoreH.jrmcExp((Entity)attacker, tp);
                            }
                        }
                        if (source.damageType.equalsIgnoreCase("player")) {
                            final int id = (int)(Math.random() * 3.0) + 1;
                            attacker.worldObj.playSoundAtEntity((Entity)attacker, "jinryuudragonbc:DBC4.punch" + id, 0.5f, 0.9f / (attacker.worldObj.rand.nextFloat() * 0.4f + 0.8f));
                        }
                        if (attacker != null) {
                            JRMCoreH.setInt(dealt, attacker, "jrmcLastDamageDealt");
                        }
                        if (targetEntity != null && targetEntity instanceof EntityPlayer) {
                            JRMCoreH.setInt(dealt, (EntityPlayer)targetEntity, "jrmcLastDamageReceived");
                            if (attacker != null) {
                                final int epoch2 = (int)(System.currentTimeMillis() / 1000L);
                                JRMCoreH.setString(attacker.getCommandSenderName() + ";" + epoch2, (EntityPlayer)targetEntity, "jrmcLastAttacker");
                            }
                        }
                        else if (targetEntity != null) {
                            JRMCoreH.nbt((Entity)targetEntity).setInteger("jrmcLastDamageReceived", dealt);
                            if (attacker != null) {
                                final int epoch2 = (int)(System.currentTimeMillis() / 1000L);
                                JRMCoreH.nbt((Entity)targetEntity).setString("jrmcLastAttacker", attacker.getCommandSenderName() + ";" + epoch2);
                            }
                        }
                        return;
                    }
                    if (attacker instanceof EntityPlayer) {
                        final String t = JRMCoreH.cly + StatCollector.translateToLocal("dbc.entitymasters.nofightinsafe");
                        attacker.addChatMessage(new ChatComponentText(JRMCoreH.cly + t));
                        return;
                    }
                }
            }
            if (!playerAttacked && source != DamageSource.outOfWorld && targetEntity instanceof EntityPlayer) {
                final EntityPlayer targetPlayer2 = (EntityPlayer)targetEntity;
                final int acc2 = JRMCoreH.getByte(targetPlayer2, "jrmcAccept");
                if (acc2 == 0 || JRMCoreH.getByte(targetPlayer2, "jrmcPwrtyp") == 0) {
                    event.setCanceled(false);
                    return;
                }
                final Entity attacker2 = source.getEntity();
                final int[] PlyrAttrbts2 = JRMCoreH.PlyrAttrbts(targetPlayer2);
                final byte race2 = JRMCoreH.getByte(targetPlayer2, "jrmcRace");
                final byte release2 = JRMCoreH.getByte(targetPlayer2, "jrmcRelease");
                final int currentBody = JRMCoreH.getInt(targetPlayer2, "jrmcBdy");
                if (!targetPlayer2.capabilities.isCreativeMode && (this.dbc || this.nc || this.saoc)) {
                    boolean doAttack2 = true;
                    if (this.dbc && JRMCoreConfig.sfzns) {
                        doAttack2 = !JRMCoreHDBC.JRMCoreEHonLivingHurtSafeZone(targetEntity);
                    }
                    if (doAttack2) {
                        amount = this.damageEntity(targetPlayer2, source, amount);
                        int dealt2 = 0;
                        JRMCoreH.a1t3(targetPlayer2);
                        if (attacker2 instanceof EntityNPCshadow) {
                            if (currentBody > amount) {
                                dealt2 = JRMCoreH.jrmcDam((Entity)targetPlayer2, (int)amount, source);
                            }
                            else {
                                attacker2.setDead();
                            }
                        }
                        else if (source.getDamageType().equals("EnergyAttack") && source.getSourceOfDamage() instanceof EntityEnergyAtt) {
                            dealt2 = JRMCoreH.jrmcDam((Entity)targetPlayer2, (int)amount, source, ((EntityEnergyAtt)source.getSourceOfDamage()).getType());
                        }
                        else {
                            dealt2 = JRMCoreH.jrmcDam((Entity)targetPlayer2, (int)amount, source);
                        }
                        if (attacker2 != null) {
                            this.knockback(targetEntity, attacker2, 1);
                        }
                        if (attacker2 != null) {
                            if (attacker2 instanceof EntityPlayer) {
                                JRMCoreH.setInt(dealt2, (EntityPlayer)attacker2, "jrmcLastDamageDealt");
                            }
                            else {
                                JRMCoreH.nbt(attacker2).setInteger("jrmcLastDamageDealt", dealt2);
                            }
                        }
                        if (targetEntity != null && targetEntity instanceof EntityPlayer) {
                            JRMCoreH.setInt(dealt2, (EntityPlayer)targetEntity, "jrmcLastDamageReceived");
                            if (attacker2 != null) {
                                final int epoch3 = (int)(System.currentTimeMillis() / 1000L);
                                JRMCoreH.setString(attacker2.getCommandSenderName() + ";" + epoch3, (EntityPlayer)targetEntity, "jrmcLastAttacker");
                            }
                        }
                        else if (targetEntity != null) {
                            JRMCoreH.nbt((Entity)targetEntity).setInteger("jrmcLastDamageReceived", dealt2);
                            if (attacker2 != null) {
                                final int epoch3 = (int)(System.currentTimeMillis() / 1000L);
                                JRMCoreH.nbt((Entity)targetEntity).setString("jrmcLastAttacker", attacker2.getCommandSenderName() + ";" + epoch3);
                            }
                        }
                        return;
                    }
                    if (attacker2 instanceof EntityPlayer) {
                        final String t2 = JRMCoreH.cly + StatCollector.translateToLocal("dbc.entitymasters.nofightinsafe");
                        ((EntityPlayer)attacker2).addChatMessage(new ChatComponentText(JRMCoreH.cly + t2));
                    }
                }
            }
        }
    }

    @Overwrite(remap = false)
    protected float damageEntity(EntityPlayer targetPlayer, DamageSource source, float amount) {
        return 0;
    }

    @Overwrite(remap = false)
    protected void damageEntity(EntityLivingBase targetEntity, DamageSource source, float amount) {
    }
}
