package rebelkeithy.mods.metallurgy.vanilla;

import java.io.File;
import java.io.IOException;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import rebelkeithy.mods.keithyutils.metablock.MetaBlock;
import rebelkeithy.mods.keithyutils.metablock.SubBlock;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import rebelkeithy.mods.metallurgy.core.metalsets.ItemMetallurgy;
import cpw.mods.fml.common.registry.GameRegistry;

public class VanillaAddons
{
    public static SubBlock goldBrick;
    public static SubBlock ironBrick;

    public static Item dustIron;
    public static Item dustGold;

    public static int goldBrickID;
    public static int ironBrickID;

    public static final int goldBrickMeta = 0;
    public static final int ironBrickMeta = 1;

    public static int ironDustID = 29000;
    public static int goldDustID = 29001;

    public static void init()
    {
        initConfig();

        goldBrick = new SubBlock(goldBrickID, goldBrickMeta, "Metallurgy:Vanilla/GoldBrick").setHardness(3.0F).setResistance(10.0F).setUnlocalizedName("metallurgy.gold.brick")
                .setCreativeTab(CreativeTabs.tabBlock);
        ironBrick = new SubBlock(ironBrickID, ironBrickMeta, "Metallurgy:Vanilla/IronBrick").setHardness(5.0F).setResistance(10.0F).setUnlocalizedName("metallurgy.iron.brick")
                .setCreativeTab(CreativeTabs.tabBlock);

        MetaBlock.registerID(goldBrickID);
        MetaBlock.registerID(ironBrickID);

        dustIron = new ItemMetallurgy(ironDustID).setTextureName("Metallurgy:Vanilla/IronDust").setUnlocalizedName("metallurgy.iron.dust").setCreativeTab(CreativeTabs.tabMaterials);
        dustGold = new ItemMetallurgy(goldDustID).setTextureName("Metallurgy:Vanilla/GoldDust").setUnlocalizedName("metallurgy.gold.dust").setCreativeTab(CreativeTabs.tabMaterials);

        GameRegistry.registerItem(dustIron, "iron.dust");
        GameRegistry.registerItem(dustGold, "gold.dust");

        OreDictionary.registerOre("dustIron", dustIron);
        OreDictionary.registerOre("dustGold", dustGold);

    }

    public static void initConfig()
    {
        final File fileDir = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3");
        fileDir.mkdir();
        final File cfgFile = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3/MetallurgyVanilla.cfg");

        try
        {
            cfgFile.createNewFile();

        }
        catch (final IOException e)
        {
            MetallurgyCore.log.info("[Metallurgy3] Could not create configuration file for Metallurgy 3 metal set Vanilla. Reason:");
            MetallurgyCore.log.info(e.getLocalizedMessage());
        }

        final Configuration config = new Configuration(cfgFile);
        config.load();

        ironBrickID = config.getBlock("brick", 900).getInt();

        goldBrickID = config.getBlock("brick", 900).getInt();

        ironDustID = config.getItem("iron_dust", ironDustID).getInt();

        goldDustID = config.getItem("gold_dust", goldDustID).getInt();

        if (config.hasChanged())
        {
            config.save();
        }
    }

    public static void load()
    {
        GameRegistry.addRecipe(new ItemStack(goldBrickID, 4, goldBrickMeta), "XX", "XX", 'X', Item.ingotGold);
        GameRegistry.addRecipe(new ItemStack(ironBrickID, 4, ironBrickMeta), "XX", "XX", 'X', Item.ingotIron);
        GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotGold), new ItemStack(goldBrickID, 1, goldBrick.getMeta()));
        GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron), new ItemStack(ironBrickID, 1, ironBrick.getMeta()));

        FurnaceRecipes.smelting().addSmelting(dustIron.itemID, 0, new ItemStack(Item.ingotIron), 0.7F);
        FurnaceRecipes.smelting().addSmelting(dustGold.itemID, 0, new ItemStack(Item.ingotGold), 0.7F);
    }

}
