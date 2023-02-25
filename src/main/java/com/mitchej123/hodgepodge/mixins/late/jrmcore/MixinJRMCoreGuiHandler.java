package com.mitchej123.hodgepodge.mixins.late.jrmcore;

import JinRyuu.JRMCore.JRMCoreGuiHandler;
import JinRyuu.JRMCore.JRMCoreGuiSCM;
import JinRyuu.JRMCore.JRMCoreGuiScreen;
import JinRyuu.JRMCore.i.ExtendedPlayer;
import JinRyuu.JRMCore.i.GuiCustomPlayerInventory;
import JinRyuu.JRMCore.mod_JRMCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(JRMCoreGuiHandler.class)
public class MixinJRMCoreGuiHandler {
    /**
     * @author 1ost_
     * @reason Fix dbc being so retarded and opening GUI's even without keybinds being registered
     */
    @Overwrite(remap = false)
    public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
        if (guiId == 8) {
            return new JRMCoreGuiScreen(8);
        }
        return null;
    }
}
