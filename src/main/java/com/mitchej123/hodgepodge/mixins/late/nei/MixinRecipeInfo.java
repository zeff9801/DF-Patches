package com.mitchej123.hodgepodge.mixins.late.nei;

import codechicken.nei.api.API;
import codechicken.nei.recipe.*;
import me.aegous.dfclient.gui.custom.GPlayer;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.gui.inventory.GuiInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(RecipeInfo.class)
public class MixinRecipeInfo {
    @Overwrite(remap = false)
    public static void load() {
        API.registerRecipeHandler(new ShapedRecipeHandler());
        API.registerUsageHandler(new ShapedRecipeHandler());
        API.registerRecipeHandler(new ShapelessRecipeHandler());
        API.registerUsageHandler(new ShapelessRecipeHandler());
        API.registerRecipeHandler(new FireworkRecipeHandler());
        API.registerUsageHandler(new FireworkRecipeHandler());
        API.registerRecipeHandler(new FurnaceRecipeHandler());
        API.registerUsageHandler(new FurnaceRecipeHandler());
        API.registerRecipeHandler(new BrewingRecipeHandler());
        API.registerUsageHandler(new BrewingRecipeHandler());
        API.registerRecipeHandler(new FuelRecipeHandler());
        API.registerUsageHandler(new FuelRecipeHandler());
        API.registerGuiOverlay(GuiCrafting.class, "crafting");
        API.registerGuiOverlay(GuiInventory.class, "crafting2x2", 63, 20);
        API.registerGuiOverlay(GPlayer.class, "crafting2x2", 63, 20);
        API.registerGuiOverlay(GuiFurnace.class, "smelting");
        API.registerGuiOverlay(GuiFurnace.class, "fuel");
        API.registerGuiOverlay(GuiBrewingStand.class, "brewing");
        API.registerGuiOverlayHandler(GuiCrafting.class, new DefaultOverlayHandler(), "crafting");
        API.registerGuiOverlayHandler(GuiInventory.class, new DefaultOverlayHandler(63, 20), "crafting2x2");
        API.registerGuiOverlayHandler(GPlayer.class, new DefaultOverlayHandler(63, 20), "crafting2x2");
        API.registerGuiOverlayHandler(GuiBrewingStand.class, new BrewingOverlayHandler(), "brewing");
        API.registerRecipeHandler(new ProfilerRecipeHandler(true));
        API.registerUsageHandler(new ProfilerRecipeHandler(false));
    }
}
