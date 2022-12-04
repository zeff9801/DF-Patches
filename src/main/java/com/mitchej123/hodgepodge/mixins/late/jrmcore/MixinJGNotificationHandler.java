package com.mitchej123.hodgepodge.mixins.late.jrmcore;

import JinRyuu.JRMCore.client.notification.JGNotification;
import JinRyuu.JRMCore.client.notification.JGNotificationHandlerC;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(JGNotificationHandlerC.class)
public class MixinJGNotificationHandler {
    /**
     * @Reason Disable the newly added notifications, not even a way to disable them via config!
     * @param notification
     */
    @Overwrite(remap = false)
    public static void addNotification(final JGNotification notification) {
        //Nothing
    }
}
