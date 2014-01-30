package rebelkeithy.mods.metallurgy.machines.drums;

import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ItemFluidContainer;

public class ItemDrum extends ItemFluidContainer
{

    private String localizedName;
    private String texture;

    public ItemDrum(int itemID, Drums type)
    {
        super(itemID, type.getCapacity());
        this.localizedName = type.getLocalizedName();
        this.setCreativeTab(MetallurgyMachines.machineTab);
        this.texture = type.getTexture();
    }

    @Override
    public void registerIcons(IconRegister register)
    {
        register.registerIcon(this.texture);
    }

    public int getRemainingCapacity(ItemStack container)
    {
        FluidStack fluid = getFluid(container);

        if (fluid == null) { return this.capacity; }

        return this.capacity - fluid.amount;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

        if (movingobjectposition == null)
        {
            return stack;
        }
        else
        {
            FillBucketEvent event = new FillBucketEvent(player, stack, world, movingobjectposition);
            if (MinecraftForge.EVENT_BUS.post(event)) { return stack; }

            if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
            {
                int x = movingobjectposition.blockX;
                int y = movingobjectposition.blockY;
                int z = movingobjectposition.blockZ;

                if (!world.canMineBlock(player, x, y, z)) { return stack; }

                Block block = Block.blocksList[world.getBlockId(x, y, z)];

                Fluid fluid = FluidRegistry.lookupFluidForBlock(block);

                if (fluid != null)
                {
                    FluidStack fluidStack = new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME);
                    int filled = this.fill(stack, fluidStack, false);

                    if (filled != 0 && filled <= getRemainingCapacity(stack))
                    {
                        this.fill(stack, fluidStack, true);
                        world.setBlockToAir(x, y, z);
                    }
                }

            }

            return stack;
        }
    }
}
