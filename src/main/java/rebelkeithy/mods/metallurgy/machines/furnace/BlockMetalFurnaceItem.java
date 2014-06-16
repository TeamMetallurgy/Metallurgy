package rebelkeithy.mods.metallurgy.machines.furnace;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockMetalFurnaceItem extends ItemBlock
{

    public BlockMetalFurnaceItem(int i)
    {
        super(i);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int i)
    {
        return i;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        String name = "";
        switch (itemstack.getItemDamage())
        {
        case 0:
        {
            name = "copper";
            break;
        }
        case 1:
        {
            name = "bronze";
            break;
        }
        case 2:
        {
            name = "iron";
            break;
        }
        case 3:
        {
            name = "steel";
            break;
        }
        default:
            name = "brick";
        }
        return getUnlocalizedName() + "." + name;
    }
}
