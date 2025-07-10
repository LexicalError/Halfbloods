package lexicalerror.halfbloods.entities.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.WolfEntityRenderer;
import net.minecraft.client.render.entity.state.WolfEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ArcadianHoundsEntityRenderer extends WolfEntityRenderer {

    public ArcadianHoundsEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void scale(WolfEntityRenderState state, MatrixStack matrices) {
        float scale = 1.7f;
        matrices.scale(scale, scale, scale);
    }

    @Override
    public Identifier getTexture(WolfEntityRenderState state) {
        return super.getTexture(state);
    }
}
