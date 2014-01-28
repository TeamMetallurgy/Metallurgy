package rebelkeithy.mods.metallurgy.core.metalsets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;

import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.Files;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenMetals implements IWorldGenerator
{
    private int[] generation;
    private String dimensions;
    private WorldGenMinable mineable;

    private int blockId;
    private int metaId;
    private static HashMap<Integer, ArrayList<String>> worldGeneratored = new HashMap<Integer, ArrayList<String>>();

    public WorldGenMetals(int blockId, int metaId, int[] generationInfo, String dimensionsInfo)
    {

        this.generation = generationInfo;
        this.dimensions = dimensionsInfo;

        int targetId = Block.stone.blockID;

        if (vaildDimension(-1))
        {
            targetId = Block.netherrack.blockID;
        }

        if (vaildDimension(1))
        {
            targetId = Block.whiteStone.blockID;
        }

        this.blockId = blockId;
        this.metaId = metaId;

        this.mineable = new WorldGenMinable(blockId, metaId, this.generation[1], targetId);
    }

    public WorldGenMetals(int blockID, int oreMeta, int[] generationInfo, String[] dimensions2)
    {
        this(blockID, oreMeta, generationInfo, Joiner.on(" ").join(dimensions2));
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        int dimId = world.provider.dimensionId;

        if (!vaildDimension(dimId)) { return; }

        if (random.nextInt(100) > this.generation[4]) { return; }

        for (int i = 0; i <= this.generation[0]; i++)
        {
            int initX = (chunkX * 16) + random.nextInt(16);
            int initY = this.generation[2] + random.nextInt(this.generation[3] - this.generation[2]);
            int initZ = (chunkZ * 16) + random.nextInt(16);

            boolean boolean1 = MetallurgyCore.config.get("Debugs", "DumpOreGen", false).getBoolean(false);

            if (MetallurgyCore.config.hasChanged())
            {
                MetallurgyCore.config.save();
            }

            if (boolean1)
            {
                ItemStack itemStack = new ItemStack(this.blockId, 1, this.metaId);
                Block blockOld = Block.blocksList[world.getBlockId(initX, initY, initZ)];

                ArrayList<String> list = WorldGenMetals.worldGeneratored.get(dimId);

                if (list == null)
                {
                    list = new ArrayList<String>();
                    list.add("new,old,x,y,z");
                }

                if (blockOld != null)
                {
                    String name = null;
                    final int oreID = OreDictionary.getOreID(itemStack);
                    if (oreID != -1)
                    {
                        name = OreDictionary.getOreName(oreID);
                    }
                    String string = name + "," + blockOld.getLocalizedName() + "," + initX + "," + initY + "," + initZ;
                    list.add(string);

                    WorldGenMetals.worldGeneratored.put(dimId, list);
                }
            }
            this.mineable.generate(world, random, initX, initY, initZ);
        }
    }

    public static void dumpRegistry(int dim, String minecraftDir)
    {

        if (worldGeneratored == null || worldGeneratored.isEmpty()) { return; }

        StringBuilder builder = new StringBuilder();
        ArrayList<String> list = worldGeneratored.get(dim);

        for (String string : list)
        {
            builder.append(string);
            builder.append("\r\n");
        }

        File f = new File(minecraftDir, "metallurgyWorldGen" + dim + ".csv");
        try
        {
            Files.write((builder.toString()), f, Charsets.UTF_8);
            FMLLog.log(Level.INFO, "Dumped oregen data to %s", f.getAbsolutePath());
        }
        catch (IOException e)
        {
            FMLLog.log(Level.SEVERE, e, "Failed to write oregen data to %s", f.getAbsolutePath());
        }
    }

    private boolean vaildDimension(int dimensionId)
    {

        boolean vaild = false;
        String[] dims = this.dimensions.split(" ");

        if (dims.length < 1) { return false; }

        for (String dim : dims)
        {
            if (dim.contains("-"))
            {
                String[] range = dim.split("-");

                if (range.length > 1)
                {

                    if (!(range[0].compareTo("") == 0))
                    {
                        int low = Integer.parseInt(range[0]);
                        int high = Integer.parseInt(range[1]);

                        if ((dimensionId >= low) && (dimensionId <= high))
                        {
                            vaild = true;
                            break;
                        }
                    }
                    else
                    {
                        int canidateDim = -1 * Integer.parseInt(range[1]);

                        if (canidateDim == dimensionId)
                        {
                            vaild = true;
                            break;
                        }
                    }

                }
            }
            else
            {
                int canidateDim = Integer.parseInt(dim);

                if (canidateDim == dimensionId)
                {
                    vaild = true;
                    break;
                }
            }
        }

        return vaild;
    }
}
