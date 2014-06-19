package rebelkeithy.mods.metallurgy.machines.forge;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileEntityNetherForge extends TileEntity implements ISidedInventory, IFluidHandler
{
    /**
     * The ItemStacks that hold the items currently being used in the furnace
     */
    private ItemStack[] furnaceItemStacks = new ItemStack[2];

    /** The number of ticks that the furnace will keep burning */
    public int furnaceBurnTime;

    /**
     * The number of ticks that a fresh copy of the currently-burning item would
     * keep the furnace burning for
     */
    public int currentItemBurnTime;

    /** The number of ticks that the current item has been cooking for */
    public int furnaceCookTime;
    public float fuelMultiplier;
    public int furnaceTimeBase;
    public int fuelPerItem;
    public boolean isBurning;
    public int direction;
    private int ticksSinceSync;

    protected FluidTank tank;

    public TileEntityNetherForge()
    {
        this.tank = new FluidTank(10000);

        this.direction = 0;
        this.fuelPerItem = 50;
        this.furnaceTimeBase = 200;
        this.fuelMultiplier = 0.5F;
        this.furnaceCookTime = 0;
        this.currentItemBurnTime = 0;
        this.furnaceBurnTime = 0;
    }

    public void addFuelBucket()
    {

        this.fill(ForgeDirection.UNKNOWN, new FluidStack(FluidRegistry.LAVA, 1000), true);
        // sync();
        if (!worldObj.isRemote)
        {
            sendPacket();
        }
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack par2ItemStack, int side)
    {
        return slot == 1;
    }

    @Override
    public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return isItemValidForSlot(par1, par2ItemStack);
    }

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item,
     * destination stack isn't full, etc.
     */
    private boolean canSmelt()
    {
        final ItemStack var1 = FurnaceRecipes.smelting().getSmeltingResult(furnaceItemStacks[0]);
        if (var1 == null) { return false; }
        if (furnaceItemStacks[1] == null) { return true; }
        if (!furnaceItemStacks[1].isItemEqual(var1)) { return false; }
        final int result = furnaceItemStacks[1].stackSize + var1.stackSize;
        return result <= getInventoryStackLimit() && result <= var1.getMaxStackSize();
    }

    @Override
    public void closeChest()
    {
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeCustomNBT(tagCompound);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 3, tagCompound);
    }

    private void writeCustomNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setShort("CookTime", (short) furnaceCookTime);
        tagCompound.setShort("Direction", (short) direction);
        tagCompound.setShort("TimeBase", (short) furnaceTimeBase);
        this.tank.writeToNBT(tagCompound);
        final NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < furnaceItemStacks.length; ++var3)
        {
            if (furnaceItemStacks[var3] != null)
            {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                furnaceItemStacks[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        tagCompound.setTag("Items", var2);
    }

    private void readCustomNBT(NBTTagCompound tagCompound)
    {
        final NBTTagList var2 = tagCompound.getTagList("Items");
        furnaceItemStacks = new ItemStack[getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            final NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
            final byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < furnaceItemStacks.length)
            {
                furnaceItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
        this.tank = tank.readFromNBT(tagCompound);

        furnaceCookTime = tagCompound.getShort("CookTime");
        direction = tagCompound.getShort("Direction");
        furnaceTimeBase = tagCompound.getShort("TimeBase");
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
    {
        readCustomNBT(pkt.data);
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of
     * the second int arg. Returns the new stack.
     */
    @Override
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (furnaceItemStacks[par1] != null)
        {
            ItemStack var3;

            if (furnaceItemStacks[par1].stackSize <= par2)
            {
                var3 = furnaceItemStacks[par1];
                furnaceItemStacks[par1] = null;
                return var3;
            }
            else
            {
                var3 = furnaceItemStacks[par1].splitStack(par2);

                if (furnaceItemStacks[par1].stackSize == 0)
                {
                    furnaceItemStacks[par1] = null;
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    // @Override
    // public FluidStack drain(int maxDrain, boolean doDrain)
    // {
    // int amount = maxDrain;
    //
    // if (amount > fuel)
    // {
    // amount = fuel;
    // }
    //
    // if (doDrain)
    // {
    // fuel -= amount;
    // }
    //
    // return new FluidStack(FluidRegistry.LAVA, amount);
    // }

    // @Override
    // public int fill(FluidStack resource, boolean doFill)
    // {
    // int amount = resource.amount;
    // if (amount > maxFuel - fuel)
    // {
    // amount = maxFuel - fuel;
    // }
    //
    // if (doFill)
    // {
    // fuel += amount;
    // }
    //
    // return amount;
    // }

    /**
     * Get the size of the side inventory.
     */
    @Override
    public int[] getAccessibleSlotsFromSide(int par1)
    {
        if (par1 == 1)
        {
            return new int[] { 0, 1 };
        }
        else if (par1 == 2)
        {
            return new int[] { 1 };
        }
        else
        {
            return new int[] { 0, 1 };
        }
    }

    /**
     * Returns an integer between 0 and the passed value representing how close
     * the current item is to being completely cooked
     */
    public int getCookProgressScaled(int par1)
    {
        return furnaceCookTime * par1 / furnaceTimeBase;
    }

    public int getDirection()
    {
        return direction;
    }

    public int getFuelScaled(int scale)
    {
        final int retValue = tank.getFluidAmount() * scale / tank.getCapacity();
        if (retValue > scale)
        {
            return scale;
        }
        else
        {
            return retValue;
            // return fuel * scale / maxFuel;
        }
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be
     * 64, possibly will be extended. *Isn't this more of a set than a get?*
     */
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Returns the name of the inventory.
     */
    @Override
    public String getInvName()
    {
        return "container.furnace";
    }

    public int getScaledFuel(int i)
    {
        final int scaledFuel = MathHelper.ceiling_float_int(i * (tank.getFluidAmount() / (float) tank.getCapacity()));
        return scaledFuel >= i ? i : scaledFuel;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory()
    {
        return furnaceItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(int par1)
    {
        return furnaceItemStacks[par1];
    }

    /**
     * When some containers are closed they call this on each slot, then drop
     * whatever it returns as an EntityItem - like when you close a workbench
     * GUI.
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (furnaceItemStacks[par1] != null)
        {
            final ItemStack var2 = furnaceItemStacks[par1];
            furnaceItemStacks[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    public int getType()
    {
        final int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        return meta < 8 ? meta : meta - 8;
    }

    /**
     * Returns true if the furnace is currently burning
     */
    public boolean isBurning()
    {
        return tank.getFluidAmount() > 0 && canSmelt();
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return false;
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring
     * stack size) into the given slot.
     */
    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
        return par1 == 0 ? true : false;
    }

    /*
     * @Override public int getStartInventorySide(ForgeDirection side) { if
     * (side == ForgeDirection.DOWN) return 1; if (side == ForgeDirection.UP)
     * return 0; return 2; }
     * @Override public int getSizeInventorySide(ForgeDirection side) { return
     * 1; }
     */

    /**
     * Do not make give this method the name canInteractWith because it clashes
     * with Container
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : par1EntityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openChest()
    {
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        readCustomNBT(tag);

    }

    /*
     * @Override public int fill(ForgeDirection from, LiquidStack resource,
     * boolean doFill) { if(resource.itemID != Block.lavaStill.blockID) return
     * 0; if(fuel < maxFuel) { int res = 0; if(fuel + resource.amount <=
     * maxFuel) { res = resource.amount; fuel += resource.amount; } else { res =
     * maxFuel - fuel; fuel = maxFuel; } //sendPacket(); sync(); return res; }
     * else { return 0; } }
     * @Override public int fill(int tankIndex, LiquidStack resource, boolean
     * doFill) { return 0; }
     * @Override public LiquidStack drain(ForgeDirection from, int maxDrain,
     * boolean doDrain) { return null; }
     * @Override public LiquidStack drain(int tankIndex, int maxDrain, boolean
     * doDrain) { return null; }
     */

    /*
     * @Override public int addItem(ItemStack stack, boolean doAdd,
     * ForgeDirection from) { int slot = 0; if(furnaceItemStacks[slot] == null)
     * { if(doAdd) furnaceItemStacks[slot] = stack; return stack.stackSize; }
     * else { if(furnaceItemStacks[slot].itemID == stack.itemID &&
     * furnaceItemStacks[slot].getItemDamage() == stack.getItemDamage()) {
     * if(furnaceItemStacks[slot].stackSize + stack.stackSize >
     * stack.getMaxStackSize()) { int amount = stack.getMaxStackSize() -
     * furnaceItemStacks[1].stackSize; if(doAdd)
     * furnaceItemStacks[slot].stackSize =
     * furnaceItemStacks[1].getMaxStackSize(); return amount; } else { if(doAdd)
     * furnaceItemStacks[slot].stackSize += stack.stackSize; return
     * stack.stackSize; } } else { return 0; } } }
     */

    /*
     * @Override public ItemStack[] extractItem(boolean doRemove, ForgeDirection
     * from, int maxItemCount) { if(furnaceItemStacks[1] != null) { int amount =
     * (furnaceItemStacks[1].stackSize < maxItemCount) ?
     * furnaceItemStacks[1].stackSize : maxItemCount; ItemStack[] returnStack =
     * { new ItemStack(furnaceItemStacks[1].itemID, amount,
     * furnaceItemStacks[1].getItemDamage()) }; if(doRemove) decrStackSize(1,
     * amount); return returnStack; } return null; }
     */

    /*
     * @Override public ILiquidTank[] getTanks(ForgeDirection direction) {
     * return new LiquidTank[] { new LiquidTank(Block.lavaStill.blockID, fuel,
     * maxFuel) }; }
     * @Override public ILiquidTank getTank(ForgeDirection direction,
     * LiquidStack type) { if(type.itemID == Block.lavaStill.blockID) return new
     * LiquidTank(Block.lavaStill.blockID, fuel, maxFuel); else return null; }
     */

    public void removeFuelBucket()
    {
        this.tank.drain(1000, true);

        if (!worldObj.isRemote)
        {
            sendPacket();
        }
    }

    public void sendPacket()
    {
        if (worldObj.isRemote) { return; }

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final DataOutputStream dos = new DataOutputStream(bos);
        try
        {
            dos.writeShort(50);
            dos.writeInt(xCoord);
            dos.writeInt(yCoord);
            dos.writeInt(zCoord);
            dos.writeInt(direction);
            dos.writeInt(furnaceTimeBase);
            dos.writeInt(furnaceBurnTime);
            dos.writeInt(furnaceCookTime);
        }
        catch (final IOException e)
        {
            // UNPOSSIBLE?
        }
        final Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "M3Machines";
        packet.data = bos.toByteArray();
        packet.length = bos.size();
        packet.isChunkDataPacket = true;

        if (packet != null)
        {
            PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 16, worldObj.provider.dimensionId, packet);
        }
    }

    public void setDirection(int par1)
    {
        direction = par1;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be
     * crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        furnaceItemStacks[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
        {
            par2ItemStack.stackSize = getInventoryStackLimit();
        }
    }

    public void setMaxBuckets(int buckets)
    {
        this.tank.setCapacity(buckets * 1000);
    }

    public void setSpeed(int var1)
    {
        furnaceTimeBase = var1;
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted
     * item in the furnace result stack
     */
    public void smeltItem()
    {
        if (canSmelt())
        {
            final ItemStack var1 = FurnaceRecipes.smelting().getSmeltingResult(furnaceItemStacks[0]);

            if (furnaceItemStacks[1] == null)
            {
                furnaceItemStacks[1] = var1.copy();
                --furnaceItemStacks[0].stackSize;
            }
            else if (furnaceItemStacks[1].isItemEqual(var1) && ((furnaceItemStacks[1].stackSize + var1.stackSize) <= furnaceItemStacks[1].getMaxStackSize()))
            {
                furnaceItemStacks[1].stackSize += var1.stackSize;
                --furnaceItemStacks[0].stackSize;
            }

            if (furnaceItemStacks[0].stackSize <= 0)
            {
                furnaceItemStacks[0] = null;
            }
        }
    }

    public void sync()
    {
        if (worldObj != null)
        {
            final int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 1, direction);
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 2, furnaceTimeBase);
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 3, this.tank.getFluidAmount());
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 4, this.tank.getCapacity());
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 5, furnaceCookTime);
        }
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses,
     * e.g. the mob spawner uses this to count ticks and creates a new spawn
     * inside its implementation.
     */
    @Override
    public void updateEntity()
    {
        furnaceTimeBase = 20;
        if (++ticksSinceSync % 80 == 0)
        {
            sendPacket();
        }

        boolean checkBurning = false;
        final boolean prevIsBurning = isBurning;

        if (canSmelt() && this.tank.getFluidAmount() > 0)
        {
            ++furnaceCookTime;
            isBurning = true;

            if (furnaceCookTime == furnaceTimeBase)
            {
                furnaceCookTime = 0;
                this.tank.drain(fuelPerItem, true);
                smeltItem();
                checkBurning = true;
            }
        }
        else
        {
            furnaceCookTime = 0;
            isBurning = false;
        }

        if (prevIsBurning != isBurning)
        {
            checkBurning = true;
        }

        if (checkBurning)
        {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            onInventoryChanged();
            // int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
            // sync();
            sendPacket();
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        writeCustomNBT(tag);
    }

    /* Here Starts IFluidHandler */

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        return this.tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if (resource == null || !resource.isFluidEqual(tank.getFluid())) return null;

        return this.drain(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        return this.tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        return tank.getFluid() == null || tank.getFluid().getFluid() == FluidRegistry.LAVA;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return tank.getFluid() == null || tank.getFluid().getFluid() == FluidRegistry.LAVA;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[] { this.tank.getInfo() };
    }

    public int getTankCapacity()
    {
        return this.tank.getCapacity();
    }

    public int getTankFluidAmount()
    {
        return this.tank.getFluidAmount();
    }

}
