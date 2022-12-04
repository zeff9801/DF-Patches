package com.mitchej123.hodgepodge.mixins.late.jrmcore;

import JinRyuu.JRMCore.JRMCoreGuiBars;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(JRMCoreGuiBars.class)
public class MixinJRMCoreGuiBars {

    @Overwrite(remap = false)
    public void renderKiBar() {}

    @Overwrite(remap = false)
    public void renderSAOHealthBar() {}

    @Overwrite(remap = false)
    public void renderBodyBar() {}

    @Overwrite(remap = false)
    public void renderRageBar() {}

    @Overwrite(remap = false)
    public void renderEnChrgBar() {}

    @Overwrite(remap = false)
    public void renderEnSideBar() {}

    @Overwrite(remap = false)
    public void renderEnSideBarNC() {}

    @Overwrite(remap = false)
    public void renderTrainGui() {}

}
