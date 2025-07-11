package lexicalerror.halfbloods.abilities.passives;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class NightsMaidenPassiveAbility implements PassiveAbility{
    private int tickCounter = 0;
    private final int effectDelay = 40;
    @Override
    public String getName() {
        return "ARTEMIS_PASSIVE_NIGHTS_MAIDEN";
    }

    @Override
    public void apply(PlayerEntity player) {
        tickCounter++;
        World world = player.getWorld();
        if(world.isNight()) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 210, 0, true, false));
            if(tickCounter % effectDelay == 0){
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 0, true, false));
            }
        }
        else {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 210, 0, true, false));
        }
    }
}
