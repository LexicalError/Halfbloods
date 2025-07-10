package lexicalerror.halfbloods.abilities.passives;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class HuntressPassiveAbility implements PassiveAbility {
    @Override
    public String getName() {
        return "ARTEMIS_PASSIVE_HUNTRESS";
    }

    @Override
    public void apply(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 210, 1, true, false));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 210, 0, true, false));
    }
}
