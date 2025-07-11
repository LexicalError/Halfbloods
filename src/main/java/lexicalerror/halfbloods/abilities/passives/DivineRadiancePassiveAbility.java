package lexicalerror.halfbloods.abilities.passives;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class DivineRadiancePassiveAbility implements PassiveAbility{
    private static final int effectDelay = 100;
    private static int tickCounter = 0;
    @Override
    public String getName(){ return "URIEL_PASSIVE_FLAME_WARD";}

    @Override
    public void apply(PlayerEntity player) {
        World world = player.getWorld();
        if (tickCounter % effectDelay == 0){
            tickCounter %= effectDelay;
            boolean isDay = world.isDay();
            boolean nearFire = world.getLightLevel(player.getBlockPos()) >= 9;

            if (isDay || nearFire) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, effectDelay + 1, 1, true, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, effectDelay + 1, 0, true, false));
            } else {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, effectDelay +1, 0, true, false));
            }
        }
        tickCounter ++;
    }
}
