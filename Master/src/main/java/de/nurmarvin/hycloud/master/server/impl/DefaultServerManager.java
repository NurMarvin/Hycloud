package de.nurmarvin.hycloud.master.server.impl;

import de.nurmarvin.hycloud.master.Master;
import de.nurmarvin.hycloud.master.server.Server;
import de.nurmarvin.hycloud.master.server.ServerManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultServerManager implements ServerManager {

    private final Set<Server> servers = new HashSet<>();

    @Override
    public void addServer(Server server) {
        Master.instance().logger().info("Server {} successfully connected!", server);
        servers.add(server);
        Master.instance().screenManager().addScreenToServer(server);
    }

    @Override
    public Server getServer(String template, int id) {
        for (Server server : servers)
            if (server.template().name().equalsIgnoreCase(template) && server.id() == id)
                return server;
        return null;
    }

    @Override
    public void removeServer(Server server) {
        server.template().remove(server.id());
        Master.instance().logger().info("Server {} successfully disconnected!", server);
        Master.instance().screenManager().screens().remove(server.screen());
        servers.remove(server);
    }

    @Override
    public Set<Server> servers() {
        return servers;
    }
}
