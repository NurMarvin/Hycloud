package de.nurmarvin.hycloud.master.command.commands;

import de.nurmarvin.hycloud.master.Master;
import de.nurmarvin.hycloud.master.command.Command;

/**
 * @author NurMarvin
 */
@Command.CloudCommand(name = "help", description = "Shows this help page")
public class HelpCommand implements Command {
    @Override
    public void execute(String[] args) {
        Master.instance().logger().info("Hycloud Command List:");
        for (Command command : Master.instance().commandManager().commands()) {
            if (command.getClass().isAnnotationPresent(Command.CloudCommand.class)) {
                Command.CloudCommand info = command.getClass().getAnnotation(Command.CloudCommand.class);
                Master.instance().logger().info(info.name() + "  |  " + info.description());
            }
        }
    }
}
