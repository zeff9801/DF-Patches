package com.mitchej123.hodgepodge.mixins.late.jrmcore;

import JinRyuu.JRMCore.JRMCoreClient;
import JinRyuu.JRMCore.JRMCoreKeyHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.lang.reflect.Field;

@Mixin(JRMCoreClient.class)
public class MixinJRMCoreClient {
    @Overwrite(remap = false)
    public void registerKeys() {
        JRMCoreKeyHandler obj = new JRMCoreKeyHandler();
        try {
            Field actionMenuReflected = obj.getClass().getDeclaredField("actionMenu");
            actionMenuReflected.setAccessible(true);
            ClientRegistry.registerKeyBinding((KeyBinding) actionMenuReflected.get(obj));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        ClientRegistry.registerKeyBinding(JRMCoreKeyHandler.KiDash);
        ClientRegistry.registerKeyBinding(JRMCoreKeyHandler.KiFlight);
    }
}
