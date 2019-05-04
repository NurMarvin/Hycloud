package de.nurmarvin.hycloud.master.server;

import java.util.Set;

/**
 * Manages all connected Hytale subservers
 * @author NurMarvin
 * @see Server
 */
public interface ServerManager {
    /**
     * Add a new server to the server set
     * @param server the server to add
     */
    void addServer(Server server);

    /**
     * Gets a server by it's template name and it's ID
     * @param template the name of the template
     * @param id the id of the server
     * @return the server if found otherwise null
     */
    Server getServer(String template, int id);

    /**
     * Removes a server from the server set
     * @param server the server to remove
     */
    void removeServer(Server server);

    /**
     * A set of all connected Hytale subservers
     * @return the set of all connected subservers
     */
    Set<Server> servers();
}
