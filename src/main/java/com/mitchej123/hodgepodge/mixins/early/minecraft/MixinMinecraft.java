package com.mitchej123.hodgepodge.mixins.early.minecraft;

import com.google.common.collect.Lists;
import com.mitchej123.hodgepodge.util.ResourceLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.settings.GameSettings;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Mixin(Minecraft.class)
public class MixinMinecraft {


    @Shadow
    @Final
    private static Logger logger;
    @Shadow public GameSettings gameSettings;
    @Shadow private List defaultResourcePacks;
    @Shadow private IReloadableResourceManager mcResourceManager;
    @Shadow private LanguageManager mcLanguageManager;
    @Shadow private ResourcePackRepository mcResourcePackRepository;
    @Shadow public RenderGlobal renderGlobal;

    /**
     * @Reason Injects our custom resource pack into the game, so we don't have to use resource packs anymore
     */
    @Overwrite
    public void refreshResources() {
        ArrayList arraylist = Lists.newArrayList(this.defaultResourcePacks);
        ResourceLoader.insertNormalPack(arraylist); //Our change
        Iterator iterator = this.mcResourcePackRepository.getRepositoryEntries().iterator();

        while (iterator.hasNext()) {
            ResourcePackRepository.Entry entry = (ResourcePackRepository.Entry) iterator.next();
            arraylist.add(entry.getResourcePack());
        }

        if (this.mcResourcePackRepository.func_148530_e() != null) {
            arraylist.add(this.mcResourcePackRepository.func_148530_e());
        }
        try {
            this.mcResourceManager.reloadResources(arraylist);
        } catch (RuntimeException runtimeexception) {
            logger.info("Caught error stitching, removing all assigned resourcepacks", runtimeexception);
            arraylist.clear();
            arraylist.addAll(this.defaultResourcePacks);
            this.mcResourcePackRepository.func_148527_a(Collections.emptyList());
            this.mcResourceManager.reloadResources(arraylist);
            this.gameSettings.resourcePacks.clear();
            this.gameSettings.saveOptions();
        }

        this.mcLanguageManager.parseLanguageMetadata(arraylist);

        if (this.renderGlobal != null) {
            this.renderGlobal.loadRenderers();
        }
    }

}
