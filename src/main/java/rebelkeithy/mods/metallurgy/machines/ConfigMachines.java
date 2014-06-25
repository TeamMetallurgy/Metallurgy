package rebelkeithy.mods.metallurgy.machines;

import java.io.File;
import java.io.IOException;

import net.minecraftforge.common.Configuration;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;

public class ConfigMachines
{
    public static boolean crusherEnabled = true;
    public static int crusherID = 920;
    public static float stoneCrusherSpeed = 60;
    public static float copperCrusherSpeed = 30;
    public static float bronzeCrusherSpeed = 20;
    public static float ironCrusherSpeed = 15;
    public static float steelCrusherSpeed = 10;

    public static boolean furnaceEnabled = true;
    public static int furnaceID = 921;
    public static float copperFurnaceSpeed = 9.5F;
    public static float bronzeFurnaceSpeed = 9;
    public static float ironFurnaceSpeed = 8;
    public static float steelFurnaceSpeed = 7;

    public static boolean forgeEnabled = true;
    public static int forgeID = 922;
    public static float[] forgeSpeeds = new float[]
    { 6, 5.5F, 5, 4.5F, 4, 3.5F, 3, 2 };
    public static int[] forgeBuckets = new int[]
    { 10, 20, 30, 40, 50, 60, 80, 100 };
    public static boolean smelterDropsLava = false;

    public static boolean abstractorEnabled = true;
    public static int abstractorID = 923;

    public static boolean chestEnabled = true;
    public static int chestID = 924;

    public static boolean mintEnabled = true;
    public static int mintID = 925;
    public static boolean mintStorageEnabled = true;
    public static int mintStorageID = 926;

    public static boolean enchanterEnabled = true;
    public static int enchanterID = 940;

    public static int coinID = 29002;
    public static int stackID = 29003;
    public static int coinBagID = 29004;
    public static int bullionID = 29005;
    public static int goldCogID = 29017;

    public static boolean tradesEnabled = true;

    public static int lanternID = 928;
    public static int glassDustID = 29006;
    public static int coloredGlassID = 927;

    public static boolean ladderEnabled = true;
    public static int ladderID = 929;

    public static int laserID = 932;

    public static int[] extractorSpeeds = new int[11];
    public static double[] xpBonus = new double[11];
    public static int xpTankID = 933;

    public static boolean pylonEnabled = true;
    public static int pylonID = 934;

    public static int orbID = 29018;
	public static int sawDustID = 29019;

