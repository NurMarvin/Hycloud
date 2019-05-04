package de.nurmarvin.hycloud.master.networking.handler;

import de.nurmarvin.hycloud.master.Master;
import de.nurmarvin.hycloud.master.daemon.Daemon;
import de.nurmarvin.hycloud.master.networking.packet.PacketIn;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author NurMarvin
 */
public class NetworkHandler extends SimpleChannelInboundHandler<PacketIn> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PacketIn packetIn) {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Daemon daemon = Master.instance().daemonManager().getDaemonByChannel(ctx);
        if (daemon != null)
            Master.instance().daemonManager().removeDaemon(daemon);
    }
}
