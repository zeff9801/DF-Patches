package com.mitchej123.hodgepodge.mixins.early.netty.nio;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.IOException;

/**
 * @Reason Lifts the cap on the size of packets that can be read so that bigger packets can be sent
 */

@Mixin(PacketBuffer.class)
public abstract class MixinPacketBuffer {

    @Shadow public abstract short readShort();

    @Shadow public abstract ByteBuf readBytes(byte[] p_readBytes_1_);

    @Shadow public abstract void writeVarIntToBuffer(int p_150787_1_);

    @Shadow public abstract ByteBuf writeBytes(byte[] p_writeBytes_1_);

    @Overwrite
    public NBTTagCompound readNBTTagCompoundFromBuffer() throws IOException {
        short short1 = this.readShort();

        if (short1 < 0) {
            return null;
        } else {
            byte[] abyte = new byte[short1];
            this.readBytes(abyte);
            return CompressedStreamTools.func_152457_a(abyte, new NBTSizeTracker(16777216L));
        }
    }

    @Overwrite
    public static int getVarIntSize(int p_150790_0_) {
        return (p_150790_0_ & -128) == 0 ? 1 : ((p_150790_0_ & -16384) == 0 ? 2 : ((p_150790_0_ & -16777216) == 0 ? 3 : ((p_150790_0_ & -268435456) == 0 ? 4 : 5)));
    }
}
