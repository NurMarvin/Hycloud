package de.nurmarvin.hycloud.master.networking.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

/**
 * An incoming packet from an e.g. an {@link de.nurmarvin.hycloud.master.daemon.Daemon} or
 * {@link de.nurmarvin.hycloud.master.server.Server}
 *
 * @author NurMarvin
 * @since 1.0
 */
public interface PacketIn {
    /**
     * Called when the packet was received
     *
     * @param byteBuf The packet contents as a byte buffer
     * @param ctx     The channel handler context containing channel data and similar
     * @throws IOException
     */
    void read(ByteBuf byteBuf, ChannelHandlerContext ctx) throws IOException;
}
