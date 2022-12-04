package com.mitchej123.hodgepodge.mixins.late.jbra;

import JinRyuu.DragonBC.common.Items.ItemsDBC;
import JinRyuu.JBRA.JBRAClient;
import JinRyuu.JBRA.JBRAH;
import JinRyuu.JBRA.ModelBipedDBC;
import JinRyuu.JBRA.RenderPlayerJBRA;
import JinRyuu.JRMCore.JRMCoreConfig;
import JinRyuu.JRMCore.JRMCoreGuiScreen;
import JinRyuu.JRMCore.JRMCoreH;
import JinRyuu.JRMCore.JRMCoreHDBC;
import JinRyuu.JRMCore.client.config.jrmc.JGConfigClientSettings;
import JinRyuu.JRMCore.i.ExtendedPlayer;
import JinRyuu.JRMCore.items.ItemVanity;
import JinRyuu.JRMCore.server.config.dbc.JGConfigUltraInstinct;
import com.mitchej123.hodgepodge.util.JbraHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static JinRyuu.JBRA.RenderPlayerJBRA.*;

@Mixin(RenderPlayerJBRA.class)
public abstract class MixinRenderPlayerJBRA {
    @Shadow(remap = false) private static final ResourceLocation steveTextures = new ResourceLocation("textures/entity/steve.png");
    @Shadow(remap = false) private static final ResourceLocation fem = new ResourceLocation("jinryuufamilyc:fem.png");
    @Shadow(remap = false) private static ResourceLocation curSkin;
    @Shadow(remap = false) private static boolean curSkinUp;
    @Shadow(remap = false) public ModelBipedDBC modelMain;
    @Shadow(remap = false) private static boolean kk2;
    @Shadow(remap = false) public static int kk;
    @Shadow(remap = false) public static float r;
    @Shadow(remap = false) public static float g;
    @Shadow(remap = false) public static float b;
    @Shadow(remap = false) private int pl;
    @Shadow(remap = false) private static float age = 0.0F;
    @Shadow(remap = false) private static float getR() { return r + r2; }
    @Shadow(remap = false) private static float getG() { return g + g2; }
    @Shadow(remap = false) private static float getB() { return b + b2; }

    @Shadow public abstract int getAuratime(String pl);

