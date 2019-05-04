package de.nurmarvin.hycloud.master.daemon;

import io.netty.channel.ChannelHandlerContext;

import java.util.Set;

/**
 * Manages all the connected daemons
 * @author NurMarvin
 */
public interface DaemonManager {
    /**
     * Adds a daemon to the daemon set
     * @param daemon the daemon to add
     */
    void addDaemon(Daemon daemon);

    /**
     * Removes a daemon from the daemon set
     * @param daemon the daemon to remove
     */
    void removeDaemon(Daemon daemon);

    /**
     * Gets the daemon that is currently used the least
     * @return the daemon if the daemon set is not empty otherwise null. If no optimal daemon is found, a random
     * daemon will be returned
     */
    Daemon getBestDaemon();

    /**
     * Gets a daemon by the provided name
     * @param name the name of the daemon
     * @return the daemon with the provided name if found otherwise null
     */
    Daemon getDaemonByName(String name);

    /**
     * Gets a daemon by the channel it uses to communicate with the master
     * @param ctx the channel of the daemon
     * @return the daemon that uses the provided channel if found otherwise null
     */
    Daemon getDaemonByChannel(ChannelHandlerContext ctx);

    /**
     * Syncs all templates from the {@link de.nurmarvin.hycloud.master.template.TemplateManager} with the provided
     * daemon
     * @param daemon the daemon to sync templates with
     */
    void syncTemplates(Daemon daemon);

    /**
     * A set of all connected daemons
     * @return the set of all connected daemons
     */
    Set<Daemon> daemons();
}
