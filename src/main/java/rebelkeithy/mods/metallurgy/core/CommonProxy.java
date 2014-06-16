package rebelkeithy.mods.metallurgy.core;

import java.io.File;

import net.minecraft.world.World;

public class CommonProxy
{
    public World getClientWorld()
    {
        return null;
    }

    public File getMinecraftDir()
    {
        return new File(".");
    }

    public void spawnParticle(String string, World par1World, double x, double y, double z, double r, double g, double b)
    {
    }
}
