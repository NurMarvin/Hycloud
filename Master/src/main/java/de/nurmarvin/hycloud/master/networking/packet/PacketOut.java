package de.nurmarvin.hycloud.master.networking.packet;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

public interface PacketOut {
    void write(ByteBuf byteBuf) throws IOException;

    int getId();
}