    public static void initConfig()
    {
        final File fileDir = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3");
        fileDir.mkdir();
        final File cfgFile = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3/MetallurgyMachines.cfg");

        try
        {
            cfgFile.createNewFile();
        } catch (final IOException e)
        {
        	 MetallurgyCore.log.info(e.getLocalizedMessage());
        }

        final Configuration config = new Configuration(cfgFile);

        crusherEnabled = config.get("1_enable.machines", "crusher", crusherEnabled).getBoolean(crusherEnabled);
        furnaceEnabled = config.get("1_enable.machines", "furnance", furnaceEnabled).getBoolean(furnaceEnabled);
        forgeEnabled = config.get("1_enable.machines", "smelter", forgeEnabled).getBoolean(forgeEnabled);
        abstractorEnabled = config.get("1_enable.machines", "abstractor", abstractorEnabled).getBoolean(abstractorEnabled);
        chestEnabled = config.get("1_enable.machines", "chest", chestEnabled).getBoolean(chestEnabled);
        mintEnabled = config.get("1_enable.machines", "mint", mintEnabled).getBoolean(mintEnabled);
        enchanterEnabled = config.get("1_enable.machines", "enchanter", enchanterEnabled).getBoolean(enchanterEnabled);
        ladderEnabled = config.get("1_enable.machines", "ladder", ladderEnabled).getBoolean(ladderEnabled);
        pylonEnabled = config.get("1_enable.machines", "pylons", pylonEnabled).getBoolean(pylonEnabled);
        
        crusherID = config.getBlock("crusher", crusherID).getInt();
        furnaceID = config.getBlock("metal_furnace", furnaceID).getInt();
        forgeID = config.getBlock("smelter", forgeID).getInt();
        abstractorID = config.getBlock("abstractor", abstractorID).getInt();
        chestID = config.getBlock("precious_chest", chestID).getInt();
        mintID = config.getBlock("mint", mintID).getInt();
        mintStorageID = config.getBlock("mint_storage", mintStorageID).getInt();
        coloredGlassID = config.getBlock("colored_glass", coloredGlassID).getInt();
        lanternID = config.getBlock("lanterns", lanternID).getInt();
        ladderID = config.getBlock("ladders", ladderID).getInt();
        enchanterID = config.getBlock("enchanter", enchanterID).getInt();
        laserID = config.getBlock("miners_laser", laserID).getInt();
        xpTankID = config.getBlock("xp_tank", xpTankID).getInt();
        pylonID = config.getBlock("pylon", pylonID).getInt();

        coinID = config.getItem("coin", coinID).getInt();
        stackID = config.getItem("stack", stackID).getInt();
        coinBagID = config.getItem("coin_Bag", coinBagID).getInt();
        bullionID = config.getItem("Bullion", bullionID).getInt();
        goldCogID = config.getItem("gold_cog", goldCogID).getInt();
        glassDustID = config.getItem("glass_dust", glassDustID).getInt();
        orbID = config.getItem("fantasy_Orbs", orbID).getInt();
        sawDustID = config.getItem("saw_dust", sawDustID).getInt();

        tradesEnabled = config.get("Mint", "enable_trades", true).getBoolean(true);

        stoneCrusherSpeed = config.get("crusher_speeds", "stone", (int) (stoneCrusherSpeed * 1000)).getInt() / 1000F;
        copperCrusherSpeed = config.get("crusher_speeds", "copper", (int) (copperCrusherSpeed * 1000)).getInt() / 1000F;
        bronzeCrusherSpeed = config.get("crusher_speeds", "bronze", (int) (bronzeCrusherSpeed * 1000)).getInt() / 1000F;
        ironCrusherSpeed = config.get("crusher_speeds", "iron", (int) (ironCrusherSpeed * 1000)).getInt() / 1000F;
        steelCrusherSpeed = config.get("crusher_speeds", "steel", (int) (steelCrusherSpeed * 1000)).getInt() / 1000F;

        copperFurnaceSpeed = config.get("furnace_speeds", "copper", (int) (copperFurnaceSpeed * 1000)).getInt() / 1000F;
        bronzeFurnaceSpeed = config.get("furnace_speeds", "bronze", (int) (bronzeFurnaceSpeed * 1000)).getInt() / 1000F;
        ironFurnaceSpeed = config.get("furnace_speeds", "iron", (int) (ironFurnaceSpeed * 1000)).getInt() / 1000F;
        steelFurnaceSpeed = config.get("furnace_speeds", "steel", (int) (steelFurnaceSpeed * 1000)).getInt() / 1000F;

        extractorSpeeds[0] = config.get("abstractor", "speed_prometheum", 8).getInt();
        extractorSpeeds[1] = config.get("abstractor", "speed_deep_iron", 8).getInt();
        extractorSpeeds[2] = config.get("abstractor", "speed_block_steel", 7).getInt();
        extractorSpeeds[3] = config.get("abstractor", "speed_oureclase", 7).getInt();
        extractorSpeeds[4] = config.get("abstractor", "speed_aredrite", 6).getInt();
        extractorSpeeds[5] = config.get("abstractor", "speed_mithril", 6).getInt();
        extractorSpeeds[6] = config.get("abstractor", "speed_haderoth", 6).getInt();
        extractorSpeeds[7] = config.get("abstractor", "speed_orichalcum", 5).getInt();
        extractorSpeeds[8] = config.get("abstractor", "speed_adamantine", 5).getInt();
        extractorSpeeds[9] = config.get("abstractor", "speed_atlarus", 4).getInt();
        extractorSpeeds[10] = config.get("abstractor", "speed_tartarite", 4).getInt();

        xpBonus[0] = config.get("abstractor", "bonus_prometheum", 1.0).getDouble(1.0);
        xpBonus[1] = config.get("abstractor", "bonus_deep_iron", 1.1).getDouble(1.1);
        xpBonus[2] = config.get("abstractor", "bonus_black_steel", 1.2).getDouble(1.2);
        xpBonus[3] = config.get("abstractor", "bonus_oureclase", 1.3).getDouble(1.3);
        xpBonus[4] = config.get("abstractor", "bonus_aredrite", 1.4).getDouble(1.4);
        xpBonus[5] = config.get("abstractor", "bonus_mithril", 1.4).getDouble(1.4);
        xpBonus[6] = config.get("abstractor", "bonus_haderoth", 1.5).getDouble(1.5);
        xpBonus[7] = config.get("abstractor", "bonus_oreichalcum", 1.6).getDouble(1.6);
        xpBonus[8] = config.get("abstractor", "bonus_admantine", 1.7).getDouble(1.7);
        xpBonus[9] = config.get("abstractor", "bonus_atlarus", 1.8).getDouble(1.8);
        xpBonus[10] = config.get("abstractor", "bonus_tartarite", 2.0).getDouble(2.0);

        final String[] forgeNames =
        { "Ignatius", "Shadow Iron", "Shadow Steel", "Vyroxeres", "Inolashite", "Kalendrite", "Vulcanite", "Sanguinite" };
        for (int i = 0; i < 8; i++)
        {
            String forgeName = forgeNames[i].toLowerCase().trim().replace(" ", "_");
            forgeSpeeds[i] = config.get("forge.speeds", forgeName, forgeSpeeds[i]).getInt();
            forgeBuckets[i] = config.get("forge.buckets", forgeName, forgeBuckets[i]).getInt();
        }
        smelterDropsLava = config.get("forge", "drops_lava", smelterDropsLava).getBoolean(smelterDropsLava);

        if (config.hasChanged())
        {
            config.save();
        }
    }
}
