package de.nurmarvin.hycloud.master.command.commands;

import de.nurmarvin.hycloud.master.Master;
import de.nurmarvin.hycloud.master.command.Command;

import java.io.IOException;

/**
 * @author NurMarvin
 */
@Command.CloudCommand(name = "clear", description = "Clears the page", alias = "cls")
public class ClearCommand implements Command {
    @Override
    public void execute(String[] args) {
        try {
            Master.instance().consoleReader().clearScreen();
        } catch (IOException ex) {
            Master.instance().logger().error("An error occurred while clearing the screen", ex);
        }
    }
}
