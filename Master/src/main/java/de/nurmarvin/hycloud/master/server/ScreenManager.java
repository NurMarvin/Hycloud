package de.nurmarvin.hycloud.master.server;

import java.util.Set;

/**
 * Manages all screens for the subservers
 * Will probably be replaced with https://www.gnu.org/software/screen/
 * @author NurMarvin
 * @see Screen
 * @see Server
 */
@Deprecated
public interface ScreenManager {
    /**
     * Adds a new screen instance to a server
     * @param server the server that should receive a new screen instance
     */
    void addScreenToServer(Server server);

    /**
     * Gets a screen by the server name
     * @param name the name of the server
     * @return the screen of the server with the provided name if found otherwise null
     */
    Screen getScreenByServerName(String name);

    /**
     * Changes the current screen to the screen of the provided name
     * @param name the name of the screen
     */
    void login(String name);

    /**
     * Leaves the current screen
     */
    void leave();

    /**
     * Writes a line into the console of the server associated with the current screen
     * @param line the line to write
     */
    void write(String line);

    /**
     * The currently active screen that receives commands from {@link #write(String)}
     * @return the currently active screen
     */
    Screen activeScreen();

    /**
     * A set of all screens
     * @return the set of all screens
     */
    Set<Screen> screens();
}
