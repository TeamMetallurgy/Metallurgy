package rebelkeithy.mods.metallurgy.metals;

import java.util.List;

import rebelkeithy.mods.metallurgy.api.plugin.IPlugin;

import com.google.common.collect.ImmutableList;

public class MetallurgyNativePlugin implements IPlugin
{
    @Override
    public List<? extends Object> getForgeSubscribers()
    {
        return ImmutableList.of(new MetallurgyMetals());
    }

    @Override
    public boolean isActive()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return "Metallurgy Native Metals Plugin";
    }
}
