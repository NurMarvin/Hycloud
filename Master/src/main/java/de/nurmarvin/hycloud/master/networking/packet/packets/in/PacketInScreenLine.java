package de.nurmarvin.hycloud.master.networking.packet.packets.in;

import de.nurmarvin.hycloud.master.Master;
import de.nurmarvin.hycloud.master.networking.StringUtil;
import de.nurmarvin.hycloud.master.networking.packet.PacketIn;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author NurMarvin
 */
public class PacketInScreenLine implements PacketIn {

    @Override
    public void read(ByteBuf byteBuf, ChannelHandlerContext ctx) {
        //ServerName, Line
        Master.instance().screenManager().getScreenByServerName(StringUtil.getString(byteBuf))
              .addConsoleLine(StringUtil.getString(byteBuf));
    }
}
