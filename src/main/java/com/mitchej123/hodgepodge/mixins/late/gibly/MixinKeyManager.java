package com.mitchej123.hodgepodge.mixins.late.gibly;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.gliby.voicechat.client.keybindings.KeyEvent;
import net.gliby.voicechat.client.keybindings.KeyManager;
import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;

@Mixin(KeyManager.class)

public class MixinKeyManager {
    @SideOnly(Side.CLIENT)
    @Shadow(remap = false) final List<KeyEvent> keyEvents = new ArrayList<KeyEvent>();

    @Shadow(remap = false) protected boolean[] keyDown;
    @Overwrite(remap = false)
    private KeyBinding[] registerKeyBindings() {
        keyEvents.get(0).keyBind.name = "Speak";
        keyEvents.get(1).keyBind.name = "Voice-Chat Options";
        final KeyBinding keyBinding[] = new KeyBinding[keyEvents.size()];
        for (int i = 0; i < keyBinding.length; i++) {
            final KeyEvent keyEvent = this.keyEvents.get(i);
            keyBinding[i] = new KeyBinding(keyEvent.keyBind.name, keyEvent.keyID, "key.dfclient.category");
            this.keyDown = new boolean[keyBinding.length];
            keyEvent.forgeKeyBinding = keyBinding[i];
            ClientRegistry.registerKeyBinding(keyBinding[i]);
        }
        return keyBinding;
    }
}
