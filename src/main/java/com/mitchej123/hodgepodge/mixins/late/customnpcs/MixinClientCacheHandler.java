package com.mitchej123.hodgepodge.mixins.late.customnpcs;

import net.minecraft.util.ResourceLocation;
import noppes.npcs.client.ClientCacheHandler;
import noppes.npcs.client.renderer.ImageData;
import noppes.npcs.util.CacheHashMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientCacheHandler.class)
public class MixinClientCacheHandler {

    @Shadow @Final private static CacheHashMap<String, CacheHashMap.CachedObject<ImageData>> imageDataCache;

    /**
     * @author
     * @reason
     */
    @Overwrite
    public static ImageData getNPCTexture(String directory, boolean x64, ResourceLocation resource) {
        String newDirectory = directory;
        if (newDirectory.contains("http://novask.in/") || newDirectory.contains("https://novask.in/")) {
            String[] fileNameParts = newDirectory.split("/");
            String fileName = fileNameParts[fileNameParts.length - 1];
            newDirectory = "https://minecraft.novaskin.me/skin/" + fileName;
        }

        synchronized (imageDataCache) {
            if (!imageDataCache.containsKey(resource.getResourcePath())) {
                imageDataCache.put(resource.getResourcePath(), new CacheHashMap.CachedObject(new ImageData(newDirectory, x64, resource)));
            }

            return (ImageData) imageDataCache.get(resource.getResourcePath()).getObject();
        }
    }
}
