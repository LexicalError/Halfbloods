package lexicalerror.halfbloods.abilities.passives;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Objects;

public class FlameWardPassiveAbility implements PassiveAbility{

    @Override
    public String getName(){ return "URIEL_PASSIVE_FLAME_WARD";}

    @Override
    public void apply(PlayerEntity player){
        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseDamageTaken, damageTaken, blocked) -> {
            if (entity instanceof LivingEntity target && source.getAttacker() instanceof PlayerEntity attacker) {
                if (player == attacker && Objects.equals(source.getType().msgId(), "thorns")) {
                    target.setOnFireFor(4.0F);
                }
            }
        });
    }
}
