package rebelkeithy.mods.metallurgy.core.plugin.event;

import java.io.File;
import java.util.logging.Logger;

import rebelkeithy.mods.metallurgy.core.MetallurgyCore;

import net.minecraftforge.event.Event;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class NativePluginPreInitEvent extends Event
{
    private final File sourceFile;
    private final File configDir;
    private final String version;
    private final Logger logger;
    private MetallurgyCore instance;

    public NativePluginPreInitEvent(final FMLPreInitializationEvent event, final MetallurgyCore instance, final String version)
    {
        sourceFile = event.getSourceFile();
        configDir = new File(event.getModConfigurationDirectory(), "Metallurgy3");
        this.version = version;
        logger = event.getModLog();
        this.instance = instance;
    }

    public MetallurgyCore getMetallurgyInstance()
    {
        return instance;
    }
    
    public File getMetallurgyConfigDir()
    {
        return configDir;
    }

    public Logger getMetallurgyLog()
    {
        return logger;
    }

    public File getMetallurgySourceFile()
    {
        return sourceFile;
    }

    public String getMetallurgyVersion()
    {
        return version;
    }
}
