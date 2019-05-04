package de.nurmarvin.hycloud.master.networking.packet;

import de.nurmarvin.hycloud.master.Master;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Yonathan on 20.05.2018 17:02
 */
public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> output) throws Exception {
        int id = byteBuf.readInt();
        Class<? extends PacketIn> pClass = Master.instance().packetManager().getPacketById(id);

        if (pClass == null) {
            Master.instance().logger().error("Could not get Packet by ID: " + id);
            return;
        }

        PacketIn packetIn = pClass.newInstance();
        packetIn.read(byteBuf, ctx);
        output.add(packetIn);

    }
}
