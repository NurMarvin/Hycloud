package de.nurmarvin.hycloud.master.server;

import de.nurmarvin.hycloud.master.Master;

import java.util.concurrent.CopyOnWriteArrayList;

public class Screen {

    private final CopyOnWriteArrayList<String> console = new CopyOnWriteArrayList<>();
    private boolean userInScreen = false;
    private Server server;

    public Screen(Server server) {
        this.server = server;
    }

    public void login() {
        userInScreen = true;
        printConsole();
    }

    public void addConsoleLine(String consoleLine) {
        console.add(consoleLine);
        if (userInScreen)
            Master.instance().logger().info("[{}] " + consoleLine, server.name());
        if (console.size() > 50)
            console.remove(0);
    }

    private void printConsole() {
        for (String consoleLine : console)
            Master.instance().logger().info("[{}] " + consoleLine, this.server.name());
    }

    public boolean userInScreen() {
        return userInScreen;
    }

    public Screen userInScreen(boolean userInScreen) {
        this.userInScreen = userInScreen;
        return this;
    }

    public Server server() {
        return server;
    }

    @Override
    public String toString() {
        return "Screen(server=" + this.server.name() + ")";
    }
}
