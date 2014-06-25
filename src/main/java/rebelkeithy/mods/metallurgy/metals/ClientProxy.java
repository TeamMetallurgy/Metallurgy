package rebelkeithy.mods.metallurgy.metals;

import java.io.File;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import rebelkeithy.mods.keithyutils.particleregistry.ParticleRegistry;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.EntityLargeTNTPrimed;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.EntityMinersTNTPrimed;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.RenderLargeTNTPrimed;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.RenderMinersTNTPrimed;

public class ClientProxy extends CommonProxy
{
    @Override
    public File getMinecraftDir()
    {
        return Minecraft.getMinecraft().mcDataDir;
    }

    @Override
    public void registerParticles()
    {
        ParticleRegistry.registerParticle("NetherOre", EntityNetherOreFX.class);
        ParticleRegistry.registerParticle("FantasyOre", EntityFantasyOreFX.class);
    }

    @Override
    public void registerRenders()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityLargeTNTPrimed.class, new RenderLargeTNTPrimed());
        RenderingRegistry.registerEntityRenderingHandler(EntityMinersTNTPrimed.class, new RenderMinersTNTPrimed());
    }
}
