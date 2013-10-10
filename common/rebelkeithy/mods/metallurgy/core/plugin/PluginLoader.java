package rebelkeithy.mods.metallurgy.core.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.minecraftforge.event.EventBus;
import rebelkeithy.mods.metallurgy.api.plugin.IPlugin;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;

import com.google.common.collect.Lists;

public class PluginLoader
{
    private static String getPackageFromFilename(final String filename)
    {
        String packageName = "";
        final List<String> folders = Arrays.asList(filename.split("\\\\"));

        for (int i = 0; i < folders.size() && folders.get(i) != null; i++)
            if (folders.get(i).equals("bin"))
            {
                for (int j = i + 1; j < folders.size(); j++)
                {
                    if (!packageName.isEmpty()) packageName += ".";
                    packageName += folders.get(j);
                }
                break;
            }
        return packageName;
    }

    private static boolean hasArchiveName(final File path)
    {
        return path.getName().endsWith(".jar") || path.getName().endsWith(".zip");
    }

    public static void loadPlugins(final EventBus pluginEventBus, final File modFile,
            final File thirdPartyPath)
    {
        final PluginLoader loader = new PluginLoader();
        loader.loadNativePlugins(modFile);
        loader.loadThirdPartyPlugins(modFile, thirdPartyPath);
        loader.registerPlugins(pluginEventBus);
    }

    private final List<IPlugin> plugins = Lists.newArrayList();

    private PluginLoader()
    {}

    private void addPlugin(final String pluginName, final String packageName)
    {
        final String pluginClassName = packageName.replace(".class", "");
        try
        {
            final Class<?> pluginCandidate =
                    MetallurgyCore.class.getClassLoader().loadClass(pluginClassName);
            if (pluginCandidate != null)
            {
                boolean isPlugin = false;
                Class<?> target = pluginCandidate;
                do
                {
                    for (final Class<?> iface : target.getInterfaces())
                        if (iface == IPlugin.class)
                        {
                            isPlugin = true;
                            break;
                        }
                    target = target.getSuperclass();
                }
                while (target != null && !isPlugin);

                if (!isPlugin) return;

                final IPlugin plugin = (IPlugin) pluginCandidate.newInstance();
                if (plugin != null)
                {
                    MetallurgyCore.log.fine("Found plugin " + plugin);
                    plugins.add(plugin);
                }
            }
        }
        catch (final Exception ex)
        {
            ; // ignore
        }
    }

    private boolean isPluginName(final String pluginName)
    {
        return pluginName.startsWith("Metallurgy") && pluginName.endsWith("Plugin.class");
    }

    private void loadNativePlugins(final File modfile)
    {
        if (modfile.isFile() && hasArchiveName(modfile)) try
        {
            loadPluginsFromFile(modfile);
        }
        catch (final Throwable e)
        {
            throw new RuntimeException(e);
        }
        else if (modfile.isDirectory()) loadPluginsFromFolder(modfile);
    }

    private void loadPluginsFromFile(final File zipFile) throws IOException
    {
        final ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile));
        try
        {
            for (ZipEntry e; (e = zin.getNextEntry()) != null;)
            {
                final File entry = new File(e.getName());
                final String pluginName = entry.getName();
                if (!entry.isDirectory() && isPluginName(pluginName))
                    addPlugin(pluginName, entry.getPath().replace(File.separatorChar, '.'));
            }
        }
        catch (final Exception ex)
        {
            throw new RuntimeException(ex);
        }
        finally
        {
            zin.close();
        }
    }

    private void loadPluginsFromFolder(final File folder)
    {
        for (final File file : folder.listFiles())
        {
            final String pluginName = file.getName();
            if (file.isFile() && isPluginName(pluginName)) addPlugin(pluginName,
                    getPackageFromFilename(file.getPath()));
            else if (file.isDirectory()) loadPluginsFromFolder(file);
        }
    }

    private void loadThirdPartyPlugins(final File modFile, final File pluginDir)
    {
        try
        {
            if (!pluginDir.isDirectory()) return;

            for (final File file : pluginDir.listFiles())
                if (file.isFile())
                    if (hasArchiveName(file))
                        if (!file.getName().equals(modFile.getName())) loadPluginsFromFile(file);
        }
        catch (final Throwable e)
        {
            throw new RuntimeException(e);
        }

    }

    private void registerPlugins(final EventBus pluginEventBus)
    {
        for (final IPlugin plugin : plugins)
            if (plugin.isActive())
                for (Object subscriber : plugin.getForgeSubscribers())
                    pluginEventBus.register(subscriber);
            else
                MetallurgyCore.log.fine(String.format("Plugin %s not active. Discarding.", plugin));
    }
}
