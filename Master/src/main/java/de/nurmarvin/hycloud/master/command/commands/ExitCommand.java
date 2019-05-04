package de.nurmarvin.hycloud.master.command.commands;

import de.nurmarvin.hycloud.master.Master;
import de.nurmarvin.hycloud.master.command.Command;

/**
 * @author NurMarvin
 */
@Command.CloudCommand(name = "exit", description = "Stops the ever.", alias = "end")
public class ExitCommand implements Command
{
    @Override
    public void execute(String[] args)
    {
        Master.instance().shutdown();
    }
}
