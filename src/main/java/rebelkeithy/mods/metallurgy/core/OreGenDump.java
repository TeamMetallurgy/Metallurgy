package rebelkeithy.mods.metallurgy.core;

import rebelkeithy.mods.metallurgy.core.metalsets.WorldGenMetals;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

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
            int i;

            if (astring[0].matches("\\d"))
            {
                WorldGenMetals.dumpRegistry(Integer.parseInt(astring[0]), MetallurgyCore.modConfigurationDirectory.getPath());
                return;
            }
        }

        throw new WrongUsageException("commands.dumpOreGen.usage", new Object[0]);
    }
}
