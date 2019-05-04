package de.nurmarvin.hycloud.master.daemon.impl;

import de.nurmarvin.hycloud.master.Master;
import de.nurmarvin.hycloud.master.daemon.Daemon;
import de.nurmarvin.hycloud.master.daemon.DaemonManager;
import de.nurmarvin.hycloud.master.networking.packet.packets.out.PacketOutSyncTemplate;
import de.nurmarvin.hycloud.master.template.Template;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashSet;
import java.util.Set;

/**
 * @author NurMarvin
 */
public class DefaultDaemonManager implements DaemonManager {
    private final Set<Daemon> daemons = new HashSet<>();
    private Master master;

    public DefaultDaemonManager(Master master) {
        this.master = master;
    }

    public void addDaemon(Daemon daemon) {
        daemons.add(daemon);
        master.logger().info("Daemon {} successfully connected!", daemon);
        syncTemplates(daemon);
    }

    public void removeDaemon(Daemon daemon) {
        daemons.remove(daemon);
        master.logger().info("Daemon {} successfully disconnected!", daemon);
    }

    public Daemon getBestDaemon() {
        //TODO: Implement
        return daemons.stream().findFirst().orElse(null);
    }

    public Daemon getDaemonByName(String name) {
        for (Daemon daemon : daemons)
            if (daemon.name().equalsIgnoreCase(name))
                return daemon;
        return null;
    }

    public Daemon getDaemonByChannel(ChannelHandlerContext ctx) {
        for (Daemon daemon : daemons)
            if (daemon.ctx().equals(ctx) || daemon.ctx().channel().equals(ctx.channel()))
                return daemon;
        return null;
    }

    public void syncTemplates(Daemon daemon) {
        master.logger().info("Syncing templates with daemon {}", daemon);
        for (Template template : master.templateManager().templates())
            daemon.sendPacket(new PacketOutSyncTemplate(template));
    }

    public Set<Daemon> daemons() {
        return daemons;
    }
}
