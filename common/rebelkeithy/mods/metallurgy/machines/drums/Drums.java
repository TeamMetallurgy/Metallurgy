package rebelkeithy.mods.metallurgy.machines.drums;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import rebelkeithy.mods.metallurgy.api.MetallurgyAPI;

public enum Drums
{
    Copper("Copper", "metallurgy:drums/copper",  2000),
    Bronze("Bronze", "metallurgy:drums/bronze", 4000),
    Iron("Iron", "metallurgy:drums/iron", 8000),
    Steel("Steel", "metallurgy:drums/steel", 16000);

    private String localizedName;
    private String texture;
    private int capacity;
    private String material;

    Drums(String material, String texture, int capacity)
    {
        this.material = material;
        this.localizedName = material + " Drum";
        this.texture = texture;
        this.capacity = capacity;
    }

    public String getLocalizedName()
    {
        return this.localizedName;
    }

    public String getTexture()
    {
        return this.texture;
    }

    public int getCapacity()
    {
        return this.capacity;
    }

}
