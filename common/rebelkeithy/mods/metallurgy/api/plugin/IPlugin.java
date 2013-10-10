package rebelkeithy.mods.metallurgy.api.plugin;


/**
 * Metallurgy add-ons implement this interface. Implementors will be acquired and loaded when the
 * Metallurgy mod is installed. To be a valid Metallurgy plugin, implementor class named must begin with "Metallurgy" and end with "Plugin.class"
 */
public interface IPlugin
{
    /**
     * @return an Iterable of event handlers annotated with
     *         {@link net.minecraftforge.event.ForgeSubscribe @ForgeSubscribe}
     */
    public Iterable<?> getForgeSubscribers();

    /**
     * Checks any preconditions and decides whether the plugin is available to the Metallurgy mod.
     * Called once immediately after the implementor is constructed.
     * 
     * @return Flag indicating whether the add-on is active. A false result indicates that the
     *         plugin should be discarded and not be used for anything further.
     */
    public boolean isActive();
}
