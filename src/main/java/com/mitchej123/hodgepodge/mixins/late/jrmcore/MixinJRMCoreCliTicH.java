package com.mitchej123.hodgepodge.mixins.late.jrmcore;

import JinRyuu.JRMCore.*;
import JinRyuu.JRMCore.p.OpenGuiMessage;
import JinRyuu.JRMCore.p.PD;
import com.mitchej123.hodgepodge.util.JbraHelper;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovementInputFromOptions;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static JinRyuu.JRMCore.JRMCoreCliTicH.time;

@Mixin(JRMCoreCliTicH.class)
public class MixinJRMCoreCliTicH {
    @Shadow(remap = false) public static HashMap<Integer, Integer> DoubleTapPressCounter;
    @Shadow(remap = false) public static HashMap<Integer, Long> Tapped;
    @Shadow(remap = false) public static int currentTime;
    @Shadow(remap = false) public static int previousTime;
    @Shadow(remap = false) public static float countingValue;
    @Shadow(remap = false) public static float counterValue;
    @Shadow(remap = false) public static float smod;
    @Shadow(remap = false) public static int smodr;
    @Shadow(remap = false) public static int ts;
    @Shadow(remap = false) public static int ts2;
    @Shadow(remap = false) public static int ts5;
    @Shadow(remap = false) public static float offsetY;
    @Shadow(remap = false) public static float clientHght;
    @Shadow(remap = false) public static float yc;
    @Shadow(remap = false) public static int mw;
    @Shadow(remap = false) static int fnPressed;
    @Shadow(remap = false) public Minecraft mc;
    @Shadow(remap = false) public boolean wig;
    @Shadow(remap = false) private int check;
    @Shadow(remap = false) private boolean stand;
    @Shadow(remap = false) private boolean viewChange;
    @Shadow(remap = false) private int viewPrevious;


    private byte b(final int n) {
        return (byte) n;
    }

