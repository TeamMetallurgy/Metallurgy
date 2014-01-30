package rebelkeithy.mods.metallurgy.machines.drums;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;

public class DrumRender implements IItemRenderer
{

    private String texture;

    public DrumRender(String texture)
    {
        this.texture = texture;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {

        switch (type)
        {
            case INVENTORY:
                renderInventoryItem(item, (RenderBlocks) data[0]);
                break;
        }
    }

    private void renderInventoryItem(ItemStack item, RenderBlocks renderBlocks)
    {
        Icon icon = item.getIconIndex();

        float f = icon.getMinU();
        float f1 = icon.getMaxU();
        float f2 = icon.getMinV();
        float f3 = icon.getMaxV();
        ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
    }

}
