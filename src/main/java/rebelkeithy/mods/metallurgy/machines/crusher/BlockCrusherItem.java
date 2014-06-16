package rebelkeithy.mods.metallurgy.machines.crusher;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockCrusherItem extends ItemBlock
{
    public BlockCrusherItem(int i)
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
            name = "stone";
            break;
        }
        case 1:
        {
            name = "copper";
            break;
        }
        case 2:
        {
            name = "bronze";
            break;
        }
        case 3:
        {
            name = "iron";
            break;
        }
        case 4:
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