    public void onTickInGame() {
        MixinJRMCoreCliTicH.mw = Mouse.getDWheel();
        final EntityPlayer plyr = this.mc.thePlayer;
        if (JRMCoreH.data1 == null) {
            JRMCoreH.Race = this.b(0);
            JRMCoreH.dns = "0";
            JRMCoreH.Pwrtyp = this.b(0);
            JRMCoreH.Class = this.b(0);
            JRMCoreH.Accepted = this.b(2);
        }
        JRMCoreHC.t1s = (MixinJRMCoreCliTicH.ts <= 0);
        if (MixinJRMCoreCliTicH.ts > 0) {
            --MixinJRMCoreCliTicH.ts;
        } else {
            MixinJRMCoreCliTicH.ts = 20;
        }
        JRMCoreHC.t2s = (MixinJRMCoreCliTicH.ts2 <= 0);
        if (MixinJRMCoreCliTicH.ts2 > 0) {
            --MixinJRMCoreCliTicH.ts2;
        } else {
            MixinJRMCoreCliTicH.ts2 = 40;
        }
        JRMCoreHC.t5s = (MixinJRMCoreCliTicH.ts5 <= 0);
        if (MixinJRMCoreCliTicH.ts5 > 0) {
            --MixinJRMCoreCliTicH.ts5;
        } else {
            MixinJRMCoreCliTicH.ts5 = 100;
        }
        MixinJRMCoreCliTicH.currentTime = (int) (System.currentTimeMillis() / 1000L);
        if (MixinJRMCoreCliTicH.currentTime != MixinJRMCoreCliTicH.previousTime) {
            MixinJRMCoreCliTicH.previousTime = MixinJRMCoreCliTicH.currentTime;
            MixinJRMCoreCliTicH.counterValue = MixinJRMCoreCliTicH.countingValue;
            MixinJRMCoreCliTicH.countingValue = 0.0f;
        }
        if (MixinJRMCoreCliTicH.currentTime == MixinJRMCoreCliTicH.previousTime) {
            ++MixinJRMCoreCliTicH.countingValue;
        }
        final HashMap<Integer, Long> tapped = (HashMap<Integer, Long>) MixinJRMCoreCliTicH.Tapped.clone();
        for (final Map.Entry<Integer, Long> mapEntry : tapped.entrySet()) {
            final int k = mapEntry.getKey();
            final long v = mapEntry.getValue();
            if (v < time()) {
                MixinJRMCoreCliTicH.Tapped.remove(k);
                MixinJRMCoreCliTicH.DoubleTapPressCounter.remove(k);
            }
        }
        if (plyr != null && !plyr.isDead && this.mc.theWorld != null) {
            if (this.wig) {
                this.wig = false;
            }
            if (MixinJRMCoreCliTicH.smodr > 0) {
                --MixinJRMCoreCliTicH.smodr;
            }
            if (MixinJRMCoreCliTicH.smodr == 0 && MixinJRMCoreCliTicH.smod != 1.0f) {
                MixinJRMCoreCliTicH.smod = 1.0f;
            }
            float w = JRMCoreH.weightPerc(1) * MixinJRMCoreCliTicH.smod;
            boolean b2 = false;
            Label_0428:
            {
                if (!JRMCoreConfig.releaseStop || !JRMCoreKeyHandler.KiCharge.getIsKeyPressed()) {
                    if (!JRMCoreH.kob) {
                        b2 = false;
                        break Label_0428;
                    }
                }
                b2 = true;
            }
            boolean b = b2;
            final String[] d18 = JRMCoreH.data(18, "0;0;0;0;0;0;0;0;0").split(";");
            final String[] fuse = d18[2].split(",");
            if ((fuse.length == 3 && fuse[1].equalsIgnoreCase(this.mc.thePlayer.getCommandSenderName())) || JRMCoreH.kob) {
                b = true;
                final EntityPlayer pl = this.mc.theWorld.getPlayerEntityByName(fuse[0]);
                if (pl != null) {
                    if (this.mc.thePlayer.getDistanceToEntity(pl) > 5.0f) {
                        this.mc.thePlayer.setLocationAndAngles(pl.posX, pl.posY, pl.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch);
                    }
                    this.mc.gameSettings.thirdPersonView = 1;
                    this.mc.thePlayer.motionX = 0.0;
                    this.mc.thePlayer.motionY = 0.0;
                    this.mc.thePlayer.motionZ = 0.0;
                }
            }
            if (JRMCoreH.kob) {
                if (!this.viewChange) {
                    this.viewChange = true;
                    this.viewPrevious = this.mc.gameSettings.thirdPersonView;
                }
                this.mc.gameSettings.thirdPersonView = 1;
            } else if (this.viewChange) {
                this.viewChange = false;
                this.mc.gameSettings.thirdPersonView = this.viewPrevious;
            }
            if (JRMCoreH.WeightOn > 0.0f || b || MixinJRMCoreCliTicH.smod != 1.0f || JRMCoreH.pnh) {
                if (b) {
                    w = 0.0f;
                }
                if (JRMCoreH.pnh && plyr.dimension != 22) {
                    w *= 0.5f;
                }
                if (this.mc.thePlayer.movementInput instanceof MoveInputJRMC && this.mc.thePlayer.worldObj.getHeightValue((int) this.mc.thePlayer.posX, (int) this.mc.thePlayer.posZ) > 0 && this.mc.thePlayer.motionY > 0.0) {
                    final EntityClientPlayerMP thePlayer = this.mc.thePlayer;
                    thePlayer.motionY *= w;
                }
                if (!(this.mc.thePlayer.movementInput instanceof MoveInputJRMC) && (this.mc.thePlayer.movementInput.moveForward != 0.0f || this.mc.thePlayer.movementInput.moveStrafe != 0.0f)) {
                    this.mc.thePlayer.movementInput = new MoveInputJRMC(this.mc.gameSettings, w);
                }
                if (this.mc.thePlayer.movementInput instanceof MoveInputJRMC && ((MoveInputJRMC) this.mc.thePlayer.movementInput).moveModifier != w) {
                    ((MoveInputJRMC) this.mc.thePlayer.movementInput).moveModifier = w;
                }
            } else if (!(this.mc.thePlayer.movementInput instanceof MovementInputFromOptions)) {
                this.mc.thePlayer.movementInput = new MovementInputFromOptions(this.mc.gameSettings);
            }
            ++this.check;
            if (this.check == 1) {
                plyr.openGui(mod_JRMCore.instance, 30, plyr.worldObj, (int) plyr.posX, (int) plyr.posY, (int) plyr.posZ);
                this.check = 2;
            }
            if ((JRMCoreH.Pwrtyp == 1 || JRMCoreH.Pwrtyp == 2) && JRMCoreKeyHandler.Sagasys.getIsKeyPressed()) {
                plyr.openGui(mod_JRMCore.instance, 60, plyr.worldObj, (int) plyr.posX, (int) plyr.posY, (int) plyr.posZ);
            }
            if (IJRMCoreKeyHandler.getInfopanel().getIsKeyPressed()) {
                plyr.openGui(mod_JRMCore.instance, 30, plyr.worldObj, (int) plyr.posX, (int) plyr.posY, (int) plyr.posZ);
            }
            if (IJRMCoreKeyHandler.getDS().getIsKeyPressed()) {
                plyr.openGui(mod_JRMCore.instance, 0, plyr.worldObj, (int) plyr.posX, (int) plyr.posY, (int) plyr.posZ);
            }
            if (JRMCoreH.PlyrAttrbts[0] == 0 || this.wig) {
                this.wig = false;
                JRMCoreH.jrmct(1);
                JRMCoreH.jrmct(-1);
                JRMCoreH.jrmct(3);
            }
            if (!this.mc.isGamePaused()) {
                for (int i = 0; i < JRMCoreH.techniqueCooldowns.length; ++i) {
                    JRMCoreH.techniqueCooldowns[i] = ((JRMCoreH.techniqueCooldowns[i] > 0.0f) ? (JRMCoreH.techniqueCooldowns[i] - 0.05f) : 0.0f);
                }
                JRMCoreH.updateAllOldCooldownValues();
            }
            if (JRMCoreH.Accepted == 2) {
                JRMCoreH.Race = 0;
                JRMCoreH.dns = "0";
                JRMCoreH.Pwrtyp = 0;
                JRMCoreH.Class = 0;
                JRMCoreH.State = 0;
            }
            float f1 = 1.0f;
            float f2 = 1.0f;
            float f3 = 1.0f;
            final boolean noC = !JRMCoreH.DBC();
            if (JRMCoreH.dnsGender(JRMCoreH.dns) <= 1) {
                f1 = 0.73f + (noC ? 0.27f : 0.0f);
            }
            if (JRMCoreH.dnsGender(JRMCoreH.dns) >= 2) {
                f1 = 0.7f + (noC ? 0.27f : 0.0f);
            }
            if (JRMCoreH.JYC()) {
                MixinJRMCoreCliTicH.yc = JRMCoreHJYC.JYCsizeBasedOnAge(plyr);
            }
            if (JRMCoreH.DBC() && JRMCoreH.PlyrAttrbts[0] != 0 && JRMCoreConfig.sizes) {
                final float f1r = f1;
                JRMCoreHDBC.DBCreleaseZeroTurnOffTurbo();
                f1 += JRMCoreHDBC.DBCsizeBasedOnCns(JRMCoreH.PlyrAttrbts);
                f2 = JbraHelper.getCustomWidth(JRMCoreH.State, JRMCoreH.Race, JRMCoreH.StusEfctsMe());
                f3 = JbraHelper.getCustomHeight(JRMCoreH.State, JRMCoreH.Race, JRMCoreH.StusEfctsMe());
                byte rls = JRMCoreH.curRelease;
                if (JRMCoreH.rSai(JRMCoreH.Race) && (JRMCoreH.State == 7 || JRMCoreH.State == 8)) {
                    rls = 50;
                    f1 = f1r;
                }
                final float f3a = (f3 - 1.0f) * rls * 0.02f + 1.0f;
                f3 = ((f3a > f3) ? f3 : ((f3 > 1.0f) ? f3a : f3));
                final float f2a = (f2 - 1.0f) * rls * 0.02f + 1.0f;
                f2 = ((f2 > 1.0f) ? f2a : f2);
                final float f1a1 = (f1 - f1r) * ((rls <= 50) ? 0.25f : 0.5f);
                final float f1ac = f1a1 * rls * 0.02f;
                final float f1ao = f1 = f1 - f1r - f1a1 + f1ac + f1r;
            }
            if (JRMCoreH.dns.length() < 3) {
                f1 = 0.9375f;
                f2 = 1.0f;
                f3 = 1.0f;
                MixinJRMCoreCliTicH.yc = 1.0f;
            }
            if (!plyr.isPlayerSleeping() && JRMCoreH.JBRA()) {
                if (this.mc.gameSettings.keyBindJump.isPressed() && (this.mc.theWorld.getBlock((int) this.mc.thePlayer.posX, (int) this.mc.thePlayer.posY - 1, (int) this.mc.thePlayer.posZ - 1).getMaterial() == Material.water || this.mc.theWorld.getBlock((int) this.mc.thePlayer.posX, (int) this.mc.thePlayer.posY - 1, (int) this.mc.thePlayer.posZ - 1).getMaterial() == Material.lava)) {
                    final double d19 = 0.02;
                    final EntityClientPlayerMP thePlayer2 = this.mc.thePlayer;
                    thePlayer2.motionY += d19;
                }
                MixinJRMCoreCliTicH.clientHght = IJRMCoreComTickH.getHeight() * f1 * f3 * MixinJRMCoreCliTicH.yc;
                final float clientHght2 = MixinJRMCoreCliTicH.clientHght;
                final float clientWdth2 = IJRMCoreComTickH.getWidth() * f1 * f2 * f3 * MixinJRMCoreCliTicH.yc;
                IJRMCoreComTickH.invokeSS(plyr, clientWdth2, clientHght2);
                final float transBody = (0.5f - (MixinJRMCoreCliTicH.yc - 0.5f)) * 2.0f;
                float f4 = MixinJRMCoreCliTicH.yc;
                f4 = 3.0f - f4 * 2.0f;
                MixinJRMCoreCliTicH.offsetY = MixinJRMCoreCliTicH.clientHght * 0.9f - 1.54f;
            }
            final int r = 10;
            final AxisAlignedBB ab = AxisAlignedBB.getBoundingBox(plyr.posX - (double) r, plyr.posY - (double) r, plyr.posZ - (double) r, plyr.posX + (double) r, plyr.posY + (double) r, plyr.posZ + (double) r);
            final List list = this.mc.thePlayer.worldObj.getEntitiesWithinAABB(EntityPlayer.class, ab);
            for (Object o : list) {
                final EntityPlayer plyr2 = (EntityPlayer) o;
                if (!plyr.getCommandSenderName().equals(plyr2.getCommandSenderName())) {
                    final String[] s = JRMCoreH.data(plyr2.getCommandSenderName(), 1, "0;0;0;0;0;0").split(";");
                    final String[] s2 = JRMCoreH.data(plyr2.getCommandSenderName(), 2, "0;0").split(";");
                    final int state = Integer.parseInt(s2[0]);
                    final String[] s3 = JRMCoreH.data(plyr2.getCommandSenderName(), 14, "0,0,0,0,0,0").split(",");
                    int rls2 = Integer.parseInt(JRMCoreH.data(plyr2.getCommandSenderName(), 10, "0;0").split(";")[0]);
                    String playersStatusEff = JRMCoreH.StusEfctsClient(plyr2);
                    final int race = Integer.parseInt(s[0]);
                    final int[] PlyrAttrbts = new int[s3.length];
                    for (int i2 = 0; i2 < PlyrAttrbts.length; ++i2) {
                        PlyrAttrbts[i2] = Integer.parseInt(s3[i2]);
                    }
                    float f5 = 1.0f;
                    float f6 = 1.0f;
                    float f7 = 1.0f;
                    final boolean noC2 = !JRMCoreH.DBC();
                    if (JRMCoreH.dnsGender(s[1]) <= 1) {
                        f5 = 0.73f + (noC2 ? 0.27f : 0.0f);
                    }
                    if (JRMCoreH.dnsGender(s[1]) >= 2) {
                        f5 = 0.7f + (noC2 ? 0.27f : 0.0f);
                    }
                    float yc = 1.0f;
                    if (JRMCoreH.JYC()) {
                        yc = JRMCoreHJYC.JYCsizeBasedOnAge(plyr2);
                    }
                    if (JRMCoreH.DBC() && PlyrAttrbts[0] != 0 && JRMCoreConfig.sizes) {
                        final float f1r2 = f5;
                        f5 += JRMCoreHDBC.DBCsizeBasedOnCns(PlyrAttrbts);
                        f6 = JbraHelper.getCustomWidth(state, race, playersStatusEff);
                        f7 = JbraHelper.getCustomHeight(state, race, playersStatusEff);
                        if (JRMCoreH.rSai(race) && (state == 7 || state == 8)) {
                            rls2 = 50;
                            f5 = f1r2;
                        }
                        final float f3a2 = (f7 - 1.0f) * rls2 * 0.02f + 1.0f;
                        f7 = ((f3a2 > f7) ? f7 : ((f7 > 1.0f) ? f3a2 : f7));
                        final float f2a2 = (f6 - 1.0f) * rls2 * 0.02f + 1.0f;
                        f6 = ((f6 > 1.0f) ? f2a2 : f6);
                        final float f1a2 = (f5 - f1r2) * ((rls2 <= 50) ? 0.25f : 0.5f);
                        final float f1ac2 = f1a2 * rls2 * 0.02f;
                        final float f1ao2 = f5 = f5 - f1r2 - f1a2 + f1ac2 + f1r2;
                    }
                    if (s[1].length() < 3) {
                        f5 = 0.9375f;
                        f6 = 1.0f;
                        f7 = 1.0f;
                        yc = 1.0f;
                    }
                    if (!plyr2.isPlayerSleeping()) {
                        boolean DO = true;
                        if (JRMCoreH.JBRA()) {
                            final float clientHght4;
                            final float clientHght3 = clientHght4 = IJRMCoreComTickH.getHeight() * f5 * f7 * yc;
                            final float clientWdth3 = IJRMCoreComTickH.getWidth() * f5 * f6 * f7 * yc;
                            if (fuse.length == 3 && fuse[1].equalsIgnoreCase(plyr2.getCommandSenderName())) {
                                IJRMCoreComTickH.invokeSS(plyr2, 0.0f, 0.0f);
                                DO = false;
                            } else {
                                IJRMCoreComTickH.invokeSS(plyr2, clientWdth3, clientHght4);
                            }
                        }
                        if (DO && fuse.length == 3 && fuse[1].equalsIgnoreCase(plyr2.getCommandSenderName())) {
                            IJRMCoreComTickH.invokeSS(plyr2, 0.0f, 0.0f);
                        }
                    }
                }
            }
            final boolean fly = (JRMCoreH.StusEfctsMe(9) && !JRMCoreH.StusEfctsMe(4)) || (JRMCoreH.DBC() && JRMCoreHDBC.DBCKiTechFly());
            if ((((plyr.motionX < 0.0) ? (plyr.motionX * -1.0) : plyr.motionX) > 0.2 || (((plyr.motionZ < 0.0) ? (plyr.motionZ * -1.0) : plyr.motionZ) > 0.2 && JRMCoreH.inAir(plyr))) && fly) {
                if (this.stand) {
                    this.stand = false;
                    JRMCoreH.Anim(1);
                }
            } else if (!this.stand) {
                this.stand = true;
                JRMCoreH.Anim(2);
            }
            if (this.mc.thePlayer != null && this.mc.theWorld != null) {
                if (MixinJRMCoreCliTicH.fnPressed > 0 && (this.mc.currentScreen instanceof GuiInventory || this.mc.currentScreen instanceof GuiContainerCreative)) {
                    PD.sendToServer(new OpenGuiMessage(mod_JRMCore.GUI_CUSTOM_INV));
                }
                if (JRMCoreKeyHandler.Fn.getIsKeyPressed()) {
                    MixinJRMCoreCliTicH.fnPressed = 10;
                } else {
                    --MixinJRMCoreCliTicH.fnPressed;
                }
            }
        }
    }
}
