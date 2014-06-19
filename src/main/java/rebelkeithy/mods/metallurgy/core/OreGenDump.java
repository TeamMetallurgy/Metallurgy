package rebelkeithy.mods.metallurgy.core;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import rebelkeithy.mods.metallurgy.core.metalsets.WorldGenMetals;

public class OreGenDump extends CommandBase
{

    @Override
    public String getCommandName()
    {
        return "dumpOreGen";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "commands.dumpOreGen.usage";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        if (astring.length == 1)
        {

            if (astring[0].matches("\\d"))
            {
                WorldGenMetals.dumpRegistry(Integer.parseInt(astring[0]), MetallurgyCore.modConfigurationDirectory.getPath());
                return;
            }
        }

        throw new WrongUsageException("commands.dumpOreGen.usage", new Object[0]);
    }

	@Override
	public int compareTo(Object arg0) {
		return this.compareTo((ICommand)arg0);
	}
}
