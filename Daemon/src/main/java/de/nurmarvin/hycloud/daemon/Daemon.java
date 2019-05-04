package de.nurmarvin.hycloud.daemon;

import org.slf4j.Logger;

public class Daemon {
    public static final String VERSION = "1.0-DEV";
    private static Daemon instance;
    private Logger logger;

    public static void main(String[] args) {
        new Daemon();
    }

    public Daemon() {
        instance = this;
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
}
