package rebelkeithy.mods.metallurgy.metals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import rebelkeithy.mods.metallurgy.core.MetalInfoDatabase;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import rebelkeithy.mods.metallurgy.core.MetallurgyTabs;
import rebelkeithy.mods.metallurgy.core.metalsets.ISwordHitListener;
import rebelkeithy.mods.metallurgy.core.metalsets.ItemMetallurgy;
import rebelkeithy.mods.metallurgy.core.metalsets.MetalSet;
import rebelkeithy.mods.metallurgy.integration.ComputerCraftIntegration;
import rebelkeithy.mods.metallurgy.integration.IndustrialCraftIntegration;
import rebelkeithy.mods.metallurgy.integration.RailcraftIntegration;
import rebelkeithy.mods.metallurgy.integration.ThaumcraftIntegration;
import rebelkeithy.mods.metallurgy.integration.TreeCapitatorIntegration;
import rebelkeithy.mods.metallurgy.metals.utilityItems.ItemFertilizer;
import rebelkeithy.mods.metallurgy.metals.utilityItems.ItemIgniter;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.BlockLargeTNT;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.BlockMinersTNT;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.EntityLargeTNTPrimed;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.EntityMinersTNTPrimed;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "Metallurgy3Base", name = "Metallurgy 3 Base", version = "3.4.0", dependencies = "required-after:Metallurgy3Core;after:Metallurgy3Vanilla")
@NetworkMod(channels =
{ "MetallurgyBase" }, clientSideRequired = true, serverSideRequired = false)
public class MetallurgyMetals
{

    public boolean isRelease = false;

	private int oreFinderID = 5102;
	private boolean oreFinderEnabled = true;

    public static MetalSet baseSet;
    public static MetalSet preciousSet;
    public static MetalSet netherSet;
    public static MetalSet fantasySet;
    public static MetalSet enderSet;
    public static MetalSet utilitySet;

    public static CreativeTabs baseTab;
    public static CreativeTabs preciousTab;
    public static CreativeTabs netherTab;
    public static CreativeTabs fantasyTab;
    public static CreativeTabs enderTab;
    public static CreativeTabs utilityTab;

    public static Configuration coreConfig;
    public static Configuration utilityConfig;
    public static Configuration fantasyConfig;
    public static Configuration vanillaConfig;
    public static Configuration netherConfig;

    // Utility Items
    public static Item magnesiumIgniter;
    public static Item match;
    public static Item fertilizer;
    public static Item tar;
    public static Item debug;

    public static Block largeTNT;
    public static Block minersTNT;

    // Recipes
    private static boolean midasiumRecipesEnabled;
    private static boolean ironDustRecipesEnabled;

    @SidedProxy(clientSide = "rebelkeithy.mods.metallurgy.metals.ClientProxy", serverSide = "rebelkeithy.mods.metallurgy.metals.CommonProxy")
    public static CommonProxy proxy;

    @Instance(value = "Metallurgy3Base")
    public static MetallurgyMetals instance;

    private void addRailRecipes()
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.rail, 4), "X X", "XSX", "X X", 'X', "ingotCopper", 'S', Item.stick));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.rail, 10), "X X", "XSX", "X X", 'X', "ingotBronze", 'S', Item.stick));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.rail, 14), "X X", "XSX", "X X", 'X', "ingotHepatizon", 'S', Item.stick));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.rail, 26), "X X", "XSX", "X X", 'X', "ingotDamascus Steel", 'S', Item.stick));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.rail, 32), "X X", "XSX", "X X", 'X', "ingotAngmallen", 'S', Item.stick));

    }
    
