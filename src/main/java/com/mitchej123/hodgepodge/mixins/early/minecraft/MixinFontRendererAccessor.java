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
    void setColorCode(int[] colorCode);

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
    void setUnicodeFlag(boolean unicodeFlag);

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
    void setRandomStyle(boolean randomStyle);

    @Accessor
    boolean getBoldStyle();

    @Accessor
    void setBoldStyle(boolean boldStyle);

    @Accessor
    boolean getItalicStyle();

    @Accessor
    void setItalicStyle(boolean italicStyle);

    @Accessor
    boolean getUnderlineStyle();

    @Accessor
    void setUnderlineStyle(boolean underlineStyle);

    @Accessor
    boolean getStrikethroughStyle();

    @Accessor
    void setStrikethroughStyle(boolean strikethroughStyle);

}
