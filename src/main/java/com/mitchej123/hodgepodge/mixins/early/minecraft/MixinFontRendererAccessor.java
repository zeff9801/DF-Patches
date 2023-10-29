package com.mitchej123.hodgepodge.mixins.early.minecraft;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Random;

@Mixin(FontRenderer.class)
public interface MixinFontRendererAccessor {

    @Invoker("getFormatFromString")
    static String callGetFormatFromString(String s) {
        throw new IllegalStateException("Mixin stub invoked");
    }

    @Accessor
    static ResourceLocation[] getUnicodePageLocations() {throw new IllegalStateException("Mixin stub invoked");}

    @Accessor
    int[] getCharWidth();

    @Accessor
    byte[] getGlyphWidth();

    @Accessor
    int[] getColorCode();

    @Accessor
    ResourceLocation getLocationFontTexture();

    @Accessor
    TextureManager getRenderEngine();

    @Accessor
    float getPosX();

    @Accessor
    float getPosY();

    @Accessor
    boolean getUnicodeFlag();

    @Accessor
    boolean getBidiFlag();

    @Accessor
    float getRed();

    @Accessor
    float getBlue();

    @Accessor
    float getGreen();

    @Accessor
    float getAlpha();

    @Accessor
    int getTextColor();

    @Accessor
    boolean getRandomStyle();

    @Accessor
    boolean getBoldStyle();

    @Accessor
    boolean getItalicStyle();

    @Accessor
    boolean getUnderlineStyle();

    @Accessor
    boolean getStrikethroughStyle();

}
