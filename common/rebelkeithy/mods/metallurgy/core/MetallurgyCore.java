package rebelkeithy.mods.metallurgy.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import net.minecraftforge.event.EventBus;
import rebelkeithy.mods.keithyutils.guiregistry.GuiRegistry;
import rebelkeithy.mods.metallurgy.api.plugin.PluginPostInitEvent;
import rebelkeithy.mods.metallurgy.api.plugin.event.PluginInitEvent;
import rebelkeithy.mods.metallurgy.api.plugin.event.PluginPreInitEvent;
import rebelkeithy.mods.metallurgy.core.metalsets.MetalSet;
import rebelkeithy.mods.metallurgy.core.plugin.PluginLoader;
import rebelkeithy.mods.metallurgy.core.plugin.event.NativePluginInitEvent;
import rebelkeithy.mods.metallurgy.core.plugin.event.NativePluginPostInitEvent;
import rebelkeithy.mods.metallurgy.core.plugin.event.NativePluginPreInitEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "Metallurgy3", name = "Metallurgy 3", version = MetallurgyCore.MOD_VERSION, dependencies = "required-after:KeithyUtils@[1.2,]")
@NetworkMod(channels =
{ "MetallurgyCore" }, clientSideRequired = true, serverSideRequired = false)
public class MetallurgyCore
{
    public static final String MOD_VERSION = "3.2.3";
    
    @SidedProxy(clientSide = "rebelkeithy.mods.metallurgy.core.ClientProxy", serverSide = "rebelkeithy.mods.metallurgy.core.CommonProxy")
    public static CommonProxy proxy;

    @Instance(value = "Metallurgy3")
    public static MetallurgyCore instance;
    
    public static EventBus PLUGIN_BUS = new EventBus();

    public static boolean spawnInAir = false;

    public static boolean DEBUG = true;

    public static Configuration config;

    List<String> csvFiles;
    List<String> setsToRead;

    public static Logger log;

    private static List<MetalSet> metalSets;

    public static List<MetalSet> getMetalSetList()
    {
        if (metalSets == null)
        {
            metalSets = new ArrayList<MetalSet>();
        }

        return metalSets;
    }

    MetalSet baseSet;

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        log.fine("Posting init event to plugins.");
        PLUGIN_BUS.post(new NativePluginInitEvent());
        PLUGIN_BUS.post(new PluginInitEvent());

        for (final MetalSet set : getMetalSetList())
        {
            set.load();
            proxy.registerNamesForMetalSet(set);
        }
        MetalInfoDatabase.registerItemsWithOreDict();
    }

    public void initConfig()
    {
        final File fileDir = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3");
        fileDir.mkdir();
        final File cfgFile = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3/MetallurgyCore.cfg");

        try
        {
            cfgFile.createNewFile();
            log.info("[Metallurgy3] Successfully created/read configuration file for Metallurgy 3 Core");
        } catch (final IOException e)
        {
            log.warning("[Metallurgy3] Could not create configuration file for Metallurgy 3 Core, Reason:");
            e.printStackTrace();
        }

        config = new Configuration(cfgFile);
        config.load();

        spawnInAir = config.get("Cheats", "Spawn Ore In Air", false).getBoolean(false);

        csvFiles = Arrays.asList(config.get("Metal Sets", "File List", "").getString().split("\\s*,\\s*"));
        setsToRead = Arrays.asList(config.get("Metal Sets", "Metal Set List", "").getString().split("\\s*,\\s*"));
        log.info("reading sets " + setsToRead.size());

        if (config.hasChanged())
        {
            config.save();
        }
    }
    
    public static Boolean getConfigSettingBoolean(String category, String name, Boolean defaultValue)
    {
    	config.load();
    	
    	Property property = config.get(category, name, defaultValue);
    	
    	if(config.hasChanged())
    	{
    		config.save();
    	}
    	
    	return property.getBoolean(defaultValue);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        log.fine("Posting postInit event to plugins.");
        PLUGIN_BUS.post(new NativePluginPostInitEvent());
        PLUGIN_BUS.post(new PluginPostInitEvent());
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

        log = event.getModLog();

        initConfig();

        for (final String filename : csvFiles)
        {
            if (!filename.equals(""))
            {
                MetalInfoDatabase.readMetalDataFromFile(event.getModConfigurationDirectory() +"/Metallurgy3/" + filename);
            }
        }
        for (final String set : setsToRead)
        {
            if (!set.equals(""))
            {
                final CreativeTabs tab = new CreativeTabs(set);
                new MetalSet(set, MetalInfoDatabase.getSpreadsheetDataForSet(set), tab);
            }
        }

        NetworkRegistry.instance().registerGuiHandler(this, GuiRegistry.instance());
        
        log.fine("Loading plugins.");
        PluginLoader.loadPlugins(PLUGIN_BUS, event.getSourceFile(), new File(MetallurgyCore.proxy.getMinecraftDir() + "/mods"));
        
        log.fine("Posting preInit event to plugins.");
        PLUGIN_BUS.post(new NativePluginPreInitEvent(event, instance, MOD_VERSION));
        PLUGIN_BUS.post(new PluginPreInitEvent(event, MOD_VERSION));
    }
}
