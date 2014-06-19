package rebelkeithy.mods.metallurgy.core.metalsets;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.StatCollector;

public class ItemMetallurgySword extends ItemSword
{
    private final List<ISwordHitListener> hlList = new ArrayList<ISwordHitListener>();

    String subText;
    String subTextColour = "";

    public ItemMetallurgySword(int par1, EnumToolMaterial par2EnumToolMaterial)
    {
        super(par1, par2EnumToolMaterial);
    }

    public void addHitListener(ISwordHitListener hl)
    {
        hlList.add(hl);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        if (subText != null)
        {
            String colour = "";
            if  (!subTextColour.equalsIgnoreCase(""))
            {
                colour = "\u00A7" + subTextColour;
            }

            for (final String string : subText.split(","))
            {
                String[] unlocalized = string.split(" ");
                String localized = StatCollector.translateToLocal(unlocalized[0]);
                String localizedPower = StatCollector.translateToLocal(unlocalized[1]);

                par3List.add(colour + localized + " " + localizedPower);
            }
        }
    }

    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
    {
        for (final ISwordHitListener hl : hlList)
        {
            hl.hitEntity(par1ItemStack, par2EntityLivingBase, par3EntityLivingBase);
        }

        return super.hitEntity(par1ItemStack, par2EntityLivingBase, par3EntityLivingBase);
    }

    public void setSubText(String text)
    {
        subText = text;
    }

    public void setSubTextColour (String colour)
    {
        subTextColour = colour;
    }

    @Override
    public ItemMetallurgySword setTextureName(String par1Str)
    {
        super.setTextureName(par1Str);
        return this;
    }
}
