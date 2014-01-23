package rebelkeithy.mods.metallurgy.integration;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import rebelkeithy.mods.metallurgy.api.IMetalSet;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import rebelkeithy.mods.metallurgy.api.MetallurgyAPI;
import rebelkeithy.mods.metallurgy.vanilla.MetallurgyVanilla;

import com.xcompwiz.mystcraft.api.MystAPI;
import com.xcompwiz.mystcraft.api.internals.BlockCategory;
import com.xcompwiz.mystcraft.api.internals.BlockDescriptor;
import com.xcompwiz.mystcraft.api.internals.IInstabilityFormula;
import com.xcompwiz.mystcraft.api.symbol.IAgeController;
import com.xcompwiz.mystcraft.api.symbol.IAgeSymbol;
import com.xcompwiz.mystcraft.api.symbol.ModifierUtils;

public class MystcraftIntegration {

	public static class ModifierBlock implements IAgeSymbol {

		private String[] words;
		private BlockDescriptor blockDescriptor;
		private float grammarWeight;

		public ModifierBlock(BlockDescriptor blockDescriptor, String word, float grammarWeight) {
			this.blockDescriptor = blockDescriptor;
			this.grammarWeight = grammarWeight;
			setWords(new String[]{"Transform", "Constraint", word, getBlockAsItem().getUnlocalizedName()});
			createRules();
		}

		public void createRules() {
			for(BlockCategory category : BlockCategory.getCategories()) {
				if(blockDescriptor.isUsable(category)) {
					MystAPI.grammar.registerGrammarRule(category.getGrammarBinding(), grammarWeight, identifier());
				}
			}
		}

		public void setWords(String[] words) {
			this.words = words;
		}

		public IAgeSymbol setRarity(float f) {
			MystAPI.symbolValues.setSymbolItemRarity(this, f);
			return this;
		}

		public int instabilityModifier(int count) {
			return 0;
		}

		public final String[] getPoem() {
			return words;
		}

		public void registerLogic(IAgeController controller, long seed) {
			ModifierUtils.pushBlock(controller, blockDescriptor);
		}

		public String identifier() {
			return "ModMat_" + getBlockAsItem().getUnlocalizedName();
		}

		public String displayName() {
			String str = getBlockAsItem().getDisplayName();
			if(str.endsWith("Block")) {
				return str;
			}
			return str + " Block";
		}

		private ItemStack getBlockAsItem() {
			Block block = Block.blocksList[blockDescriptor.id];
			if(block == null) {
				throw new RuntimeException("Block id " + blockDescriptor.id + " is not a valid block!");
			}
			ItemStack itemstack = new ItemStack(block, 1, blockDescriptor.metadata);
			if(itemstack.getItem() == null) {
				throw new RuntimeException("Invalid item form for block " + block.getUnlocalizedName() + "with metadata " + blockDescriptor.metadata);
			}
			return itemstack;
		}
	}

	public static void init() {
		for(int i = 0; i < MetallurgyAPI.getMetalSetNames().length; i++) {
			IMetalSet metalSet = MetallurgyAPI.getMetalSet(MetallurgyAPI.getMetalSetNames()[i]);
			for(IOreInfo oreInfo : metalSet.getOreList().values()) {
				if(oreInfo.getOre() != null) {
					MystAPI.symbol.registerSymbol(createBlockSymbol("Terrain", 0.18F, 0.18F, oreInfo.getOre().itemID, (byte) oreInfo.getOre().getItemDamage(), null, new BlockCategory[]{BlockCategory.TERRAIN, BlockCategory.STRUCTURE, BlockCategory.SOLID}));
				}
			}
		}
		IMetalSet metalSet = MetallurgyVanilla.vanillaSet;
		for(IOreInfo oreInfo : metalSet.getOreList().values()) {
			if(oreInfo.getOre() != null) {
				MystAPI.symbol.registerSymbol(createBlockSymbol("Terrain", 0.18F, 0.18F, oreInfo.getOre().itemID, (byte) oreInfo.getOre().getItemDamage(), null, new BlockCategory[]{BlockCategory.TERRAIN, BlockCategory.STRUCTURE, BlockCategory.SOLID}));
			}
		}
	}

	public static IAgeSymbol createBlockSymbol(String word, float grammarweight, float itemrarity, int blockId, byte metadata, IInstabilityFormula formulaLinear, BlockCategory[] cats) {
		BlockDescriptor block = new BlockDescriptor((short) blockId, metadata, formulaLinear);
		for(BlockCategory cat : cats) {
			block.setUsable(cat, true);
		}
		return new ModifierBlock(block, word, grammarweight).setRarity(itemrarity);
	}
}
