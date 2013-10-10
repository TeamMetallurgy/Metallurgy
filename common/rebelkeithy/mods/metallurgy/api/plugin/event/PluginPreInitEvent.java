package rebelkeithy.mods.metallurgy.api.plugin.event;

import java.io.File;

import net.minecraftforge.event.Event;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class PluginPreInitEvent extends Event
{
    private final File configDir;
    private final String version;

    public PluginPreInitEvent(final FMLPreInitializationEvent event, final String version)
    {
        configDir = new File(event.getModConfigurationDirectory(), "Metallurgy");
        this.version = version;
    }

    public File getMetallurgyPluginConfigDir()
    {
        return new File(configDir, "PlugIn");
    }

    public String getMetallurgyVersion()
    {
        return version;
    }
}
