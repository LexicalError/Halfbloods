package lexicalerror.halfbloods.entities;

import lexicalerror.halfbloods.Halfbloods;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.WolfEntityRenderer;

public class ModEntitiesClient {
    public static void registerModEntityRenderers() {
        Halfbloods.LOGGER.info("Registering renderers for: " + Halfbloods.MOD_ID);

        EntityRendererRegistry.register(ModEntities.ARCADIAN_HOUND, WolfEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.MOONLIT_ARROW, ArrowEntityRenderer::new);
    }
}
