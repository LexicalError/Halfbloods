package lexicalerror.halfbloods.abilities;

import net.minecraft.entity.player.PlayerEntity;

public interface Ability {
    String getName();
    void apply(PlayerEntity player);
}


