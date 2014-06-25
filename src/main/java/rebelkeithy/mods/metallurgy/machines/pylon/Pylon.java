package rebelkeithy.mods.metallurgy.machines.pylon;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
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

        if (ConfigMachines.pylonEnabled)
        {
            for (int i = 0; i < BlockPylon.names.length; i++)
            {
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pylon,1,i), "III","IPI","BBB"
                        ,'I',"ingot"+BlockPylon.names[i], 'P', Item.eyeOfEnder, 'B',"block"+BlockPylon.names[i]));
            }
        }
    }
}
