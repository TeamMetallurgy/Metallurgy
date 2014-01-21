package rebelkeithy.mods.metallurgy.integration;

import net.minecraft.item.ItemStack;
import rebelkeithy.mods.metallurgy.core.metalsets.OreInfo;
import rebelkeithy.mods.metallurgy.metals.MetallurgyMetals;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import cpw.mods.fml.common.Loader;

public class ThaumcraftIntegration
{

    private static void addAspects()
    {
        /**
         * This structure iterates through all metals available in the mod and
         * adds some best guess values We only need to give aspects to dusts,
         * ingots and ores as Thaumcraft automaticly calculates aspects for
         * blocks that are make of blocks which all have aspects
         * 
         * As a baseline i am using what Thaumcraft gives to some non-vanilla
         * blocks: For ores, Copper ore gets 5 metal, 2 stone 1 exhange, Tin
         * gets 5 metal, 2 stone 1 control For bars copper gets 6 metal 2 life,
         * Tin gets 6 metal 2 crystal Dusts get 1 destruction and 5-6 metal +
         * 1-2 other aspect to a total of 8 Generally easily available stuff
         * gives 8 aspects, but more rare stuff like diamon ore and emerald ore
         * give 20
         * */
        /*
         * for (String setName :
         * rebelkeithy.mods.metallurgy.api.MetallurgyAPI.getMetalSetNames() ){
         * for (IOreInfo oreInfo :
         * MetallurgyAPI.getMetalSet(setName).getOreList().values()){ ItemStack
         * ore = oreInfo.getOre(); ItemStack dust = oreInfo.getDust(); ItemStack
         * ingot = oreInfo.getIngot(); switch (oreInfo.getType()) {
         * 
         * case ALLOY: //only need to add ingot and dust..
         * 
         * if(setName == "base") { if(dust != null)
         * ThaumcraftApi.registerObjectTag(dust.itemID ,dust.getItemDamage(),
         * (new ObjectTags()).add(EnumTag.METAL, 7).add(EnumTag.DESTRUCTION,
         * 1)); if(ingot != null) ThaumcraftApi.registerObjectTag(ingot.itemID
         * ,ingot.getItemDamage(), (new ObjectTags()).add(EnumTag.METAL,
         * 6).add(EnumTag.CRYSTAL, 2)); break; }
         * 
         * if(dust != null) ThaumcraftApi.registerObjectTag(dust.itemID
         * ,dust.getItemDamage(), (new ObjectTags()).add(EnumTag.METAL,
         * 7).add(EnumTag.DESTRUCTION, 1)); if(ingot != null)
         * ThaumcraftApi.registerObjectTag(ingot.itemID ,ingot.getItemDamage(),
         * (new ObjectTags()).add(EnumTag.METAL, 6).add(EnumTag.CRYSTAL, 1)); //
         * TODO more special cases break;
         * 
         * case CATALYST: // doing all three, ill just add soem CRAFT to all of
         * em if(ore != null) ThaumcraftApi.registerObjectTag(ore.itemID
         * ,ore.getItemDamage(), (new ObjectTags()).add(EnumTag.METAL,
         * 5).add(EnumTag.CRAFT, 1).add(EnumTag.ROCK, 2)); if(dust != null)
         * ThaumcraftApi.registerObjectTag(dust.itemID ,dust.getItemDamage(),
         * (new ObjectTags()).add(EnumTag.METAL, 6).add(EnumTag.DESTRUCTION,
         * 1).add(EnumTag.CRAFT, 1)); if(ingot != null)
         * ThaumcraftApi.registerObjectTag(ingot.itemID ,ingot.getItemDamage(),
         * (new ObjectTags()).add(EnumTag.METAL, 7).add(EnumTag.CRAFT, 1));
         * 
         * break;
         * 
         * case DROP: // redstone ore is 2 earth 1 power 1 mechanish, gunpowder
         * is 2 fire 2 destruction
         * 
         * if(oreInfo.getName() == "Sulfur" && ore != null){ //because used for
         * gunpowder ThaumcraftApi.registerObjectTag(ore.itemID
         * ,ore.getItemDamage(), (new ObjectTags()).add(EnumTag.EARTH,
         * 2).add(EnumTag.FIRE, 1).add(EnumTag.DESTRUCTION, 1)); ItemStack drop
         * = oreInfo.getDrop(); if(drop != null) {
         * ThaumcraftApi.registerObjectTag(ore.itemID ,ore.getItemDamage(), (new
         * ObjectTags()).add(EnumTag.FIRE, 2).add(EnumTag.DESTRUCTION, 2)); }
         * break; }
         * 
         * if(oreInfo.getName() == "Saltpeter" && ore != null){ //used for
         * fertilizer and gunpowder ThaumcraftApi.registerObjectTag(ore.itemID
         * ,ore.getItemDamage(), (new ObjectTags()).add(EnumTag.EARTH,
         * 2).add(EnumTag.CROP, 1).add(EnumTag.DESTRUCTION, 1)); ItemStack drop
         * = oreInfo.getDrop(); if(drop != null){
         * ThaumcraftApi.registerObjectTag(ore.itemID ,ore.getItemDamage(), (new
         * ObjectTags()).add(EnumTag.CROP, 2).add(EnumTag.DESTRUCTION, 2)); }
         * break; } // TODO add more special cases if(ore != null) {
         * ThaumcraftApi.registerObjectTag(ore.itemID ,ore.getItemDamage(), (new
         * ObjectTags()).add(EnumTag.EARTH, 2).add(EnumTag.CROP,
         * 1).add(EnumTag.DESTRUCTION, 1)); ItemStack drop = oreInfo.getDrop();
         * if(drop != null && ore != null) {
         * ThaumcraftApi.registerObjectTag(ore.itemID ,ore.getItemDamage(), (new
         * ObjectTags()).add(EnumTag.CROP, 2).add(EnumTag.DESTRUCTION, 2)); } }
         * break; case ORE: if(ore != null)
         * ThaumcraftApi.registerObjectTag(ore.itemID ,ore.getItemDamage(), (new
         * ObjectTags()).add(EnumTag.METAL, 5).add(EnumTag.CRAFT,
         * 1).add(EnumTag.ROCK, 2)); if(dust != null)
         * ThaumcraftApi.registerObjectTag(dust.itemID ,dust.getItemDamage(),
         * (new ObjectTags()).add(EnumTag.METAL, 6).add(EnumTag.DESTRUCTION,
         * 1).add(EnumTag.CRAFT, 1)); if(ingot != null)
         * ThaumcraftApi.registerObjectTag(ingot.itemID ,ingot.getItemDamage(),
         * (new ObjectTags()).add(EnumTag.METAL, 7).add(EnumTag.CRAFT, 1));
         * break; case RESPAWN: break; default: break;
         * 
         * } } }
         */
    	
    	/** Thaumcraft 4 support */
    	
    	/** Base Metals */
    	addAspectToMaterials( MetallurgyMetals.baseSet.getOreInfo("Copper"),
    			(new AspectList()).add(Aspect.METAL, 4));
    	
    	addAspectToMaterials(MetallurgyMetals.baseSet.getOreInfo("Tin"),
    			(new AspectList()).add(Aspect.METAL, 4));
    	
    	addAspectToMaterials(MetallurgyMetals.baseSet.getOreInfo("Bronze"),
    			(new AspectList()).add(Aspect.METAL, 8));
    	
    	addAspectToMaterials(MetallurgyMetals.baseSet.getOreInfo("Hepatizon"),
    			(new AspectList()).add(Aspect.METAL, 12));
    	
    	addAspectToMaterials(MetallurgyMetals.baseSet.getOreInfo("Damascus Steel"),
    			(new AspectList()).add(Aspect.METAL, 16));
    	
    	addAspectToMaterials(MetallurgyMetals.baseSet.getOreInfo("Angmallen"),
    			(new AspectList()).add(Aspect.METAL, 16));
    	
    	addAspectToMaterials(MetallurgyMetals.baseSet.getOreInfo("Manganese"),
    			(new AspectList()).add(Aspect.METAL, 12));
    	
    	addAspectToMaterials(MetallurgyMetals.baseSet.getOreInfo("Steel"),
    			(new AspectList()).add(Aspect.METAL, 16));
    	
    	/** Precious Metals */
    	
    	addAspectToMaterials( MetallurgyMetals.preciousSet.getOreInfo("Zinc"),
    			(new AspectList()).add(Aspect.METAL, 4).add(Aspect.GREED, 2));
    	
    	addAspectToMaterials(MetallurgyMetals.preciousSet.getOreInfo("Brass"),
    			(new AspectList()).add(Aspect.METAL, 6).add(Aspect.GREED, 2));
    	
    	addAspectToMaterials(MetallurgyMetals.preciousSet.getOreInfo("Silver"),
    			(new AspectList()).add(Aspect.METAL, 8).add(Aspect.GREED, 4));
    	
    	addAspectToMaterials(MetallurgyMetals.preciousSet.getOreInfo("Electrum"),
    			(new AspectList()).add(Aspect.METAL, 10).add(Aspect.GREED, 6));
    	
    	addAspectToMaterials(MetallurgyMetals.preciousSet.getOreInfo("Platinum"),
    			(new AspectList()).add(Aspect.METAL, 12).add(Aspect.GREED, 8));
    	
    	/** Nether Metals */
    	
    	addAspectToMaterials(MetallurgyMetals.netherSet.getOreInfo("Ignatius"),
    			(new AspectList()).add(Aspect.METAL, 4).add(Aspect.FIRE, 2));
    	
    	addAspectToMaterials(MetallurgyMetals.netherSet.getOreInfo("Shadow Iron"),
    			(new AspectList()).add(Aspect.METAL, 4).add(Aspect.DARKNESS, 2));
    	
    	addAspectToMaterials(MetallurgyMetals.netherSet.getOreInfo("Lemurite"),
    			(new AspectList()).add(Aspect.METAL, 4).add(Aspect.HEAL, 2));
    	
    	addAspectToMaterials(MetallurgyMetals.netherSet.getOreInfo("Shadow Steel"),
    			(new AspectList()).add(Aspect.METAL, 6).add(Aspect.FIRE, 2));
    	
    	addAspectToMaterials(MetallurgyMetals.netherSet.getOreInfo("Midasium"),
    			(new AspectList()).add(Aspect.METAL, 6).add(Aspect.GREED, 10));
    	
    	addAspectToMaterials(MetallurgyMetals.netherSet.getOreInfo("Vyroxeres"),
    			(new AspectList()).add(Aspect.METAL, 8).add(Aspect.POISON, 4));
    	
    	addAspectToMaterials(MetallurgyMetals.netherSet.getOreInfo("Ceruclase"),
    			(new AspectList()).add(Aspect.METAL, 8).add(Aspect.WATER, 4));
    	
    	addAspectToMaterials(MetallurgyMetals.netherSet.getOreInfo("Adluorite"),
    			(new AspectList()).add(Aspect.METAL, 8).add(Aspect.ORDER, 6));
    	
    	addAspectToMaterials(MetallurgyMetals.netherSet.getOreInfo("Inolashite"),
    			(new AspectList()).add(Aspect.METAL, 12).add(Aspect.ICE, 6));
    	
    	addAspectToMaterials(MetallurgyMetals.netherSet.getOreInfo("Kalendrite"),
    			(new AspectList()).add(Aspect.METAL, 12).add(Aspect.SOUL, 4));
    	
    	addAspectToMaterials(MetallurgyMetals.netherSet.getOreInfo("Amordrine"),
    			(new AspectList()).add(Aspect.METAL, 12).add(Aspect.SOUL, 4)
    			.add(Aspect.GREED, 6));
    	
    	addAspectToMaterials(MetallurgyMetals.netherSet.getOreInfo("Vulcanite"),
    			(new AspectList()).add(Aspect.METAL, 16).add(Aspect.FIRE, 8));
    	
    	addAspectToMaterials(MetallurgyMetals.netherSet.getOreInfo("Sanguinite"),
    			(new AspectList()).add(Aspect.METAL, 24).add(Aspect.FLESH, 8));
    	
    	/** Fantasy Metals */
    	
    	addAspectToMaterials(MetallurgyMetals.fantasySet.getOreInfo("Prometheum"),
    			(new AspectList()).add(Aspect.METAL, 4).add(Aspect.EARTH, 2));
    	
    	addAspectToMaterials(MetallurgyMetals.fantasySet.getOreInfo("Deep Iron"),
    			(new AspectList()).add(Aspect.METAL, 4).add(Aspect.ENTROPY, 2));
    	
    	addAspectToMaterials(MetallurgyMetals.fantasySet.getOreInfo("Infuscolium"),
    			(new AspectList()).add(Aspect.METAL, 4).add(Aspect.ENERGY, 2));
    	
    	addAspectToMaterials(MetallurgyMetals.fantasySet.getOreInfo("Black Steel"),
    			(new AspectList()).add(Aspect.METAL, 6).add(Aspect.ENTROPY, 2)
    			.add(Aspect.ENERGY, 2));
    	
    	addAspectToMaterials(MetallurgyMetals.fantasySet.getOreInfo("Oureclase"),
    			(new AspectList()).add(Aspect.METAL, 6).add(Aspect.ENERGY, 4));
    	
    	addAspectToMaterials(MetallurgyMetals.fantasySet.getOreInfo("Astral Silver"),
    			(new AspectList()).add(Aspect.METAL, 6).add(Aspect.GREED, 4));
    	
    	addAspectToMaterials(MetallurgyMetals.fantasySet.getOreInfo("Carmot"),
    			(new AspectList()).add(Aspect.METAL, 8).add(Aspect.GREED, 6));
    	
    	addAspectToMaterials(MetallurgyMetals.fantasySet.getOreInfo("Mithril"),
    			(new AspectList()).add(Aspect.METAL, 10).add(Aspect.MOTION, 4));
    	
    	addAspectToMaterials(MetallurgyMetals.fantasySet.getOreInfo("Rubracium"),
    			(new AspectList()).add(Aspect.METAL, 10).add(Aspect.VOID, 4));
    	
    	addAspectToMaterials(MetallurgyMetals.fantasySet.getOreInfo("Quicksilver"),
    			(new AspectList()).add(Aspect.METAL, 14).add(Aspect.MOTION, 4)
    			.add(Aspect.VOID, 4));
    	
    	addAspectToMaterials(MetallurgyMetals.fantasySet.getOreInfo("Haderoth"),
    			(new AspectList()).add(Aspect.METAL, 14).add(Aspect.MOTION, 4)
    			.add(Aspect.GREED, 4));
    	
    	addAspectToMaterials(MetallurgyMetals.fantasySet.getOreInfo("Orichalcum"),
    			(new AspectList()).add(Aspect.METAL, 12).add(Aspect.LIFE, 6));
    	
    	addAspectToMaterials(MetallurgyMetals.fantasySet.getOreInfo("Celenegil"),
    			(new AspectList()).add(Aspect.METAL, 18).add(Aspect.LIFE, 6)
    			.add(Aspect.GREED, 6));
    	
    	addAspectToMaterials(MetallurgyMetals.fantasySet.getOreInfo("Adamantine"),
    			(new AspectList()).add(Aspect.METAL, 16).add(Aspect.MIND, 8));
    	
    	addAspectToMaterials(MetallurgyMetals.fantasySet.getOreInfo("Atlarus"),
    			(new AspectList()).add(Aspect.METAL, 16).add(Aspect.FLESH, 8));
    	
    	addAspectToMaterials(MetallurgyMetals.fantasySet.getOreInfo("Tartarite"),
    			(new AspectList()).add(Aspect.METAL, 24).add(Aspect.MIND, 6)
    			.add(Aspect.FLESH, 6));
    	
    	/** Utility */
    	
    	addAspectToMaterials(MetallurgyMetals.utilitySet.getOreInfo("Phosphorite"),
    			(new AspectList()).add(Aspect.ENERGY, 2).add(Aspect.FIRE, 2));
    	
    	addAspectToMaterials(MetallurgyMetals.utilitySet.getOreInfo("Sulfur"),
    			(new AspectList()).add(Aspect.ENERGY, 2).add(Aspect.FIRE, 2));
    	
    	addAspectToMaterials(MetallurgyMetals.utilitySet.getOreInfo("Saltpeter"),
    			(new AspectList()).add(Aspect.ENERGY, 2).add(Aspect.PLANT, 2));
    	
    	addAspectToMaterials(MetallurgyMetals.utilitySet.getOreInfo("Magnesium"),
    			(new AspectList()).add(Aspect.ENERGY, 2).add(Aspect.PLANT, 2));
    	
    	addAspectToMaterials(MetallurgyMetals.utilitySet.getOreInfo("Bitumen"),
    			(new AspectList()).add(Aspect.ENERGY, 2).add(Aspect.SLIME, 2));
    	
    	addAspectToMaterials(MetallurgyMetals.utilitySet.getOreInfo("Potash"),
    			(new AspectList()).add(Aspect.ENERGY, 2).add(Aspect.PLANT, 2));
    	
    	/** Ender Metal */
    	
    	addAspectToMaterials(MetallurgyMetals.enderSet.getOreInfo("Eximite"),
    			(new AspectList()).add(Aspect.METAL, 12).add(Aspect.ELDRITCH, 6));
    	
    	addAspectToMaterials(MetallurgyMetals.enderSet.getOreInfo("Meutoite"),
    			(new AspectList()).add(Aspect.METAL, 12).add(Aspect.VOID, 6));
    	
    	addAspectToMaterials(MetallurgyMetals.enderSet.getOreInfo("Eximite"),
    			(new AspectList()).add(Aspect.METAL, 18).add(Aspect.ELDRITCH, 4)
    			.add(Aspect.VOID, 4));
    	
    	
    }
    
