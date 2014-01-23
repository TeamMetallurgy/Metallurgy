package rebelkeithy.mods.metallurgy.integration;

import net.minecraft.item.ItemStack;
import rebelkeithy.mods.metallurgy.machines.crusher.CrusherRecipes;
import appeng.api.Materials;
import cpw.mods.fml.common.Loader;

public class AppliedEnergestics {

	public static void init() {
		if (Loader.isModLoaded("AppliedEnergistics"))
		{
			int amount = 1;
			CrusherRecipes.addCrushing(Materials.matQuartz.copy().itemID, Materials.matQuartz.copy().getItemDamage(), new ItemStack(Materials.matQuartzDust.copy().getItem(), amount, Materials.matQuartzDust.copy().getItemDamage()));
		}
		
	}

}
