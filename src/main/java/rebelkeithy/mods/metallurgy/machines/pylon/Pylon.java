package rebelkeithy.mods.metallurgy.machines.pylon;

import net.minecraft.block.Block;
import rebelkeithy.mods.metallurgy.machines.ConfigMachines;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import cpw.mods.fml.common.registry.GameRegistry;

public class Pylon
{
    public static Block pylon;

    public static void init()
    {
        pylon = new BlockPylon(ConfigMachines.pylonID).setHardness(4f).setCreativeTab(MetallurgyMachines.machineTab).setUnlocalizedName("metallurgy.pylon");

        GameRegistry.registerBlock(pylon, BlockPylonItem.class, "MetallurgyPylon");
        GameRegistry.registerTileEntity(TileEntityPylon.class, "MetallurgyPylonTileEntity");

    }
}
