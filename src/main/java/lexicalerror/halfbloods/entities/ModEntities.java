package lexicalerror.halfbloods.entities;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import lexicalerror.halfbloods.Halfbloods;

public class ModEntities {
    public static final EntityType<ArcadianHoundsEntity> ARCADIAN_HOUND = register("arcadian_hounds", ArcadianHoundsEntity::new);
    public static final EntityType<MoonlitArrowEntity> MOONLIT_ARROW = register("moonlit_arrow", MoonlitArrowEntity::new);

    private static <T extends Entity> EntityType<T> register(String id, EntityType.EntityFactory<T> factory){
        return Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(Halfbloods.MOD_ID, id),
                EntityType.Builder.create(factory, SpawnGroup.MISC)
                        .dropsNothing()
                        .dimensions(1.0f, 1.0f)
                        .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(Halfbloods.MOD_ID, id)))
        );
    }

    public static void registerModEntities() {
        Halfbloods.LOGGER.info("Registering ModEntities for: " + Halfbloods.MOD_ID);
        FabricDefaultAttributeRegistry.register(ARCADIAN_HOUND, ArcadianHoundsEntity.createCustomWolfAttributes());
    }
}
