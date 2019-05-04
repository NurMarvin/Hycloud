package de.nurmarvin.hycloud.master;

import de.nurmarvin.hycloud.master.command.CommandManager;
import de.nurmarvin.hycloud.master.config.GeneralConfig;
import de.nurmarvin.hycloud.master.daemon.DaemonManager;
import de.nurmarvin.hycloud.master.daemon.impl.DefaultDaemonManager;
import de.nurmarvin.hycloud.master.networking.NetworkServer;
import de.nurmarvin.hycloud.master.networking.manager.PacketManager;
import de.nurmarvin.hycloud.master.networking.packet.packets.out.PacketOutMasterShutdown;
import de.nurmarvin.hycloud.master.server.ScreenManager;
import de.nurmarvin.hycloud.master.server.ServerManager;
import de.nurmarvin.hycloud.master.server.impl.DefaultScreenManager;
import de.nurmarvin.hycloud.master.server.impl.DefaultServerManager;
import de.nurmarvin.hycloud.master.template.TemplateManager;
import de.nurmarvin.hycloud.master.template.impl.DefaultJSONTemplateManager;
import jline.console.ConsoleReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Master {
    public static final String VERSION = "0.1-DEV";
    private static Master instance;
    private Logger logger;

    private ConsoleReader consoleReader;

    private ScreenManager screenManager;
    private CommandManager commandManager;
    private TemplateManager templateManager;
    private DaemonManager daemonManager;
    private ServerManager serverManager;
    private PacketManager packetManager;

    private NetworkServer networkServer;

    private GeneralConfig generalConfig;

    private boolean running;

    private Master() {
        instance = this;
        this.logger = LoggerFactory.getLogger(getClass());
        this.printLogo();
        this.setupConsole();

        long start = System.currentTimeMillis();

        this.generalConfig = new GeneralConfig(this);

        this.commandManager = new CommandManager(this);
        this.screenManager = new DefaultScreenManager();
        this.templateManager = new DefaultJSONTemplateManager(this);
        this.daemonManager = new DefaultDaemonManager(this);
        this.serverManager = new DefaultServerManager();
        this.packetManager = new PacketManager(this);

        this.networkServer = new NetworkServer(this);

        this.running = true;

        this.logger.info("Done! Master has been started in {}ms.", System.currentTimeMillis() - start);
        this.logger.info("For a list of commands run \"help\".");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if(running) shutdown();
        }));

        this.startConsole();
    }

    public static void main(String[] args) {
        new Master();
    }

    public static Master instance() {
        return instance;
    }

    private void printLogo() {
        logger.info("\n" +
                    "\n" +
                    "    __  __           __                __\n" +
                    "   / / / /_  _______/ /___  __  ______/ /\n" +
                    "  / /_/ / / / / ___/ / __ \\/ / / / __  / \n" +
                    " / __  / /_/ / /__/ / /_/ / /_/ / /_/ /  \n" +
                    "/_/ /_/\\__, /\\___/_/\\____/\\__,_/\\__,_/   \n" +
                    "      /____/                             \n" +
                    "\n" +
                    "Hycloud Master version {} by NurMarvin (Marvin Witt)" +
                    "\n", VERSION);
    }

    private void setupConsole() {
        try {
            consoleReader = new ConsoleReader();
        } catch (IOException ex) {
            this.logger.error("An error occurred during the setup of the  console reader", ex);
        }
    }

    private void startConsole() {
        Thread thread = new Thread(() -> {
            try {
                String line;
                while ((line = consoleReader.readLine("$ ")) != null && running) {
                    if (screenManager.activeScreen() != null) {
                        if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("leave")) {
                            screenManager.leave();
                        } else {
                            screenManager.write(line);
                        }
                    } else {
                        commandManager.process(line);
                    }
                    if (!running) System.exit(0);
                }
            } catch (IOException ex) {
                this.logger.error("An error occurred while handling console input", ex);
            }
        });
        thread.setName("Input");
        thread.start();
    }

    public void shutdown() {
        this.daemonManager.daemons().forEach(d -> d.sendPacket(new PacketOutMasterShutdown()));
        running = false;

        logger.info("Bye!");
    }

    public Logger logger() {
        return logger;
    }

    public TemplateManager templateManager() {
        return templateManager;
    }

    public ScreenManager screenManager() {
        return screenManager;
    }

    public CommandManager commandManager() {
        return commandManager;
    }

    public DaemonManager daemonManager() {
        return daemonManager;
    }

    public ServerManager serverManager() {
        return serverManager;
    }

    public PacketManager packetManager() {
        return packetManager;
    }

    public ConsoleReader consoleReader() {
        return consoleReader;
    }

    public GeneralConfig generalConfig() {
        return generalConfig;
    }

    public NetworkServer networkServer() {
        return networkServer;
    }
}
