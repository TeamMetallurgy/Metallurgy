package rebelkeithy.mods.metallurgy.machines.abstractor;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockAbstractorItem extends ItemBlock
{
    public BlockAbstractorItem(int i)
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
            name = "prometheum";
            break;
        }
        case 1:
        {
            name = "deepIron";
            break;
        }
        case 2:
        {
            name = "blackSteel";
            break;
        }
        case 3:
        {
            name = "oureclase";
            break;
        }
        case 4:
        {
            name = "mithril";
            break;
        }
        case 5:
        {
            name = "haderoth";
            break;
        }
        case 6:
        {
            name = "orichalcum";
            break;
        }
        case 7:
        {
            name = "adamantine";
            break;
        }
        case 8:
        {
            name = "atlarus";
            break;
        }
        case 9:
        {
            name = "tartarite";
            break;
        }
        default:
            name = "brick";
        }
        return getUnlocalizedName() + "." + name;
    }
}