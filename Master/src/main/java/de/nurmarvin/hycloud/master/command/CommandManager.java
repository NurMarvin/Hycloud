package de.nurmarvin.hycloud.master.command;

import de.nurmarvin.hycloud.master.Master;
import de.nurmarvin.hycloud.master.command.commands.ClearCommand;
import de.nurmarvin.hycloud.master.command.commands.ExitCommand;
import de.nurmarvin.hycloud.master.command.commands.HelpCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Probably change this into a better command system because I don't like this this.
 * Also make this an interface and make a DefaultCommandManager implementation ~NurMarvin
 * @author NurMarvin
 * @see Command
 */
public class CommandManager {

    private final List<Command> commands = new ArrayList<>();

    public CommandManager(Master master) {
        this.commands.add(new HelpCommand());
        this.commands.add(new ClearCommand());
        this.commands.add(new ExitCommand());
    }

    public void process(String cmd) {
        for (Command command : commands) {
            if (command.getClass().isAnnotationPresent(Command.CloudCommand.class)) {
                Command.CloudCommand info = command.getClass().getAnnotation(Command.CloudCommand.class);

                String[] rawArgs = cmd.split(" ");
                List<String> triggers = new ArrayList<>();
                triggers.add(info.name());

                if(!info.alias()[0].equals(""))
                    triggers.addAll(Arrays.asList(info.alias()));
                for (String str : triggers) {
                    if (str.equalsIgnoreCase(rawArgs[0])) {
                        String[] args = new String[rawArgs.length - 1];
                        System.arraycopy(rawArgs, 1, args, 0, rawArgs.length - 1);
                        command.execute(args);
                        return;
                    }
                }
            }
        }
        Master.instance().logger().error("Unknown command. Type \"help\" for help.");
    }

    public List<Command> commands() {
        return commands;
    }
}
