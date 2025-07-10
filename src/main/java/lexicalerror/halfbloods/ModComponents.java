package lexicalerror.halfbloods;

import lexicalerror.halfbloods.components.PlayerComponent;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class ModComponents implements EntityComponentInitializer {
    public static final ComponentKey<PlayerComponent> PLAYER_COMPONENT = ComponentRegistry.getOrCreate(Identifier.of("halfbloods", "player_component"), PlayerComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(PLAYER_COMPONENT, PlayerComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
