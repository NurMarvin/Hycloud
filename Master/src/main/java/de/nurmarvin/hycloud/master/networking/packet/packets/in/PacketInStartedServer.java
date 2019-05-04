package de.nurmarvin.hycloud.master.networking.packet.packets.in;

import de.nurmarvin.hycloud.master.Master;
import de.nurmarvin.hycloud.master.daemon.Daemon;
import de.nurmarvin.hycloud.master.networking.StringUtil;
import de.nurmarvin.hycloud.master.networking.packet.PacketIn;
import de.nurmarvin.hycloud.master.server.Server;
import de.nurmarvin.hycloud.master.template.Template;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author NurMarvin
 */
public class PacketInStartedServer implements PacketIn {

    @Override
    public void read(ByteBuf byteBuf, ChannelHandlerContext ctx) {
        //Template, ID
        Template template = Master.instance().templateManager().getTemplateByName(StringUtil.getString(byteBuf));
        int id = byteBuf.readInt();
        Daemon daemon = Master.instance().daemonManager().getDaemonByChannel(ctx);
        Server server = new Server(template, id, daemon);
        Master.instance().serverManager().addServer(server);
    }
}
