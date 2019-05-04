package de.nurmarvin.hycloud.master.networking.packet.packets.in;

import de.nurmarvin.hycloud.master.Master;
import de.nurmarvin.hycloud.master.networking.StringUtil;
import de.nurmarvin.hycloud.master.networking.packet.PacketIn;
import de.nurmarvin.hycloud.master.server.Server;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author NurMarvin
 */
public class PacketInServerStopped implements PacketIn {

    @Override
    public void read(ByteBuf byteBuf, ChannelHandlerContext ctx) {
        //Template, ID
        String template = StringUtil.getString(byteBuf);
        int id = byteBuf.readInt();

        Server server = Master.instance().serverManager().getServer(template, id);
        if (Master.instance().screenManager().activeScreen().equals(server.screen())) {
            Master.instance().screenManager().leave();
            Master.instance().logger().info("You were removed from the screen, because the server went offline.");
        }

        Master.instance().serverManager().removeServer(server);
    }
}
