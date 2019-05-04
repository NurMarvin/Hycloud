package de.nurmarvin.hycloud.master.daemon;

import de.nurmarvin.hycloud.master.networking.packet.PacketOut;
import io.netty.channel.ChannelHandlerContext;

public class Daemon {
    private String name;
    private int availableRam;
    private boolean ready;
    private int usedRam;
    private ChannelHandlerContext ctx;

    //TODO: Array with active Servers

    public Daemon(String name, ChannelHandlerContext ctx) {
        this.name = name;
        this.ctx = ctx;
    }

    public void sendPacket(PacketOut packetOut) {
        ctx.channel().writeAndFlush(packetOut);
    }

    public String name() {
        return name;
    }

    public Daemon availableRam(int availableRam) {
        this.availableRam = availableRam;
        return this;
    }

    public int availableRam() {
        return availableRam;
    }

    public boolean ready() {
        return ready;
    }

    public int usedRam() {
        return usedRam;
    }

    public Daemon usedRam(int usedRam) {
        this.usedRam = usedRam;
        return this;
    }

    public ChannelHandlerContext ctx() {
        return ctx;
    }

    @Override
    public String toString() {
        return "Daemon(name=" + this.name + ", ready=" + ready + ", availableRam=" + availableRam
               + ", usedRam=" + usedRam + ")";
    }
}
