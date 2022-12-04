package com.mitchej123.hodgepodge.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.filefilter.DirectoryFileFilter;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResourceLoader implements IResourcePack
{
    @Override
    public InputStream getInputStream(ResourceLocation rl) throws IOException
    {
            File file = new File(new File(Minecraft.getMinecraft().mcDataDir, "resourcepacks/" + rl.getResourceDomain()), rl.getResourcePath());
            return new FileInputStream(file);
    }

    @Override
    public boolean resourceExists(ResourceLocation rl)
    {
        File fileRequested = new File(new File(Minecraft.getMinecraft().mcDataDir, "resourcepacks/" + rl.getResourceDomain()), rl.getResourcePath());
        return fileRequested.isFile();
    }

    @Override
    public Set getResourceDomains()
    {
        File folder = new File(Minecraft.getMinecraft().mcDataDir, "resourcepacks");
        HashSet<String> folders = new HashSet<String>();

        File[] resourceDomains = folder.listFiles((FileFilter) DirectoryFileFilter.DIRECTORY);

        for (File resourceFolder : resourceDomains)
        {
            folders.add(resourceFolder.getName());
        }

        return folders;
    }

    @Override
    public IMetadataSection getPackMetadata(IMetadataSerializer p_135058_1_, String p_135058_2_) throws IOException {
        return null;
    }

    @Override
    public BufferedImage getPackImage() throws IOException
    {
        return null;
    }

    @Override
    public String getPackName()
    {
        return "CustomResources";
    }


    public static void insertNormalPack(List resourceList)
    {

        resourceList.add(new ResourceLoader());
    }
}