    @Overwrite(remap = false)
    protected void renderEquippedItemsJBRA(final AbstractClientPlayer par1AbstractClientPlayer, final float par2) {
        Object data = null;
        if (JBRAH.JHDS()) {
            final Object temp = JBRAH.skinData(par1AbstractClientPlayer);
            data = (temp);
        }
        final ItemStack itemstack = par1AbstractClientPlayer.inventory.armorItemInSlot(3);
        boolean doit = true;
        final ItemStack hair = par1AbstractClientPlayer.inventory.armorItemInSlot(3);
        if (hair != null) {
            if (JRMCoreH.DBC()) {
                doit = true;
            } else if (JRMCoreH.NC() && itemstack.getItem() instanceof ItemArmor) {
                if (hair.getItem().getUnlocalizedName().endsWith("Headband")) {
                    doit = false;
                }
                if (hair.getItem().getUnlocalizedName().replaceAll("item.", "").startsWith("akatsuki")) {
                    doit = true;
                }
            } else {
                doit = true;
            }
        } else {
            doit = true;
        }
        final boolean dbc = JRMCoreH.DBC();
        final boolean nc = JRMCoreH.NC();
        final boolean saoc = JRMCoreH.SAOC();
        if (JRMCoreH.plyrs != null && JRMCoreH.plyrs.length > 0 && !par1AbstractClientPlayer.isInvisible() && JRMCoreH.dnn(1) && ((JRMCoreH.dnn(2) && JRMCoreH.dnn(4) && JRMCoreH.dnn(5) && JRMCoreH.dnn(19)) || (!dbc && !saoc && !nc))) {
            for (int pl = 0; pl < JRMCoreH.plyrs.length; ++pl) {
                if (JRMCoreH.plyrs[pl].equals(par1AbstractClientPlayer.getCommandSenderName())) {
                    this.pl = par1AbstractClientPlayer.getEntityId();
                    final String[] s = JRMCoreH.data1[pl].split(";");
                    String playerStatusEff = JRMCoreH.StusEfctsClient(par1AbstractClientPlayer);
                    final String[] dummy = {"0", "0", "0"};
                    final int rg = (JRMCoreH.data4 == null) ? 0 : Integer.parseInt(JRMCoreH.data4[pl].split(";")[0]);
                    final String[] state = (JRMCoreH.data2 == null) ? dummy : JRMCoreH.data2[pl].split(";");
                    final String dns = s[1];
                    final int pwr = Integer.parseInt(s[2]);
                    final int rc = Integer.parseInt(s[0]);
                    final int cls = Integer.parseInt(s[3]);
                    final int weight = Integer.parseInt(s[5].split(",")[0]);
                    int st = (JRMCoreH.rc_arc(rc) && JRMCoreGuiScreen.ufc) ? 6 : ((pwr == 2 || rc == 0) ? 0 : Byte.parseByte(state[0]));

                    //Change st based on what form we're in
                    if (playerStatusEff.contains(JbraHelper.formsEff.get(4)[1])) {
                        st = 5;
                    }else if (playerStatusEff.contains(JbraHelper.formsEff.get(4)[2])) {
                        st = 6;
                    }else if (playerStatusEff.contains(JbraHelper.formsEff.get(4)[0])) {
                        st = 4;
                    }


                    final int stY = Byte.parseByte(state[0]);
                    final boolean saiOozar = JRMCoreH.rSai(rc) && (st == 7 || st == 8);
                    final int gen = JRMCoreH.dnsGender(dns);
                    final int haircol = JRMCoreH.dnsHairC(dns);
                    final int hairback = JRMCoreH.dnsHairB(dns);
                    final int breast = JRMCoreH.dnsBreast(dns);
                    final int skintype = JRMCoreH.dnsSkinT(dns);
                    final boolean iau = JRMCoreH.rc_arc(rc) && st == 6;
                    String dnsau = JRMCoreH.data(pl, 16, "");
                    dnsau = (dnsau.contains(";") ? dnsau.substring(1) : (JRMCoreH.plyrs[pl].equals(JBRAClient.mc.thePlayer.getCommandSenderName()) ? dnsau : ""));
                    final int bodytype = (skintype == 0) ? JRMCoreH.dnsBodyC1_0(dns) : JRMCoreH.dnsBodyT(dns);
                    final int bodycm = (skintype == 0) ? 0 : (iau ? JRMCoreH.dnsauCM(dnsau) : JRMCoreH.dnsBodyCM(dns));
                    final int bodyc1 = (skintype == 0) ? 0 : (iau ? JRMCoreH.dnsauC1(dnsau) : JRMCoreH.dnsBodyC1(dns));
                    final int bodyc2 = (skintype == 0) ? 0 : (iau ? JRMCoreH.dnsauC2(dnsau) : JRMCoreH.dnsBodyC2(dns));
                    final int bodyc3 = (skintype == 0) ? 0 : (iau ? JRMCoreH.dnsauC3(dnsau) : JRMCoreH.dnsBodyC3(dns));
                    final int facen = (skintype == 0) ? 0 : JRMCoreH.dnsFaceN(dns);
                    final int facem = (skintype == 0) ? 0 : JRMCoreH.dnsFaceM(dns);
                    final int eyes = (skintype == 0) ? 0 : JRMCoreH.dnsEyes(dns);
                    final int eyec1 = (skintype == 0) ? 0 : JRMCoreH.dnsEyeC1(dns);
                    final int eyec2 = (skintype == 0) ? 0 : JRMCoreH.dnsEyeC2(dns);
                    final String[] dat5 = (JRMCoreH.data5 == null) ? dummy : JRMCoreH.data5[pl].split(";");
                    final boolean lg = JRMCoreH.lgndb(pl, rc, st);
                    final boolean v = JRMCoreH.StusEfctsClient(17, pl);
                    final boolean l = JRMCoreH.StusEfctsClient(19, pl);
                    int hc = haircol;
                    final int Hair = hairback;
                    int ultra_instinct_level = 0;
                    boolean ultra_instinct_color = false;
                    if (JRMCoreH.DBC() && l && JGConfigUltraInstinct.CONFIG_UI_LEVELS > 0) {
                        final byte id = (JGConfigUltraInstinct.CONFIG_UI_LEVELS < Byte.parseByte(state[1])) ? JGConfigUltraInstinct.CONFIG_UI_LEVELS : Byte.parseByte(state[1]);
                        ultra_instinct_level = JRMCoreH.state2UltraInstinct(false, id);
                        ultra_instinct_color = JGConfigUltraInstinct.CONFIG_UI_HAIR_WHITE[ultra_instinct_level];
                    }
                    final int suphcol = (JRMCoreH.rc_sai(rc) && dbc) ? JRMCoreHDBC.col_fe(0, 0, pwr, rc, st, v, lg, l, ultra_instinct_color) : ((dbc && l && ultra_instinct_color) ? JRMCoreHDBC.col_fe(0, 0, pwr, rc, st, v, lg, l, ultra_instinct_color) : 0);
                    final int supecoll = dbc ? JRMCoreHDBC.col_fe(1, eyec1, pwr, rc, stY, v, lg, l) : eyec1;
                    final int supecolr = dbc ? JRMCoreHDBC.col_fe(1, eyec2, pwr, rc, stY, v, lg, l) : eyec2;
                    final String[] StE = (JRMCoreH.dat19 == null) ? dummy : JRMCoreH.dat19[pl].split(";");
                    final byte ts = Byte.parseByte(StE[0]);
                    final boolean mj = JRMCoreH.StusEfctsClient(12, pl);
                    final boolean msk = JRMCoreH.StusEfctsClient(6, pl) || playerStatusEff.contains(JbraHelper.formsEff.get(4)[1]); //Include second custom form too
                    final ExtendedPlayer props = ExtendedPlayer.get(par1AbstractClientPlayer);
                    String dnsH = (props.getHairCode().length() > 5) ? props.getHairCode() : "";
                    dnsH = JRMCoreH.dnsHairG1toG2(dnsH);
                    final int plyrSpc = (skintype == 0) ? 0 : ((JRMCoreH.RaceCustomSkin[rc] == 0) ? 0 : ((bodytype >= JRMCoreH.Specials[rc]) ? (JRMCoreH.Specials[rc] - 1) : bodytype));
                    final float f5 = par1AbstractClientPlayer.prevRotationYaw + (par1AbstractClientPlayer.rotationYaw - par1AbstractClientPlayer.prevRotationYaw) * par2 - (par1AbstractClientPlayer.prevRenderYawOffset + (par1AbstractClientPlayer.renderYawOffset - par1AbstractClientPlayer.prevRenderYawOffset) * par2);
                    final float f6 = par1AbstractClientPlayer.prevRotationPitch + (par1AbstractClientPlayer.rotationPitch - par1AbstractClientPlayer.prevRotationPitch) * par2;
                    if (JRMCoreH.DBC()) {
                        kk2 = JRMCoreH.StusEfctsMe(5);
                        kk = Byte.parseByte(state[1]) + 1;
                        if (kk2) {
                            r = kk / 15.0f;
                            g = -(kk / 15.0f);
                            b = -(kk / 15.0f);
                            if (r > 1.0f) {
                                r = 1.0f;
                            }
                            if (g < 0.0f) {
                                r = 0.0f;
                            }
                            if (b < 0.0f) {
                                r = 0.0f;
                            }
                        } else {
                            r = 0.0f;
                            g = 0.0f;
                            b = 0.0f;
                        }
                    }
                    GL11.glPushMatrix();
                    if (rc == 3 && dbc) {
                        final int j = 5095183;
                        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc:gui/allw.png"));
                        final float h1 = 1.0f;
                        glColor3f(bodycm);
                        this.modelMain.renderHairs(0.0625f, "N");
                        ResourceLocation bdyskn = new ResourceLocation("jinryuudragonbc:cc/nam/0nam" + plyrSpc + ".png");
                        if (par1AbstractClientPlayer.getEntityId() == JBRAClient.mc.thePlayer.getEntityId() && curSkin == null) {
                            curSkin = bdyskn;
                        }
                        Minecraft.getMinecraft().renderEngine.bindTexture(bdyskn);
                        glColor3f(bodycm);
                        this.modelMain.renderBody(0.0625f);
                        bdyskn = new ResourceLocation("jinryuudragonbc:cc/nam/1nam" + plyrSpc + ".png");
                        if (par1AbstractClientPlayer.getEntityId() == JBRAClient.mc.thePlayer.getEntityId() && curSkin == null) {
                            curSkin = bdyskn;
                        }
                        Minecraft.getMinecraft().renderEngine.bindTexture(bdyskn);
                        glColor3f(bodyc1);
                        this.modelMain.renderBody(0.0625f);
                        bdyskn = new ResourceLocation("jinryuudragonbc:cc/nam/2nam" + plyrSpc + ".png");
                        if (par1AbstractClientPlayer.getEntityId() == JBRAClient.mc.thePlayer.getEntityId() && curSkin == null) {
                            curSkin = bdyskn;
                        }
                        Minecraft.getMinecraft().renderEngine.bindTexture(bdyskn);
                        glColor3f(bodyc2);
                        this.modelMain.renderBody(0.0625f);
                        bdyskn = new ResourceLocation("jinryuudragonbc:cc/nam/3nam" + plyrSpc + ".png");
                        if (par1AbstractClientPlayer.getEntityId() == JBRAClient.mc.thePlayer.getEntityId() && curSkin == null) {
                            curSkin = bdyskn;
                        }
                        Minecraft.getMinecraft().renderEngine.bindTexture(bdyskn);
                        GL11.glColor3f(h1 + getR(), h1 + getG(), h1 + getB());
                        this.modelMain.renderBody(0.0625f);
                        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc", "cc/nam/4namn" + facen + ".png"));
                        glColor3f(bodycm);
                        this.modelMain.renderHairs(0.0625f, "FACENOSE");
                        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc", "cc/nam/4namm" + facem + ".png"));
                        glColor3f(bodycm);
                        this.modelMain.renderHairs(0.0625f, "FACEMOUTH");
                        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc", "cc/nam/4namb" + eyes + ".png"));
                        GL11.glColor3f(1.0f + getR(), 1.0f + getG(), 1.0f + getB());
                        this.modelMain.renderHairs(0.0625f, "EYEBASE");
                        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc", "cc/nam/4naml" + eyes + ".png"));
                        glColor3f((JRMCoreH.rc_sai(rc) || JRMCoreHDBC.godKiUserBase(rc, stY) || l) ? supecoll : eyec1);
                        this.modelMain.renderHairs(0.0625f, "EYELEFT");
                        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc", "cc/nam/4namr" + eyes + ".png"));
                        glColor3f((JRMCoreH.rc_sai(rc) || JRMCoreHDBC.godKiUserBase(rc, stY) || l) ? supecolr : eyec2);
                        this.modelMain.renderHairs(0.0625f, "EYERIGHT");
                        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc", "cc/nam/4namw" + eyes + ".png"));
                        glColor3f(bodycm);
                        this.modelMain.renderHairs(0.0625f, "EYEBROW");
                    } else if (rc == 4 && dbc) {
                        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc:cc/arc/" + ((gen == 1) ? "f" : "m") + "/0B" + JRMCoreH.TransFrSkn2[st] + plyrSpc + ".png"));
                        glColor3f(bodycm);
                        this.modelMain.renderHairs(0.0625f, ((ts == 4) ? "n" : "") + "FR" + JRMCoreH.TransFrHrn[st]);
                        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc:cc/arc/" + ((gen == 1) ? "f" : "m") + "/1B" + JRMCoreH.TransFrSkn2[st] + plyrSpc + ".png"));
                        glColor3f(bodyc1);
                        this.modelMain.renderHairs(0.0625f, ((ts == 4) ? "n" : "") + "FR" + JRMCoreH.TransFrHrn[st]);
                        ResourceLocation bdyskn2 = new ResourceLocation("jinryuudragonbc:cc/arc/" + ((gen == 1) ? "f" : "m") + "/1A" + JRMCoreH.TransFrSkn[st] + plyrSpc + ".png");
                        if (par1AbstractClientPlayer.getEntityId() == JBRAClient.mc.thePlayer.getEntityId() && curSkin == null) {
                            curSkin = bdyskn2;
                        }
                        Minecraft.getMinecraft().renderEngine.bindTexture(bdyskn2);
                        glColor3f(bodyc1);
                        this.modelMain.renderBody(0.0625f);
                        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc:cc/arc/" + ((gen == 1) ? "f" : "m") + "/2B" + JRMCoreH.TransFrSkn2[st] + plyrSpc + ".png"));
                        glColor3f(bodyc2);
                        this.modelMain.renderHairs(0.0625f, ((ts == 4) ? "n" : "") + "FR" + JRMCoreH.TransFrHrn[st]);
                        bdyskn2 = new ResourceLocation("jinryuudragonbc:cc/arc/" + ((gen == 1) ? "f" : "m") + "/2A" + JRMCoreH.TransFrSkn[st] + plyrSpc + ".png");
                        if (par1AbstractClientPlayer.getEntityId() == JBRAClient.mc.thePlayer.getEntityId() && curSkin == null) {
                            curSkin = bdyskn2;
                        }
                        Minecraft.getMinecraft().renderEngine.bindTexture(bdyskn2);
                        glColor3f(bodyc2);
                        this.modelMain.renderBody(0.0625f);
                        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc:cc/arc/" + ((gen == 1) ? "f" : "m") + "/3B" + JRMCoreH.TransFrSkn2[st] + plyrSpc + ".png"));
                        glColor3f(bodyc3);
                        this.modelMain.renderHairs(0.0625f, ((ts == 4) ? "n" : "") + "FR" + JRMCoreH.TransFrHrn[st]);
                        bdyskn2 = new ResourceLocation("jinryuudragonbc:cc/arc/" + ((gen == 1) ? "f" : "m") + "/3A" + JRMCoreH.TransFrSkn[st] + plyrSpc + ".png");
                        if (par1AbstractClientPlayer.getEntityId() == JBRAClient.mc.thePlayer.getEntityId() && curSkin == null) {
                            curSkin = bdyskn2;
                        }
                        Minecraft.getMinecraft().renderEngine.bindTexture(bdyskn2);
                        glColor3f(bodyc3);
                        this.modelMain.renderBody(0.0625f);
                        bdyskn2 = new ResourceLocation("jinryuudragonbc:cc/arc/" + ((gen == 1) ? "f" : "m") + "/0A" + JRMCoreH.TransFrSkn[st] + plyrSpc + ".png");
                        if (par1AbstractClientPlayer.getEntityId() == JBRAClient.mc.thePlayer.getEntityId() && curSkin == null) {
                            curSkin = bdyskn2;
                        }
                        Minecraft.getMinecraft().renderEngine.bindTexture(bdyskn2);
                        glColor3f(bodycm);
                        this.modelMain.renderBody(0.0625f);
                        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc:cc/arc/" + ((gen == 1) ? "f" : "m") + "/4B" + JRMCoreH.TransFrSkn2[st] + plyrSpc + ".png"));
                        final float h1 = 1.0f;
                        GL11.glColor3f(h1 + getR(), h1 + getG(), h1 + getB());
                        this.modelMain.renderHairs(0.0625f, ((ts == 4) ? "n" : "") + "FR" + JRMCoreH.TransFrHrn[st]);
                        bdyskn2 = new ResourceLocation("jinryuudragonbc:cc/arc/" + ((gen == 1) ? "f" : "m") + "/4A" + JRMCoreH.TransFrSkn[st] + plyrSpc + ".png");
                        if (par1AbstractClientPlayer.getEntityId() == JBRAClient.mc.thePlayer.getEntityId() && curSkin == null) {
                            curSkin = bdyskn2;
                        }
                        Minecraft.getMinecraft().renderEngine.bindTexture(bdyskn2);
                        GL11.glColor3f(h1 + getR(), h1 + getG(), h1 + getB());
                        this.modelMain.renderBody(0.0625f);
                        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc", "cc/arc/" + ((gen == 1) ? "f" : "m") + "/4A" + JRMCoreH.TransFrSkn[st] + plyrSpc + "n" + facen + ".png"));
                        glColor3f(bodyc1);
                        this.modelMain.renderHairs(0.0625f, "FACENOSE");
                        if (st == 5 && msk) {
                            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc", "cc/arc/" + ((gen == 1) ? "f" : "m") + "/0A" + JRMCoreH.TransFrSkn[st] + plyrSpc + "a.png"));
                            glColor3f(bodycm);
                            this.modelMain.renderHairs(0.0625f, "FACEMOUTH");
                        } else {
                            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc", "cc/arc/" + ((gen == 1) ? "f" : "m") + "/4A" + JRMCoreH.TransFrSkn[st] + plyrSpc + "m" + facem + ".png"));
                            glColor3f(bodyc1);
                            this.modelMain.renderHairs(0.0625f, "FACEMOUTH");
                        }
                        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc", "cc/arc/" + ((gen == 1) ? "f" : "m") + "/4A" + JRMCoreH.TransFrSkn[st] + plyrSpc + "b" + eyes + ".png"));
                        GL11.glColor3f(1.0f + getR(), 1.0f + getG(), 1.0f + getB());
                        this.modelMain.renderHairs(0.0625f, "EYEBASE");
                        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc", "cc/arc/" + ((gen == 1) ? "f" : "m") + "/4A" + JRMCoreH.TransFrSkn[st] + plyrSpc + "l" + eyes + ".png"));
                        glColor3f((JRMCoreH.rc_sai(rc) || JRMCoreHDBC.godKiUserBase(rc, stY) || l) ? supecoll : eyec1);
                        this.modelMain.renderHairs(0.0625f, "EYELEFT");
                        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc", "cc/arc/" + ((gen == 1) ? "f" : "m") + "/4A" + JRMCoreH.TransFrSkn[st] + plyrSpc + "r" + eyes + ".png"));
                        glColor3f((JRMCoreH.rc_sai(rc) || JRMCoreHDBC.godKiUserBase(rc, stY) || l) ? supecolr : eyec2);
                        this.modelMain.renderHairs(0.0625f, "EYERIGHT");
                    } else {
                        if (saiOozar) {
                            ResourceLocation bdyskn2 = new ResourceLocation("jinryuudragonbc:cc/oozaru1.png");
                            if (par1AbstractClientPlayer.getEntityId() == JBRAClient.mc.thePlayer.getEntityId() && curSkin == null) {
                                curSkin = bdyskn2;
                            }
                            Minecraft.getMinecraft().renderEngine.bindTexture(bdyskn2);
                            glColor3f((skintype != 0) ? bodycm : 11374471);
                            this.modelMain.renderBody(0.0625f);
                            final int tailCol = (rc == 2 || bodytype != 0) ? bodytype : 6498048;
                            final int i = (st == 0 || st == 7) ? ((skintype == 1) ? bodyc1 : tailCol) : (lg ? 10092390 : 16574610);
                            bdyskn2 = new ResourceLocation("jinryuudragonbc:cc/oozaru2.png");
                            Minecraft.getMinecraft().renderEngine.bindTexture(bdyskn2);
                            glColor3f(i);
                            this.modelMain.renderBody(0.0625f);
                            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc:cc/oozaru0.png"));
                            GL11.glColor3f(1.0f + getR(), 1.0f + getG(), 1.0f + getB());
                            this.modelMain.renderHairs(0.0625f, "EYEBASE");
                            glColor3f((skintype != 0) ? bodycm : 11374471);
                            this.modelMain.renderHairs(0.0625f, "OOZARU");
                        } else if (skintype != 0) {
                            final ResourceLocation bdyskn2 = new ResourceLocation("jinryuumodscore:cc/" + ((gen == 1) ? "f" : "") + "hum.png");
                            if (par1AbstractClientPlayer.getEntityId() == JBRAClient.mc.thePlayer.getEntityId() && curSkin == null) {
                                curSkin = bdyskn2;
                            }
                            Minecraft.getMinecraft().renderEngine.bindTexture(bdyskn2);
                            glColor3f(bodycm);
                            this.modelMain.renderBody(0.0625f);
                            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuumodscore", "cc/" + ((gen == 1) ? "f" : "") + "humn" + facen + ".png"));
                            glColor3f(bodycm);
                            this.modelMain.renderHairs(0.0625f, "FACENOSE");
                            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuumodscore", "cc/" + ((gen == 1) ? "f" : "") + "humm" + facem + ".png"));
                            glColor3f(bodycm);
                            this.modelMain.renderHairs(0.0625f, "FACEMOUTH");
                            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuumodscore", "cc/" + ((gen == 1) ? "f" : "") + "humb" + eyes + ".png"));
                            GL11.glColor3f(1.0f + getR(), 1.0f + getG(), 1.0f + getB());
                            this.modelMain.renderHairs(0.0625f, "EYEBASE");
                            if (supecoll > 0) {
                                if (pwr == 2 && cls == 1) {
                                    Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(JRMCoreH.tjnc, "cc/cl" + cls + "/" + ((gen == 1) ? "f" : "") + "huml" + eyes + ".png"));
                                    glColor3f(15590377);
                                } else if (pwr == 2 && cls == 2 && stY > 0) {
                                    Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(JRMCoreH.tjnc, "cc/cl" + cls + "/" + ((gen == 1) ? "f" : "") + "huml" + eyes + ".png"));
                                    glColor3f(13828096);
                                } else {
                                    Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuumodscore", "cc/" + ((gen == 1) ? "f" : "") + "huml" + eyes + ".png"));
                                    glColor3f((dbc && (JRMCoreH.rc_sai(rc) || JRMCoreHDBC.godKiUserBase(rc, stY) || l)) ? supecoll : eyec1);
                                }
                                this.modelMain.renderHairs(0.0625f, "EYELEFT");
                            }
                            if (supecolr > 0) {
                                if (pwr == 2 && cls == 1) {
                                    Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(JRMCoreH.tjnc, "cc/cl" + cls + "/" + ((gen == 1) ? "f" : "") + "humr" + eyes + ".png"));
                                    glColor3f(15590377);
                                } else if (pwr == 2 && cls == 2 && stY > 0) {
                                    Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(JRMCoreH.tjnc, "cc/cl" + cls + "/" + ((gen == 1) ? "f" : "") + "humr" + eyes + ".png"));
                                    glColor3f(13828096);
                                } else {
                                    Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(JRMCoreH.tjjrmc, "cc/" + ((gen == 1) ? "f" : "") + "humr" + eyes + ".png"));
                                    glColor3f((dbc && (JRMCoreH.rc_sai(rc) || JRMCoreHDBC.godKiUserBase(rc, stY) || l)) ? supecolr : eyec2);
                                }
                                this.modelMain.renderHairs(0.0625f, "EYERIGHT");
                            }
                            final boolean b3 = (rc == 1 || rc == 2) && Integer.parseInt(state[0]) == 6;
                            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuumodscore", "cc/" + (b3 ? "ssj3eyebrow/" : "") + ((gen == 1) ? "f" : "") + "humw" + eyes + ".png"));
                            if (!b3) {
                                if (l && ultra_instinct_color) {
                                    glColor3f(15790320, age);
                                } else if ((rc == 1 || rc == 2) && st != 0) {
                                    glColor3f(suphcol);
                                } else {
                                    glColor3f(haircol, age);
                                }
                            } else {
                                GL11.glColor3f(1.0f, 1.0f, 1.0f);
                            }
                            this.modelMain.renderHairs(0.0625f, "EYEBROW");
                        }
                        if (JRMCoreH.rc_sai(rc) && dbc) {
                            final float h2 = 1.0f;
                            final int tailCol = (rc == 2 || bodytype != 0) ? bodytype : 6498048;
                            int i = (st == 0 || st == 7 || st == 14) ? ((skintype == 1) ? bodyc1 : tailCol) : suphcol;
                            if (JRMCoreH.rSai(rc) && i == 6498048) {
                                if (st == 14) {
                                    i = (JRMCoreH.func_26n6() ? 13292516 : 14292268);
                                } else if (l && ultra_instinct_color) {
                                    i = 15790320;
                                }
                            }
                            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc:gui/allw.png"));
                            glColor3f(i);
                            this.modelMain.renderHairs(0.0625f, (ts == 0 || ts == -1) ? "SJT1" : ((ts == 1) ? "SJT2" : ""));
                        }
                        final float h2 = 1.0f;
                        if (!saiOozar) {
                            if (skintype == 0 && gen >= 1) {
                                final ResourceLocation bdyskn3 = par1AbstractClientPlayer.getLocationSkin().equals(steveTextures) ? fem : par1AbstractClientPlayer.getLocationSkin();
                                if (par1AbstractClientPlayer.getEntityId() == JBRAClient.mc.thePlayer.getEntityId() && curSkin == null) {
                                    curSkin = bdyskn3;
                                }
                                if (JBRAH.JHDS() && JBRAH.getSkinHas(data)) {
                                    Minecraft.getMinecraft().renderEngine.bindTexture(JBRAH.getSkinLoc(data));
                                } else {
                                    Minecraft.getMinecraft().renderEngine.bindTexture(bdyskn3);
                                }
                                GL11.glColor3f(h2 + getR(), h2 + getG(), h2 + getB());
                                this.modelMain.renderBody(0.0625f);
                            } else if (JBRAH.JHDS() && JBRAH.getSkinHas(data) && skintype == 0) {
                                GL11.glColor3f(h2 + getR(), h2 + getG(), h2 + getB());
                                Minecraft.getMinecraft().renderEngine.bindTexture(JBRAH.getSkinLoc(data));
                                this.modelMain.renderBody(0.0625f);
                                curSkin = null;
                            }
                        }
                        boolean b4 = false;
                        Label_6104:
                        {
                            Label_6103:
                            {
                                if (l) {
                                    if (!ultra_instinct_color) {
                                        break Label_6103;
                                    }
                                } else if (st == 0) {
                                    break Label_6103;
                                }
                                if (st != 14) {
                                    b4 = true;
                                    break Label_6104;
                                }
                            }
                            b4 = false;
                        }
                        final boolean bc = b4;
                        if ((Hair == 8 || Hair == 9) && (st == 0 || st == 1)) {
                            hc = (bc ? suphcol : hc);
                            final String s2 = (Hair == 8) ? "c2" : "c1";
                            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuumodscore:gui/" + s2 + ".png"));
                        } else if (Hair >= 0 && Hair <= 12) {
                            hc = (bc ? suphcol : ((st == 14 && JRMCoreH.func_26n6()) ? 13292516 : hc));
                            final String s2 = (st == 0) ? "normall" : "superall";
                            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuumodscore:gui/" + s2 + ".png"));
                        }
                        if (bc) {
                            glColor3f(hc);
                        } else {
                            glColor3f(hc, age);
                        }
                        final boolean scouter = false;
                        boolean helmet = false;
                        if (itemstack != null) {
                            final Item item = itemstack.getItem();
                            if (JRMCoreH.DBC() && item instanceof ItemArmor) {
                                final ItemArmor itemarmor = (ItemArmor) item;
                                helmet = true;
                            }
                        }
                        boolean vanity_head = false;
                        final String[][] slot_vanity_num = new String[8][];
                        final int[] slot_van = new int[8];
                        for (int k = 0; k < 8; ++k) {
                            slot_vanity_num[k] = s[8 + k].split(",");
                            slot_van[k] = Integer.parseInt(slot_vanity_num[k][0]);
                            if (!vanity_head && slot_van[k] > 0) {
                                new Item();
                                final Item vanity_check = Item.getItemById(slot_van[k]);
                                if (vanity_check instanceof ItemVanity && ((ItemVanity) vanity_check).armorType == 5) {
                                    final int n = slot_van[k];
                                    final Item coat_2 = ItemsDBC.Coat_2;
                                    if (n != Item.getIdFromItem(ItemsDBC.Coat_2)) {
                                        final int n2 = slot_van[k];
                                        final Item coat = ItemsDBC.Coat;
                                        if (n2 != Item.getIdFromItem(ItemsDBC.Coat)) {
                                            vanity_head = true;
                                        }
                                    }
                                }
                            }
                        }
                        if (!saiOozar) {
                            RenderPlayerJBRA obj = new RenderPlayerJBRA();
                            final boolean dhhwho = !JRMCoreConfig.HHWHO || ((!helmet && !vanity_head) || scouter);
                            if (Hair == 12 && dhhwho && dnsH.length() > 3) {
                                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuumodscore:gui/normall.png"));
                                if (par1AbstractClientPlayer == JBRAClient.mc.thePlayer && JRMCoreGuiScreen.hairPreview > 0) {
                                    st = JRMCoreGuiScreen.hairPreviewStates[JRMCoreGuiScreen.hairPreview];
                                }

                                if (playerStatusEff.contains(JbraHelper.formsEff.get(1)[0])) {
                                    st = 4;
                                } else if (playerStatusEff.contains(JbraHelper.formsEff.get(1)[1])) {
                                    st = 5;
                                } else if (playerStatusEff.contains(JbraHelper.formsEff.get(1)[2])) {
                                    st = 6;
                                    this.modelMain.renderHairs(0.0625f, "" + JRMCoreH.HairsT[6] + JRMCoreH.Hairs[0]);
                                }

                                if (st == 6) {
                                    this.modelMain.renderHairs(0.0625f, "" + JRMCoreH.HairsT[6] + JRMCoreH.Hairs[0]);
                                } else if (st == 14) {
                                    this.modelMain.renderHairsV2(0.0625f, "373852546750347428545480193462285654801934283647478050340147507467501848505072675018255250726750183760656580501822475071675018255050716750189730327158501802475071675018973225673850189765616160501820414547655019545654216550195754542165501920475027655019943669346576193161503065231900475030655019406534276538199465393460501997654138655019976345453950189760494941501897615252415018976354563850189763494736501897614949395018976152523950189763525234501897584749395018976150493850189760545234501897585250415018885445474550189754475041501897545250435018885454523950185143607861501897415874585018514369196150185147768078391865525680565018974356806150188843567861501868396374615018975056805650189750568056501885582374615018975823726150187149568054501877495680565018774950785650189163236961501820", 0.0f, 0, 0, pl, rc, obj);
                                } else {
                                    this.modelMain.renderHairsV2(0.0625f, dnsH, 0.0f, st, rg, pl, rc, obj);
                                }
                            } else if (Hair == 10) {
                                GL11.glColor3f(h2 + getR(), h2 + getG(), h2 + getB());
                                if (JBRAH.JHDS() && JBRAH.getSkinHas(data)) {
                                    Minecraft.getMinecraft().renderEngine.bindTexture(JBRAH.getSkinLoc(data));
                                } else {
                                    Minecraft.getMinecraft().renderEngine.bindTexture(par1AbstractClientPlayer.getLocationSkin());
                                }
                                this.modelMain.renderHeadwear(0.0625f);
                            } else if (dhhwho) {
                                if (st == 14) {
                                    this.modelMain.renderHairsV2(0.0625f, "373852546750347428545480193462285654801934283647478050340147507467501848505072675018255250726750183760656580501822475071675018255050716750189730327158501802475071675018973225673850189765616160501820414547655019545654216550195754542165501920475027655019943669346576193161503065231900475030655019406534276538199465393460501997654138655019976345453950189760494941501897615252415018976354563850189763494736501897614949395018976152523950189763525234501897584749395018976150493850189760545234501897585250415018885445474550189754475041501897545250435018885454523950185143607861501897415874585018514369196150185147768078391865525680565018974356806150188843567861501868396374615018975056805650189750568056501885582374615018975823726150187149568054501877495680565018774950785650189163236961501820", 0.0f, 0, 0, pl, rc, obj);
                                } else {
                                    this.modelMain.renderHairs(0.0625f, "" + JRMCoreH.HairsT[st] + JRMCoreH.Hairs[Hair]);
                                }
                            }
                            if (st == 14) {
                                int tailCol2 = (rc == 2 || bodytype != 0) ? bodytype : 6498048;
                                tailCol2 = (JRMCoreH.func_26n6() ? 13292516 : tailCol2);
                                int jx = (skintype == 1) ? bodyc1 : tailCol2;
                                if (JRMCoreH.rSai(rc) && jx == 6498048 && st == 14) {
                                    if (JRMCoreH.func_26n6()) {
                                        jx = 13292516;
                                    } else {
                                        jx = 14292268;
                                    }
                                }
                                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc:cc/ss4" + ((skintype == 0) ? "a" : "b") + ".png"));
                                glColor3f(jx);
                                this.modelMain.renderBody(0.0625f);
                            }
                        }
                    }
                    if (mj) {
                        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc", "textures/misc/m.png"));
                        GL11.glColor3f(1.0f + getR(), 1.0f + getG(), 1.0f + getB());
                        this.modelMain.renderHairs(0.0625f, "EYERIGHT");
                    }
                    if (dbc) {
                        final int w = weight;
                        final String[] wnam = {"wshell", "whandleg"};
                        if (w >= 0 && w < wnam.length) {
                            final String[] wloc = {"roshiShell", "weightBands"};
                            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc:textures/misc/" + wloc[w] + ".png"));
                            GL11.glColor3f(1.0f + getR(), 1.0f + getG(), 1.0f + getB());
                            this.modelMain.renderHairs(0.0625f, wnam[w]);
                        }
                    }
                    if (JGConfigClientSettings.CLIENT_DA19 && (JRMCoreH.DBC() || JRMCoreH.NC())) {
                        GL11.glPushMatrix();
                        GL11.glEnable(3042);
                        GL11.glDisable(2896);
                        GL11.glBlendFunc(770, 771);
                        GL11.glAlphaFunc(516, 0.003921569f);
                        GL11.glDepthMask(false);
                        final int[] PlyrAttrbts = new int[JRMCoreH.PlyrAttrbts.length];
                        final String[] stri = JRMCoreH.dat14[pl].split(",");
                        for (int m = 0; m < PlyrAttrbts.length; ++m) {
                            PlyrAttrbts[m] = Integer.parseInt(stri[m]);
                        }
                        final int maxBody = JRMCoreH.stat(par1AbstractClientPlayer, 2, pwr, 2, PlyrAttrbts[2], rc, cls, 0.0f);
                        final int curBody = Integer.parseInt(JRMCoreH.data(par1AbstractClientPlayer.getCommandSenderName(), 8, "200"));
                        final float one = maxBody / 100.0f;
                        final int perc = (int) (curBody / one);
                        if (perc < 70) {
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuumodscore:cc/bruises1.png"));
                            this.modelMain.renderBody(0.0625f);
                        }
                        if (perc < 55) {
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuumodscore:cc/bruises2.png"));
                            this.modelMain.renderBody(0.0625f);
                        }
                        if (perc < 35) {
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuumodscore:cc/bruises3.png"));
                            this.modelMain.renderBody(0.0625f);
                        }
                        if (perc < 20) {
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuumodscore:cc/bruises4.png"));
                            this.modelMain.renderBody(0.0625f);
                        }
                        GL11.glDepthMask(true);
                        GL11.glEnable(2896);
                        GL11.glDisable(3042);
                        GL11.glPopMatrix();
                        if (JRMCoreH.NC() && pwr == 2) {
                            if (cls == 1 && stY > 0) {
                                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuunarutoc", "misc/dojutsu/byakugan1_" + eyes + ".png"));
                                GL11.glColor3f(1.0f + getR(), 1.0f + getG(), 1.0f + getB());
                                this.modelMain.renderHairs(0.0625f, "EYERIGHT");
                            } else if (cls == 2 && stY > 0) {
                                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuunarutoc", "misc/dojutsu/sharingan3_" + eyes + ".png"));
                                GL11.glColor3f(1.0f + getR(), 1.0f + getG(), 1.0f + getB());
                                this.modelMain.renderHairs(0.0625f, "EYERIGHT");
                            }
                        }
                    }
                    GL11.glPopMatrix();
                    doit = false;
                    break;
                }
            }
        }
        if (JRMCoreH.plyrs != null && JRMCoreH.plyrs.length > 0 && !par1AbstractClientPlayer.isInvisible() && JRMCoreH.dnn(13)) {
            for (int pl = 0; pl < JRMCoreH.plyrs.length; ++pl) {
                if (JRMCoreH.plyrs[pl].equals(par1AbstractClientPlayer.getCommandSenderName()) && JRMCoreH.aliveState(pl)) {
                    final float f5 = par1AbstractClientPlayer.prevRotationYaw + (par1AbstractClientPlayer.rotationYaw - par1AbstractClientPlayer.prevRotationYaw) * par2 - (par1AbstractClientPlayer.prevRenderYawOffset + (par1AbstractClientPlayer.renderYawOffset - par1AbstractClientPlayer.prevRenderYawOffset) * par2);
                    final float f6 = par1AbstractClientPlayer.prevRotationPitch + (par1AbstractClientPlayer.rotationPitch - par1AbstractClientPlayer.prevRotationPitch) * par2;
                    GL11.glPushMatrix();
                    Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("jinryuudragonbc:armor/halo.png"));
                    GL11.glColor3f(1.0f + getR(), 1.0f + getG(), 1.0f + getB());
                    this.modelMain.renderHalo(0.0625f);
                    GL11.glPopMatrix();
                    break;
                }
            }
        }
        if (doit && !par1AbstractClientPlayer.isInvisible() && par1AbstractClientPlayer.func_152123_o()) {
            if (JBRAH.JHDS() && JBRAH.getSkinHas(data)) {
                Minecraft.getMinecraft().renderEngine.bindTexture(JBRAH.getSkinLoc(data));
            } else {
                Minecraft.getMinecraft().renderEngine.bindTexture(par1AbstractClientPlayer.getLocationSkin());
            }
            final float f5 = par1AbstractClientPlayer.prevRotationYaw + (par1AbstractClientPlayer.rotationYaw - par1AbstractClientPlayer.prevRotationYaw) * par2 - (par1AbstractClientPlayer.prevRenderYawOffset + (par1AbstractClientPlayer.renderYawOffset - par1AbstractClientPlayer.prevRenderYawOffset) * par2);
            final float f6 = par1AbstractClientPlayer.prevRotationPitch + (par1AbstractClientPlayer.rotationPitch - par1AbstractClientPlayer.prevRotationPitch) * par2;
            GL11.glPushMatrix();
            this.modelMain.renderHeadwear(0.0625f);
            GL11.glPopMatrix();
        }
        if (par1AbstractClientPlayer.getEntityId() == JBRAClient.mc.thePlayer.getEntityId() && curSkin == null) {
            curSkin = ((JBRAH.JHDS() && JBRAH.getSkinHas(data)) ? JBRAH.getSkinLoc(data) : par1AbstractClientPlayer.getLocationSkin());
        }
        if (!curSkinUp) {
            curSkinUp = true;
        }
    }

}
