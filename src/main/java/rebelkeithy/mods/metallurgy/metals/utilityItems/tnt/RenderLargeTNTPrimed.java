package rebelkeithy.mods.metallurgy.metals.utilityItems.tnt;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import rebelkeithy.mods.metallurgy.metals.MetallurgyMetals;

public class RenderLargeTNTPrimed extends Render
{
    private RenderBlocks blockRenderer = new RenderBlocks();

    public RenderLargeTNTPrimed()
    {
        this.shadowSize = 0.5F;
    }

    public void renderPrimedTNT(EntityLargeTNTPrimed entityLargeTNTPrimed, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) par2, (float) par4, (float) par6);
        float f2;

        if ((float) entityLargeTNTPrimed.fuse - par9 + 1.0F < 10.0F)
        {
            f2 = 1.0F - ((float) entityLargeTNTPrimed.fuse - par9 + 1.0F) / 10.0F;

            if (f2 < 0.0F)
            {
                f2 = 0.0F;
            }

            if (f2 > 1.0F)
            {
                f2 = 1.0F;
            }

            f2 *= f2;
            f2 *= f2;
            float f3 = 1.0F + f2 * 0.3F;
            GL11.glScalef(f3, f3, f3);
        }

        f2 = (1.0F - ((float) entityLargeTNTPrimed.fuse - par9 + 1.0F) / 100.0F) * 0.8F;
        this.bindEntityTexture(entityLargeTNTPrimed);
        this.blockRenderer.renderBlockAsItem(MetallurgyMetals.largeTNT, 0, entityLargeTNTPrimed.getBrightness(par9));

        if (entityLargeTNTPrimed.fuse / 5 % 2 == 0)
        {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f2);
            this.blockRenderer.renderBlockAsItem(MetallurgyMetals.largeTNT, 0, 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }

        GL11.glPopMatrix();
    }

    protected ResourceLocation getTextureLocation(EntityLargeTNTPrimed entityLargeTNT)
    {
        return TextureMap.locationBlocksTexture;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getTextureLocation((EntityLargeTNTPrimed) entity);
    }

    @Override
    public void doRender(Entity entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderPrimedTNT((EntityLargeTNTPrimed) entity, par2, par4, par6, par8, par9);
    }
}
