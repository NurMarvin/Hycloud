package de.nurmarvin.hycloud.master.server.impl;

import de.nurmarvin.hycloud.master.Master;
import de.nurmarvin.hycloud.master.networking.packet.packets.out.PacketOutScreenWrite;
import de.nurmarvin.hycloud.master.server.Screen;
import de.nurmarvin.hycloud.master.server.ScreenManager;
import de.nurmarvin.hycloud.master.server.Server;

import java.util.HashSet;
import java.util.Set;

@Deprecated
public class DefaultScreenManager implements ScreenManager {
    private final Set<Screen> screens = new HashSet<>();

    private Screen activeScreen = null;

    @Override
    public void addScreenToServer(Server server) {
        Screen screen = new Screen(server);
        server.screen(screen);
        screens.add(screen);
    }

    @Override
    public Screen getScreenByServerName(String servername) {
        for (Screen screen : screens)
            if (screen.server().name().equalsIgnoreCase(servername))
                return screen;
        return null;
    }

    @Override
    public void login(String name) {
        Screen toLogin = getScreenByServerName(name);
        if (toLogin != null) {
            if (activeScreen != null) {
                Master.instance().logger().info("Logging out of screen {}", activeScreen);
                activeScreen.userInScreen(false);
            }
            activeScreen = toLogin;
            activeScreen.login();
        } else {
            Master.instance().logger().error("Can't find a screen for input \"{}\"!", name);
        }
    }

    @Override
    public void leave() {
        if (activeScreen == null) {
            Master.instance().logger().error("You're not in a screen!");
            return;
        }
        activeScreen.userInScreen(false);
        activeScreen = null;
        Master.instance().logger().info("Successfully exited screen!");
    }

    @Override
    public void write(String line) {
        if (activeScreen == null) {
            Master.instance().logger().error("You're not in a screen!");
            return;
        }
        activeScreen.server().daemon().sendPacket(new PacketOutScreenWrite(activeScreen.server(), line));
    }

    @Override
    public Screen activeScreen() {
        return activeScreen;
    }

    @Override
    public Set<Screen> screens() {
        return screens;
    }
}
