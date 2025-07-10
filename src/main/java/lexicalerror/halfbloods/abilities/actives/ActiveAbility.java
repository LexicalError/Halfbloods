package lexicalerror.halfbloods.abilities.actives;

import lexicalerror.halfbloods.abilities.Ability;
import net.minecraft.entity.player.PlayerEntity;

public interface ActiveAbility extends Ability {
    boolean canActivate(PlayerEntity player);
    void activate(PlayerEntity player);
}
