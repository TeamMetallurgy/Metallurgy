package rebelkeithy.mods.metallurgy.machines.forge;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockNetherForgeItem extends ItemBlock
{
    public BlockNetherForgeItem(int i)
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
            name = "ignatius";
            break;
        }
        case 1:
        {
            name = "shadowIron";
            break;
        }
        case 2:
        {
            name = "shadowSteel";
            break;
        }
        case 3:
        {
            name = "vyroxeres";
            break;
        }
        case 4:
        {
            name = "inolashite";
            break;
        }
        case 5:
        {
            name = "kalendrite";
            break;
        }
        case 6:
        {
            name = "vulcanite";
            break;
        }
        case 7:
        {
            name = "sanguinite";
            break;
        }
        default:
            name = "brick";
        }
        return getUnlocalizedName() + "." + name;
    }
}