    private static void addAspectToMaterials (OreInfo oreInfo, AspectList aspectList )
    {
    	ItemStack ore = oreInfo.getOre();
    	ItemStack block = oreInfo.getBlock();
    	ItemStack ingot = oreInfo.getIngot();
    	ItemStack drop = oreInfo.getDrop();
    	ItemStack dust = oreInfo.getDust();
    	
    	if (ore != null)
    	{
    		ThaumcraftApi.registerObjectTag(ore.itemID, ore.getItemDamage(), aspectList);
    	}
        
        if (block != null)
        {
        	ThaumcraftApi.registerObjectTag(block.itemID, block.getItemDamage(), aspectList);
        }
        
        if (ingot != null)
        {
        	ThaumcraftApi.registerObjectTag(ingot.itemID, ingot.getItemDamage(), aspectList);
        }
        
        if (drop != null)
        {
        	ThaumcraftApi.registerObjectTag(drop.itemID, drop.getItemDamage(), aspectList);
        }
        
        if (dust != null)
        {
        	ThaumcraftApi.registerObjectTag(dust.itemID, dust.getItemDamage(), aspectList);
        }
        
    }

    public static void init()
    {
        if (Loader.isModLoaded("Thaumcraft"))
        {
            addAspects();
        }
    }
}
