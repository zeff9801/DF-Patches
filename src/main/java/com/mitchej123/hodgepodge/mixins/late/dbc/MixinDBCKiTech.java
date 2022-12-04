package com.mitchej123.hodgepodge.mixins.late.dbc;

import JinRyuu.DragonBC.common.DBCClient;
import JinRyuu.DragonBC.common.DBCKiTech;
import JinRyuu.DragonBC.common.Npcs.EntityAura2;
import JinRyuu.DragonBC.common.Npcs.EntityAuraRing;
import JinRyuu.JRMCore.JRMCoreH;
import JinRyuu.JRMCore.JRMCoreHDBC;
import JinRyuu.JRMCore.server.config.dbc.JGConfigUltraInstinct;
import com.mitchej123.hodgepodge.util.JbraHelper;
import me.aegous.dfclient.main.DFClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;
import java.util.Random;

import static JinRyuu.DragonBC.common.DBCKiTech.soundAsc;

@Mixin(DBCKiTech.class)
public class MixinDBCKiTech {
    @Shadow(remap = false)
    private static int partnorm;

    @Shadow(remap = false)
    private static int power;

    //Modification to the DBC aura spawning, adding new auras for our custom forms
    @Overwrite(remap = false)
    public static void chargePart(final EntityPlayer p, final int r, final int a, int c, final int s, final int k, final boolean b, final String se) {
        int dbcchargepart = 0;
        final boolean kk = k > 0 || JRMCoreH.StusEfcts(5, se);
        final boolean l = JRMCoreH.rc_sai(r) && JRMCoreH.StusEfcts(14, se);
        final boolean u = JRMCoreH.StusEfcts(19, se);
        final boolean ma = JRMCoreH.StusEfcts(12, se);
        if (!b) {
            partnorm = 0;
            if (kk && JRMCoreH.rc_sai(r) && (s == 10 || s == 15)) {
                dbcchargepart = 9;
            } else if (kk) {
                dbcchargepart = 4;
            } else if ((s > 0 && JRMCoreH.rc_sai(r) && s != 7) || JRMCoreHDBC.godKiUser(r, s)) {
                dbcchargepart = 2;
            } else {
                dbcchargepart = 1;
            }
        } else {
            String ar = "jinryuudragonbc:DBC.aura";
            if (JRMCoreH.rc_sai(r) && (s == 10 || s == 15)) {
                ar = "jinryuudragonbc:1610.aurab";
            }
            if (JRMCoreH.rc_sai(r) && s != 10 && s != 15 && JRMCoreHDBC.godKiUser(r, s)) {
                ar = "jinryuudragonbc:1610.aurag";
            }
            if (kk && JRMCoreH.rc_sai(r) && (s == 10 || s == 15)) {
                ar = "jinryuudragonbc:1610.aurabk";
            }
            soundAsc(ar);
            Label_0333:
            {
                if (!JRMCoreH.StusEfcts(3, se) && !JRMCoreH.StusEfcts(7, se)) {
                    if (k > 0) {
                        dbcchargepart = 8;
                    } else {
                        if (s > 0 && JRMCoreH.rc_sai(r)) {
                            if (s == 7) {
                                if (!JRMCoreHDBC.godKiUser(r, s)) {
                                    dbcchargepart = 5;
                                    break Label_0333;
                                }
                            }
                        }
                        dbcchargepart = 6;
                    }
                }
            }
            power = 0;
        }
        if (dbcchargepart > 0) {
            final String dbcCharger = p.getCommandSenderName();
            final double dbcChargerY = p.posY;
            final Entity other = p.worldObj.getPlayerEntityByName(dbcCharger);
            final Random rand = new Random();
            Entity aura = null;
            Entity aura2 = null;
            float state = 0.0f;
            float state2 = 0.0f;
            int cr = 0;
            if (JRMCoreH.plyrs != null && JRMCoreH.plyrs.length > 0 && JRMCoreH.dnn(2) && JRMCoreH.dnn(10)) {
                for (int pl = 0; pl < JRMCoreH.plyrs.length; ++pl) {
                    if (JRMCoreH.plyrs[pl].equals(dbcCharger)) {
                        final String[] states = JRMCoreH.data2[pl].split(";");
                        state = (float) Integer.parseInt(states[0]);
                        state2 = (float) Integer.parseInt(states[1]);
                        cr = Integer.parseInt(JRMCoreH.dat10[pl].split(";")[0]);
                    }
                }
            }
            c = ((c > 0) ? c : JRMCoreH.Algnmnt_rc(a));
            final boolean w = JRMCoreH.StusEfcts(7, se) || (JRMCoreH.StusEfcts(9, se) && JRMCoreH.data(dbcCharger, 3, "0").contains("1") && !JRMCoreH.StusEfctsMe(4));
            final boolean ssg = JRMCoreHDBC.godKiUserBase(r, s);
            final boolean ssb = JRMCoreH.rc_sai(r) && s == 10;
            final boolean ssbs = JRMCoreH.rc_sai(r) && s == 15;
            final boolean auf = JRMCoreH.rc_arc(r) && s == 6;
            final boolean v = JRMCoreH.StusEfcts(17, se);
            final boolean lsa = JRMCoreH.lgndb(se, r, s);
            final boolean i = JRMCoreH.StusEfcts(19, se);
            final int sacol = JRMCoreHDBC.col_fe(2, c, 1, r, s, v, lsa, i);
            final float customAuraOpacity = (DFClient.optionConfig.get("Options Config","auraOpacity",50).getInt() / 100f);
            final float customFirstPersonAuraOpacity = (DFClient.optionConfig.get("Options Config","fpAuraOpacity",5).getInt() / 100f);
            state = ((ssg || ssb) ? 0.0f : state);
            state = (ssbs ? 3.0f : state);
            final boolean oozar = JRMCoreH.rc_sai(r) && (state == 7.0f || state == 8.0f);
            state = (oozar ? (state * 3.0f) : state);
            state = ((JRMCoreH.rc_nam(r) && state == 2.0f) ? 22.0f : state);
            final boolean plyrSP = DBCClient.mc.thePlayer.getCommandSenderName().equals(dbcCharger) && DBCClient.mc.gameSettings.thirdPersonView == 0;
            if (dbcchargepart >= 1 && dbcchargepart <= 4) {
                aura = new EntityAura2(p.worldObj, dbcCharger, (state2 > 0.0f) ? 16646144 : sacol, state, state2, cr, w);
            }
            if (dbcchargepart >= 5 && dbcchargepart <= 8 && !i) {
                aura = new EntityAuraRing(p.worldObj, dbcCharger, (state2 > 0.0f) ? 16646144 : sacol, state, state2, cr);
            }
            if (dbcchargepart == 9) {
                aura = new EntityAura2(p.worldObj, dbcCharger, sacol, 0.0f, 0.0f, cr, w);
                if (state2 > 0.0f) {
                    aura2 = new EntityAura2(p.worldObj, dbcCharger, 16646144, 2.0f + state, state2 * 1.5f, cr, w);
                }
            }
            if (aura != null && other != null) {
                if (aura instanceof EntityAura2) {
                    ((EntityAura2) aura).setAlp(plyrSP ? customFirstPersonAuraOpacity : customAuraOpacity);
                    if (ssg) {
                        ((EntityAura2) aura).setTex("aurai");
                        ((EntityAura2) aura).setTexL2("aurai2");
                        ((EntityAura2) aura).setColL2(16747301);
                    } else if (ssbs && v) {
                        ((EntityAura2) aura).setSpd(30);
                        ((EntityAura2) aura).setTex("aurai");
                        ((EntityAura2) aura).setTexL2("aurai2");
                        ((EntityAura2) aura).setColL2(8592109);
                    } else if (ssbs) {
                        ((EntityAura2) aura).setSpd(40);
                        ((EntityAura2) aura).setTex("aurag");
                        ((EntityAura2) aura).setColL3(12310271);
                        ((EntityAura2) aura).setTexL3("auragb");
                    } else if (ssb && v) {
                        ((EntityAura2) aura).setSpd(30);
                        ((EntityAura2) aura).setTex("aurai");
                        ((EntityAura2) aura).setTexL2("aurai2");
                        ((EntityAura2) aura).setColL2(7872713);
                    } else if (ssb) {
                        ((EntityAura2) aura).setSpd(40);
                        ((EntityAura2) aura).setTex("aurag");
                        ((EntityAura2) aura).setColL3(15727354);
                        ((EntityAura2) aura).setTexL3("auragb");
                    } else if (auf) {
                        ((EntityAura2) aura).setTex("aurau");
                        ((EntityAura2) aura).setTexL2("aurau2");
                        ((EntityAura2) aura).setColL2(16776724);
                    } else if (i) {
                        ((EntityAura2) aura).setSpd(100);
                        ((EntityAura2) aura).setTex("auras");
                        ((EntityAura2) aura).setCol(15790320);
                        ((EntityAura2) aura).setColL3(4746495);
                        ((EntityAura2) aura).setTexL3("auragb");
                    }
                    if (se.contains(JbraHelper.formsEff.get(4)[0])) {
                        final int arcoCol = new Color(176, 180, 177).getRGB();
                        ((EntityAura2) aura).setSpd(55);
                        ((EntityAura2) aura).setTex("aurasHD18");
                        ((EntityAura2) aura).setCol(arcoCol);
                    } else if (false) {
                        final int ascendedCol = new Color(176, 180, 177).getRGB();
                        ((EntityAura2) aura).setSpd(55);
                        ((EntityAura2) aura).setTex("aurasHD30");
                        ((EntityAura2) aura).setCol(ascendedCol);
                    } else if (false) {
                        int divineCol = new Color(250, 251, 255, 255).getRGB();
                        ((EntityAura2) aura).setSpd(55);
                        ((EntityAura2) aura).setTex("aurasHD12");
                        ((EntityAura2) aura).setCol(divineCol);
                        ((EntityAura2) aura).setTexL2("aura");
                        ((EntityAura2) aura).setColL2(divineCol);
                    } else if (se.contains(JbraHelper.formsEff.get(4)[2])) {
                        int goldCol = new Color(253, 248, 255, 255).getRGB();
                        ((EntityAura2) aura).setSpd(55);
                        ((EntityAura2) aura).setTex("aurasHD29");
                        ((EntityAura2) aura).setCol(goldCol);
                    }

                    if (se.contains(JbraHelper.formsEff.get(1)[0]) ||
                            se.contains(JbraHelper.formsEff.get(1)[1]) ||
                            se.contains(JbraHelper.formsEff.get(1)[2])) { //Saiyan
                        final int saiyanColor = new Color(253, 245, 255, 255).getRGB();
                        ((EntityAura2) aura).setSpd(55);
                        ((EntityAura2) aura).setTex("aurasHD29");
                        ((EntityAura2) aura).setCol(saiyanColor);

                        if (se.contains("L")) { //Is Legend
                            ((EntityAura2) aura).setCol(new Color(60, 246, 83, 255).getRGB());
                        }
                    }
                    //Human
                    if (se.contains(JbraHelper.formsEff.get(0)[0])) {
                        final int fullColor = new Color(255, 254, 0).getRGB();
                        ((EntityAura2) aura).setSpd(55);
                        ((EntityAura2) aura).setTex("aurasHD7");
                        ((EntityAura2) aura).setCol(fullColor);
                    } else if (se.contains(JbraHelper.formsEff.get(0)[1])) {
                        final int htensionColor = new Color(183, 186, 183, 255).getRGB();
                        ((EntityAura2) aura).setSpd(55);
                        ((EntityAura2) aura).setTex("aurasHD6");
                        ((EntityAura2) aura).setCol(htensionColor);
                    } else if (se.contains(JbraHelper.formsEff.get(0)[2])) {
                        int limitbreakColor = new Color(255, 206, 209, 255).getRGB();
                        ((EntityAura2) aura).setSpd(55);
                        ((EntityAura2) aura).setTex("aurasHD11");
                        ((EntityAura2) aura).setCol(limitbreakColor);
                    } else if (false) {
                        final int ultraColor = new Color(251, 255, 245).getRGB();
                        ((EntityAura2) aura).setSpd(55);
                        ((EntityAura2) aura).setTex("aurasHD17");
                        ((EntityAura2) aura).setCol(ultraColor);
                    } else if (false) {
                        int darkhumanColor = new Color(139, 136, 140, 255).getRGB();
                        ((EntityAura2) aura).setSpd(55);
                        ((EntityAura2) aura).setTex("aurasHD22");
                        ((EntityAura2) aura).setCol(darkhumanColor);
                    }
                    //Namekian
                    if (se.contains(JbraHelper.formsEff.get(3)[0])) {
                        final int buffedColor = new Color(251, 255, 245).getRGB();
                        ((EntityAura2) aura).setSpd(55);
                        ((EntityAura2) aura).setTex("aura");
                        ((EntityAura2) aura).setCol(buffedColor);
                    } else if (se.contains(JbraHelper.formsEff.get(3)[1])) {
                        final int greatCol = 65408;
                        ((EntityAura2) aura).setSpd(55);
                        ((EntityAura2) aura).setTex("aura");
                        ((EntityAura2) aura).setCol(greatCol);
                    } else if (se.contains(JbraHelper.formsEff.get(3)[2])) {
                        final int ragecolorV = new Color(255, 132, 26).getRGB();
                        ((EntityAura2) aura).setSpd(55);
                        ((EntityAura2) aura).setTex("aurasHD7");
                        ((EntityAura2) aura).setCol(ragecolorV);
                        final int ragecolorV2 = new Color(22, 22, 22, 85).getRGB();
                        ((EntityAura2) aura).setTexL2("aura");
                        ((EntityAura2) aura).setColL2(ragecolorV2);
                    } else if (false) {
                        int namekgodColor = new Color(0, 250, 255, 255).getRGB();
                        ((EntityAura2) aura).setSpd(55);
                        ((EntityAura2) aura).setTex("aurasHD10");
                        ((EntityAura2) aura).setCol(namekgodColor);
                    } else if (false) {
                        int redformColor = new Color(255, 116, 113, 255).getRGB();
                        ((EntityAura2) aura).setSpd(55);
                        ((EntityAura2) aura).setTex("aurasHD23");
                        ((EntityAura2) aura).setCol(redformColor);
                    }
                }
                if (aura != null && aura instanceof EntityAura2) {
                    ((EntityAura2) aura).setBol(JRMCoreH.StusEfcts(1, se));
                    ((EntityAura2) aura).setBol2(JRMCoreH.StusEfcts(4, se));
                    ((EntityAura2) aura).setBol3(JRMCoreH.StusEfcts(3, se));
                    ((EntityAura2) aura).setBol4(i);
                    final boolean Bol4a = JGConfigUltraInstinct.CONFIG_UI_LEVELS > 0 && JGConfigUltraInstinct.CONFIG_UI_HAIR_WHITE[JRMCoreH.state2UltraInstinct(!i, (byte) (int) state2)];
                    ((EntityAura2) aura).setBol4a(Bol4a);
                    int bol6 = ssg ? 0 : ((v && ssb) ? 3 : ((v && ssbs) ? 5 : (ssb ? 1 : (ssbs ? 2 : (auf ? 4 : -1)))));
                    if (i) {
                        bol6 = 0;
                    }
                    ((EntityAura2) aura).setBol6(bol6);
                    ((EntityAura2) aura).setBol7(ma || kk);
                }
                p.worldObj.spawnEntityInWorld(aura);
            }
            if (aura2 != null && other != null) {
                if (aura2 instanceof EntityAura2) {
                    ((EntityAura2) aura2).setSpd(40);
                    ((EntityAura2) aura2).setTex("aurak");
                    ((EntityAura2) aura2).setInner(false);
                    ((EntityAura2) aura2).setRendPass(0);
                }
                aura2.setLocationAndAngles(other.posX - 0.0, other.posY - 2.0 + rand.nextInt(5) * 0.03, other.posZ - 0.0, rand.nextFloat(), 0.0f);
                p.worldObj.spawnEntityInWorld(aura2);
            }
        }
    }
}
