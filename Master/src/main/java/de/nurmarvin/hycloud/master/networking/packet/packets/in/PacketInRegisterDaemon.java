package de.nurmarvin.hycloud.master.networking.packet.packets.in;

import de.nurmarvin.hycloud.master.Master;
import de.nurmarvin.hycloud.master.daemon.Daemon;
import de.nurmarvin.hycloud.master.networking.StringUtil;
import de.nurmarvin.hycloud.master.networking.packet.PacketIn;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * The packet sent by a {@link Daemon} when trying to register in the Master
 * @author NurMarvin
 */
public class PacketInRegisterDaemon implements PacketIn {

    @Override
    public void read(ByteBuf byteBuf, ChannelHandlerContext ctx) {
        //Key
        String key = StringUtil.getString(byteBuf);
        //Name
        String name = StringUtil.getString(byteBuf);
        //MaxRam
        int maxRam = byteBuf.readInt();

        if (key.equals(Master.instance().generalConfig().keyToConnect())) {
            Daemon daemon = new Daemon(name, ctx);
            daemon.availableRam(maxRam);
            Master.instance().daemonManager().addDaemon(daemon);
        } else {
            Master.instance().logger().debug("Disconnecting daemon with wrong key Daemon(name={}, key={})", name, key);
            ctx.disconnect();
        }
    }
}
