package de.nurmarvin.hycloud.master.networking.packet.packets.in;

import de.nurmarvin.hycloud.master.Master;
import de.nurmarvin.hycloud.master.networking.StringUtil;
import de.nurmarvin.hycloud.master.networking.packet.PacketIn;
import de.nurmarvin.hycloud.master.server.Server;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * The packet sent by a {@link Server} when trying to register in the Master
 * @author NurMarvin
 */
public class PacketInRegisterServer implements PacketIn {

    @Override
    public void read(ByteBuf byteBuf, ChannelHandlerContext ctx) {
        //Key, Template, Id
        String key = StringUtil.getString(byteBuf);
        String template = StringUtil.getString(byteBuf);
        int id = byteBuf.readInt();

        if (key.equals(Master.instance().generalConfig().keyToConnect())) {
            Server server = Master.instance().serverManager().getServer(template, id);
            server.ctx(ctx);
            Master.instance().logger().info("Server(template = " + template + ", id = " + id + ") connected!");
        } else {
            Master.instance().logger().debug("Disconnecting server with wrong key Server(template={}, id={}, key={})",
                                             template, id, key);
            ctx.disconnect();
        }
    }
}
