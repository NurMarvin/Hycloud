package de.nurmarvin.hycloud.master.networking.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;

/**
 * Created by Yonathan on 20.05.2018 17:02
 */
public class PacketEncoder extends MessageToByteEncoder<PacketOut> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, PacketOut packetOut, ByteBuf output)
            throws IOException {
        output.writeInt(packetOut.getId());
        packetOut.write(output);
    }
}