//    private void addEnderRecipes()
//    {
//        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.enderPearl, 4), " E ", "E E", " E ", 'E', "ingotMeutoite"));
//       
//    }

    public void addSwordEffects()
    {
        ISwordHitListener swordEffects = new NetherSwordHitListener();
        MinecraftForge.EVENT_BUS.register(swordEffects); // Registers the on
                                                         // death event needed
                                                         // by Midasium's
                                                         // looting effect
        if (netherSet.getOreInfo("Ignatius").sword != null)
        {
            netherSet.getOreInfo("Ignatius").sword.addHitListener(swordEffects);
            netherSet.getOreInfo("Ignatius").sword.setSubText("enchantment.fire enchantment.level.1");
            netherSet.getOreInfo("Ignatius").sword.setSubTextColour("c");
        }
        if (netherSet.getOreInfo("Shadow Iron").sword != null)
        {
            netherSet.getOreInfo("Shadow Iron").sword.addHitListener(swordEffects);
            netherSet.getOreInfo("Shadow Iron").sword.setSubText("potion.weakness enchantment.level.1");
            netherSet.getOreInfo("Shadow Iron").sword.setSubTextColour("c");
        }
        if (netherSet.getOreInfo("Shadow Steel").sword != null)
        {
            netherSet.getOreInfo("Shadow Steel").sword.addHitListener(swordEffects);
            netherSet.getOreInfo("Shadow Steel").sword.setSubText("potion.weakness enchantment.level.2");
            netherSet.getOreInfo("Shadow Steel").sword.setSubTextColour("7");
        }
        if (netherSet.getOreInfo("Midasium").sword != null)
        {
            // Midsium'ss effect comes from the onDeath event, not the onHit
            // method
            netherSet.getOreInfo("Midasium").sword.setSubText("enchantment.lootBonus enchantment.level.1");
            netherSet.getOreInfo("Midasium").sword.setSubTextColour("7");
        }
        if (netherSet.getOreInfo("Vyroxeres").sword != null)
        {
            netherSet.getOreInfo("Vyroxeres").sword.addHitListener(swordEffects);
            netherSet.getOreInfo("Vyroxeres").sword.setSubText("enchantment.lootBonus enchantment.level.1");
            netherSet.getOreInfo("Vyroxeres").sword.setSubTextColour("c");
        }
        if (netherSet.getOreInfo("Ceruclase").sword != null)
        {
            netherSet.getOreInfo("Ceruclase").sword.addHitListener(swordEffects);
            netherSet.getOreInfo("Ceruclase").sword.setSubText("potion.moveSlowdown enchantment.level.1");
            netherSet.getOreInfo("Ceruclase").sword.setSubTextColour("c");
        }
        if (netherSet.getOreInfo("Inolashite").sword != null)
        {
            netherSet.getOreInfo("Inolashite").sword.addHitListener(swordEffects);
            netherSet.getOreInfo("Inolashite").sword.setSubText("potion.poison enchantment.level.1,potion.moveSlowdown enchantment.level.1");
            netherSet.getOreInfo("Inolashite").sword.setSubTextColour("7");
        }
        if (netherSet.getOreInfo("Kalendrite").sword != null)
        {
            netherSet.getOreInfo("Kalendrite").sword.addHitListener(swordEffects);
            netherSet.getOreInfo("Kalendrite").sword.setSubText("potion.regeneration enchantment.level.1");
            netherSet.getOreInfo("Kalendrite").sword.setSubTextColour("7");
        }
        if (netherSet.getOreInfo("Amordrine").sword != null)
        {
            netherSet.getOreInfo("Amordrine").sword.addHitListener(swordEffects);
            netherSet.getOreInfo("Amordrine").sword.setSubText("enchantment.metallurgy.heal enchantment.level.1");
            netherSet.getOreInfo("Amordrine").sword.setSubTextColour("7");
        }
        if (netherSet.getOreInfo("Vulcanite").sword != null)
        {
            netherSet.getOreInfo("Vulcanite").sword.addHitListener(swordEffects);
            netherSet.getOreInfo("Vulcanite").sword.setSubText("enchantment.fire enchantment.level.2");
            netherSet.getOreInfo("Vulcanite").sword.setSubTextColour("c");
        }
        if (netherSet.getOreInfo("Sanguinite").sword != null)
        {
            netherSet.getOreInfo("Sanguinite").sword.addHitListener(swordEffects);
            netherSet.getOreInfo("Sanguinite").sword.setSubText("potion.wither enchantment.level.1");
            netherSet.getOreInfo("Sanguinite").sword.setSubTextColour("c");
        }

        swordEffects = new FantasySwordHitListener();
        MinecraftForge.EVENT_BUS.register(swordEffects); // Registers the on
                                                         // death event needed
                                                         // by Astral Silver's
                                                         // and Carmot's looting
                                                         // effect

        if (fantasySet.getOreInfo("Deep Iron").sword != null)
        {
            fantasySet.getOreInfo("Deep Iron").sword.addHitListener(swordEffects);
            fantasySet.getOreInfo("Deep Iron").sword.setSubText("potion.blindness enchantment.level.1");
            fantasySet.getOreInfo("Deep Iron").sword.setSubTextColour("c");
        }
        if (fantasySet.getOreInfo("Black Steel").sword != null)
        {
            fantasySet.getOreInfo("Black Steel").sword.addHitListener(swordEffects);
            fantasySet.getOreInfo("Black Steel").sword.setSubText("potion.blindness enchantment.level.2");
            fantasySet.getOreInfo("Black Steel").sword.setSubTextColour("c");
        }
        if (fantasySet.getOreInfo("Oureclase").sword != null)
        {
            fantasySet.getOreInfo("Oureclase").sword.addHitListener(swordEffects);
            fantasySet.getOreInfo("Oureclase").sword.setSubText("potion.resistance enchantment.level.1");
            fantasySet.getOreInfo("Oureclase").sword.setSubTextColour("7");
        }
        if (fantasySet.getOreInfo("Astral Silver").sword != null)
        {
            fantasySet.getOreInfo("Astral Silver").sword.addHitListener(swordEffects);
            fantasySet.getOreInfo("Astral Silver").sword.setSubText("enchantment.lootBonus enchantment.level.1");
            fantasySet.getOreInfo("Astral Silver").sword.setSubTextColour("7");
        }
        if (fantasySet.getOreInfo("Carmot").sword != null)
        {
            fantasySet.getOreInfo("Carmot").sword.addHitListener(swordEffects);
            fantasySet.getOreInfo("Carmot").sword.setSubText("enchantment.lootBonus enchantment.level.2");
            fantasySet.getOreInfo("Carmot").sword.setSubTextColour("7");
        }
        if (fantasySet.getOreInfo("Mithril").sword != null)
        {
            fantasySet.getOreInfo("Mithril").sword.addHitListener(swordEffects);
            fantasySet.getOreInfo("Mithril").sword.setSubText("potion.digSpeed enchantment.level.1");
            fantasySet.getOreInfo("Mithril").sword.setSubTextColour("7");
        }
        if (fantasySet.getOreInfo("Quicksilver").sword != null)
        {
            fantasySet.getOreInfo("Quicksilver").sword.addHitListener(swordEffects);
            fantasySet.getOreInfo("Quicksilver").sword.setSubText("potion.digSpeed enchantment.level.1");
            fantasySet.getOreInfo("Quicksilver").sword.setSubTextColour("7");
        }
        if (fantasySet.getOreInfo("Haderoth").sword != null)
        {
            fantasySet.getOreInfo("Haderoth").sword.addHitListener(swordEffects);
            fantasySet.getOreInfo("Haderoth").sword.setSubText("potion.digSpeed enchantment.level.1,enchantment.fire enchantment.level.2");
            fantasySet.getOreInfo("Haderoth").sword.setSubTextColour("c");
        }
        if (fantasySet.getOreInfo("Orichalcum").sword != null)
        {
            fantasySet.getOreInfo("Orichalcum").sword.addHitListener(swordEffects);
            fantasySet.getOreInfo("Orichalcum").sword.setSubText("potion.resistance enchantment.level.1");
            fantasySet.getOreInfo("Orichalcum").sword.setSubTextColour("c");
        }
        if (fantasySet.getOreInfo("Celenegil").sword != null)
        {
            fantasySet.getOreInfo("Celenegil").sword.addHitListener(swordEffects);
            fantasySet.getOreInfo("Celenegil").sword.setSubText("potion.resistance enchantment.level.1");
            fantasySet.getOreInfo("Celenegil").sword.setSubTextColour("7");
        }
        if (fantasySet.getOreInfo("Adamantine").sword != null)
        {
            fantasySet.getOreInfo("Adamantine").sword.addHitListener(swordEffects);
            fantasySet.getOreInfo("Adamantine").sword.setSubText("potion.fireResistance enchantment.level.1,enchantment.fire enchantment.level.2");
            fantasySet.getOreInfo("Adamantine").sword.setSubTextColour("7");
        }
        if (fantasySet.getOreInfo("Atlarus").sword != null)
        {
            fantasySet.getOreInfo("Atlarus").sword.addHitListener(swordEffects);
            fantasySet.getOreInfo("Atlarus").sword.setSubText("potion.damageBoost enchantment.level.2");
            fantasySet.getOreInfo("Atlarus").sword.setSubTextColour("7");
        }
        if (fantasySet.getOreInfo("Tartarite").sword != null)
        {
            fantasySet.getOreInfo("Tartarite").sword.addHitListener(swordEffects);
            fantasySet.getOreInfo("Tartarite").sword.setSubText("potion.wither enchantment.level.1,enchantment.fire enchantment.level.2");
            fantasySet.getOreInfo("Tartarite").sword.setSubTextColour("c");
        }
    }

    public void createMidasiumRecipes()
    {

        ArrayList<ItemStack> goldDusts = OreDictionary.getOres("dustGold");

        if (goldDusts.size() < 1)
        {
            MetallurgyCore.log.warning("Gold dust wasn't found in the ore dictionary, skipping adding Midasium recipes");
            return;
        }

        ItemStack dustGold = goldDusts.get(0);

        if (dustGold == null)
        {
            MetallurgyCore.log.warning("Gold dust wasn't found in the ore dictionary, skipping adding Midasium recipes");
            return;
        }

        final String[] ores = OreDictionary.getOreNames();
        for (final String name : ores)
        {
            if (name.contains("dust") && !name.toLowerCase().contains("tiny") && !name.toLowerCase().contains("clay") && !name.toLowerCase().contains("quartz") && !name.toLowerCase().contentEquals("dustgold"))
            {
                MetallurgyCore.log.fine("Adding recipe for " + name + " midasium = gold");
                GameRegistry.addRecipe(new ShapelessOreRecipe(dustGold.copy(), "dustMidasium", name));
            }
        }
    }

    public void createIronDustRecipes ()
    {
        ArrayList<ItemStack> ironDusts = OreDictionary.getOres("dustIron");

        if (ironDusts.size() < 1)
        {
            MetallurgyCore.log.warning("Iron Dust wasn't found in the ore dictionary, skipping adding Iron dust coverting recipes");
            return;
        }
        ItemStack dustIron = ironDusts.get(0);

        if (dustIron == null)
        {
            MetallurgyCore.log.warning("Iron Dust wasn't found in the ore dictionary, skipping adding Iron dust coverting recipes");
            return;
        }

        dustIron = dustIron.copy();
        dustIron.stackSize = 2;

        GameRegistry.addRecipe(new ShapelessOreRecipe(dustIron.copy(), "dustShadow Iron", "dustIgnatius"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(dustIron.copy(), "dustDeep Iron", "dustPrometheum"));

    }
    public void createUtilityItems()
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.blazeRod), "I", "I", 'I', "ingotVulcanite"));

        int id = utilityConfig.getBlock("he_tnt", 930).getInt();
        if (id != 0)
        {
            largeTNT = new BlockLargeTNT(id).setUnlocalizedName("metallurgy.largeTNT").setCreativeTab(utilityTab);
            GameRegistry.registerBlock(largeTNT, "HETNT");
            EntityRegistry.registerModEntity(EntityLargeTNTPrimed.class, "LargeTNTEntity", 113, this, 64, 10, true);
            if (utilityConfig.get("recipes", "enable_he_tnt", true).getBoolean(true))
            {
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(largeTNT, 4), "MPM", "PTP", "MPM", 'M', "dustSaltpeter", 'P', "dustSulfur", 'T', Block.tnt));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(largeTNT, 4), "MPM", "PTP", "MPM", 'P', "dustSaltpeter", 'M', "dustSulfur", 'T', Block.tnt));    
            }
        }

        id = utilityConfig.getBlock("le_tnt", 931).getInt();
        if (id != 0)
        {
            minersTNT = new BlockMinersTNT(id).setUnlocalizedName("metallurgy.minersTNT").setCreativeTab(utilityTab);
            GameRegistry.registerBlock(minersTNT, "LETNT");
            EntityRegistry.registerModEntity(EntityMinersTNTPrimed.class, "MinersTNTEntity", 114, this, 64, 10, true);
            if (utilityConfig.get("recipes", "enable_le_tnt", true).getBoolean(true))
            {
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(minersTNT, 4), "MPM", "PTP", "MPM", 'M', "dustMagnesium", 'P', "dustPhosphorus", 'T', Block.tnt));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(minersTNT, 4), "PMP", "MTM", "PMP", 'M', "dustMagnesium", 'P', "dustPhosphorus", 'T', Block.tnt));
            }
        }

        id = utilityConfig.getItem("magnesium_igniter", 29007).getInt();
        magnesiumIgniter = new ItemIgniter(id).setMaxDamage(128).setMaxStackSize(1).setTextureName("Metallurgy:Utility/Igniter").setUnlocalizedName("metallurgy.igniter").setCreativeTab(utilityTab);
        GameRegistry.registerItem(magnesiumIgniter, "magnesium.igniter");
        if (utilityConfig.get("recipes", "enable_magnesium_igniter", true).getBoolean(true))
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(magnesiumIgniter), "X ", " F", 'X', "dustMagnesium", 'F', Item.flint));
        }

        id = utilityConfig.getItem("match", 29008).getInt();
        match = new ItemIgniter(id).setMatch(true).setMaxDamage(1).setMaxStackSize(64).setTextureName("Metallurgy:Utility/Match").setUnlocalizedName("metallurgy.match").setCreativeTab(utilityTab);
        GameRegistry.registerItem(match, "match");
        if (utilityConfig.get("recipes", "enable_match", true).getBoolean(true))
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(match, 4), "X", "|", 'X', "dustPhosphorus", '|', Item.stick));
        }

        id = utilityConfig.getItem("fertilizer", 29009).getInt();
        fertilizer = new ItemFertilizer(id).setTextureName("Metallurgy:Utility/Fertilizer").setUnlocalizedName("metallurgy.fertilizer").setCreativeTab(utilityTab);
        GameRegistry.registerItem(fertilizer, "fertilizer");
        if (utilityConfig.get("Recipes", "Enable Fertilizer", true).getBoolean(true))
        {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fertilizer, 8), "dustPhosphorus", "dustMagnesium", "dustPotash"));
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fertilizer, 8), "dustPhosphorus", "dustMagnesium", "dustSaltpeter"));
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fertilizer, 8), "dustPhosphorus", "dustSaltpeter", "dustPotash"));
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fertilizer, 8), "dustSaltpeter", "dustMagnesium", "dustPotash"));
        }
        OreDictionary.registerOre("itemFertilizer", fertilizer);

        id = utilityConfig.getItem("tar", 29010).getInt();
        tar = new ItemMetallurgy(id).setTextureName("Metallurgy:Utility/Tar").setUnlocalizedName("metallurgy.tar").setCreativeTab(utilityTab);
        GameRegistry.registerItem(tar, "tar");
        OreDictionary.registerOre("itemTar", tar);
        GameRegistry.addSmelting(MetalInfoDatabase.getItem("Bitumen").itemID, new ItemStack(tar), 0.1F);

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Item.gunpowder, 4), new ItemStack(Item.coal, 1, 1), "dustSulfur", "dustSaltpeter"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(Item.magmaCream, "itemTar", Item.blazePowder));
        GameRegistry.addRecipe(new ShapedOreRecipe(Block.pistonStickyBase, "T", "P", 'T', "itemTar", 'P', Block.pistonBase));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.leash, 2), "SS ", "ST ", "  S", 'T', "itemTar", 'S', Item.silk));
        
        if (isSetEnabled("Utility"))
        {
            ((MetallurgyTabs) utilityTab).setIconItem(fertilizer.itemID);
        }
    }

    @EventHandler
    public void Init(FMLInitializationEvent event)
    {
        if (oreFinderEnabled)
        {
            debug = new ItemOreFinder(oreFinderID).setUnlocalizedName("metallurgy.oreFinder").setCreativeTab(CreativeTabs.tabTools);
        }

        if (fantasySet.getOreInfo("Atral Silver").ore != null)
        {
            fantasySet.getOreInfo("Astral Silver").ore.addDisplayListener(new DisplayListenerOreParticles("FantasyOre", 0.6, 0.8, 0.95));
        }
        if (fantasySet.getOreInfo("Carmot").ore != null)
        {
            fantasySet.getOreInfo("Carmot").ore.addDisplayListener(new DisplayListenerOreParticles("FantasyOre", 0.8, 0.8, 0.4));
        }
        if (fantasySet.getOreInfo("Mithril").ore != null)
        {
            fantasySet.getOreInfo("Mithril").ore.addDisplayListener(new DisplayListenerOreParticles("FantasyOre", 0.6, 0.9, 0.95));
        }
        if (fantasySet.getOreInfo("Orichalcum").ore != null)
        {
            fantasySet.getOreInfo("Orichalcum").ore.addDisplayListener(new DisplayListenerOreParticles("FantasyOre", 0.3, 0.5, 0.15));
        }
        if (fantasySet.getOreInfo("Adamantine").ore != null)
        {
            fantasySet.getOreInfo("Adamantine").ore.addDisplayListener(new DisplayListenerOreParticles("FantasyOre", 0.5, 0.2, 0.2));
        }
        if (fantasySet.getOreInfo("Atlarus").ore != null)
        {
            fantasySet.getOreInfo("Atlarus").ore.addDisplayListener(new DisplayListenerOreParticles("FantasyOre", 0.8, 0.8, 0.2));
        }

        if (netherSet.getOreInfo("Midasium").ore != null)
        {
            netherSet.getOreInfo("Midasium").ore.addDisplayListener(new DisplayListenerOreParticles("NetherOre", 1.0, 0.8, 0.25));
        }
        if (netherSet.getOreInfo("Vyroxeres").ore != null)
        {
            netherSet.getOreInfo("Vyroxeres").ore.addDisplayListener(new DisplayListenerVyroxeresOreParticles());
            netherSet.getOreInfo("Vyroxeres").ore.addCollisionListener(new VyroxeresCollisionListener());
        }
        if (netherSet.getOreInfo("Ceruclase").ore != null)
        {
            netherSet.getOreInfo("Ceruclase").ore.addDisplayListener(new DisplayListenerOreParticles("NetherOre", 0.35, 0.6, 0.9));
        }
        if (netherSet.getOreInfo("Kalendrite").ore != null)
        {
            netherSet.getOreInfo("Kalendrite").ore.addDisplayListener(new DisplayListenerOreParticles("NetherOre", 0.8, 0.4, 0.8));
        }
        if (netherSet.getOreInfo("Vulcanite").ore != null)
        {
            netherSet.getOreInfo("Vulcanite").ore.addDisplayListener(new DisplayListenerVulcaniteOreParticles());
        }
        if (netherSet.getOreInfo("Sanguinite").ore != null)
        {
            netherSet.getOreInfo("Sanguinite").ore.addDisplayListener(new DisplayListenerOreParticles("NetherOre", 0.85, 0.0, 0.0));
        }


        addRailRecipes();
        addSwordEffects();

        proxy.registerParticles();
        proxy.registerRenders();

        TreeCapitatorIntegration.init();
    }

    public Configuration initConfig(String name)
    {
        final File fileDir = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3");
        fileDir.mkdir();
        final File cfgFile = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3/Metallurgy" + name + ".cfg");

        try
        {
            cfgFile.createNewFile();
        } catch (final IOException e)
        {
        	 MetallurgyCore.log.info(e.getLocalizedMessage());
        }

        return new Configuration(cfgFile);
    }

    private boolean isSetEnabled(String setName)
    {

        final File fileDir = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3");
        fileDir.mkdir();
        final File cfgFile = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3/Metallurgy" + setName + ".cfg");

        try
        {
            cfgFile.createNewFile();
        } catch (final IOException e)
        {
        	 MetallurgyCore.log.info("[Metallurgy3] Could not create configuration file for Metallurgy 3 metal set " + setName + ". Reason:");
        	 MetallurgyCore.log.info(e.getLocalizedMessage());
        }

        final Configuration config = new Configuration(cfgFile);
        config.load();

        final boolean enabled = config.get("1_enable", "enable_" + setName.toLowerCase().trim().replace(" ", "_") + "_set", true).getBoolean(true);

        if (config.hasChanged())
        {
            config.save();
        }
        return enabled;
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        if (isSetEnabled("Base") && baseSet.getOreInfo("Steel").helmet != null)
        {
            ((MetallurgyTabs) baseTab).setIconItem(baseSet.getOreInfo("Steel").helmet.itemID);
        }
        if (isSetEnabled("Precious") && preciousSet.getOreInfo("Platinum").helmet != null)
        {
            ((MetallurgyTabs) preciousTab).setIconItem(preciousSet.getOreInfo("Platinum").helmet.itemID);
        }
        if (isSetEnabled("Nether") && netherSet.getOreInfo("Sanguinite").helmet != null)
        {
            ((MetallurgyTabs) netherTab).setIconItem(netherSet.getOreInfo("Sanguinite").helmet.itemID);
        }
        if (isSetEnabled("Fantasy") && fantasySet.getOreInfo("Tartarite").helmet != null)
        {
            ((MetallurgyTabs) fantasyTab).setIconItem(fantasySet.getOreInfo("Tartarite").helmet.itemID);
        }
        if (isSetEnabled("Ender") && enderSet.getOreInfo("Desichalkos").helmet != null)
        {
            ((MetallurgyTabs) enderTab).setIconItem(enderSet.getOreInfo("Desichalkos").helmet.itemID);
        }

        if (midasiumRecipesEnabled)
        {
            createMidasiumRecipes();
        }

        if (ironDustRecipesEnabled)
        {
            createIronDustRecipes();
        }

        ThaumcraftIntegration.init();
        IndustrialCraftIntegration.init();
        RailcraftIntegration.init();

        try
        {
            Class.forName("dan200.turtle.api.TurtleAPI");
            ComputerCraftIntegration.init();
        }
        catch (final Exception e)
        {
            MetallurgyCore.log.info("ComputerCraft have been not found, Skipping integration");
        }
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

        coreConfig = initConfig("Core");
        coreConfig.load();
        
        oreFinderID = coreConfig.getItem("debug", "ore_finder_id", oreFinderID).getInt(oreFinderID);
        oreFinderEnabled = coreConfig.get("debug", "ore_finder_enabled", oreFinderEnabled).getBoolean(oreFinderEnabled);
        
        if(coreConfig.hasChanged())
        {
            coreConfig.save();
        }

        utilityConfig = initConfig("Utility");
        utilityConfig.load();

        fantasyConfig = initConfig("Fantasy");
        
        netherConfig = initConfig("Nether");
        netherConfig.load();

        String midasiumComment = "Enables recipes to convert any dust in Ore Dictionary into gold.";

        midasiumRecipesEnabled = netherConfig.get("1_enable.item_recipes", "midasium_to_gold"
                , true, midasiumComment).getBoolean(true);

        if (netherConfig.hasChanged())
        {
            netherConfig.save();
        }

        vanillaConfig = initConfig("Vanilla");
        vanillaConfig.load();

        String ironDustComment = "Adds additional Iron dust crafting recipes:"
                + "\n-Ignatius dust + Shadow Iron dust = 2 iron dusts."
                + "\n-Prometheum dust + Deep Iron dust = 2 iron dusts.";

        ironDustRecipesEnabled = vanillaConfig.get("1_enable.item_recipes", "more_iron_dust_crafting"
                , true, ironDustComment).getBoolean(true);

        if (vanillaConfig.hasChanged())
        {
            vanillaConfig.save();
        }

        GameRegistry.registerFuelHandler(new IFuelHandler()
        {
            
            @Override
            public int getBurnTime(ItemStack fuel)
            {
                if(fuel.getItem() == netherSet.getOreInfo("Ignatius").dust) {
                        return 3200;
                }

                if(fuel.getItem() == netherSet.getOreInfo("Vulcanite").dust) {
                    return 32000;
                }
                
                return 0;
            }
        });

        // potion = new MetallurgyPotion(21, false,
        // 8356754).setPotionName("Low Gravity");

        if (isSetEnabled("Base"))
        {
            baseTab = new MetallurgyTabs("metallurgy.base");
        }
        else
        {
            baseTab = CreativeTabs.tabBlock;
        }
        if (isSetEnabled("Precious"))
        {
            preciousTab = new MetallurgyTabs("metallurgy.precious");
        }
        if (isSetEnabled("Nether"))
        {
            netherTab = new MetallurgyTabs("metallurgy.nether");
        }
        if (isSetEnabled("Fantasy"))
        {
            fantasyTab = new MetallurgyTabs("metallurgy.fantasy");
        }
        if (isSetEnabled("Ender"))
        {
            enderTab = new MetallurgyTabs("metallurgy.ender");
        }
        if (isSetEnabled("Utility"))
        {
            utilityTab = new MetallurgyTabs("metallurgy.utility");
        }

        String filepath = "assets/metallurgy/data";
        MetalInfoDatabase.readMetalDataFromClassPath(filepath + "/spreadsheet.csv");
        MetalInfoDatabase.readItemDataFromClassPath(utilityConfig, filepath + "/Items.csv", utilityTab);

        utilityConfig.save();

        baseSet = new MetalSet("Base", MetalInfoDatabase.getSpreadsheetDataForSet("Base"), baseTab);
        preciousSet = new MetalSet("Precious", MetalInfoDatabase.getSpreadsheetDataForSet("Precious"), preciousTab);
        netherSet = new MetalSet("Nether", MetalInfoDatabase.getSpreadsheetDataForSet("Nether"), netherTab);
        fantasySet = new MetalSet("Fantasy", MetalInfoDatabase.getSpreadsheetDataForSet("Fantasy"), fantasyTab);
        enderSet = new MetalSet("Ender", MetalInfoDatabase.getSpreadsheetDataForSet("Ender"), enderTab);
        utilitySet = new MetalSet("Utility", MetalInfoDatabase.getSpreadsheetDataForSet("Utility"), utilityTab);

        if (isSetEnabled("Utility"))
        {
            utilityConfig.load();
            createUtilityItems();
            utilityConfig.save();
        }
    }
}
