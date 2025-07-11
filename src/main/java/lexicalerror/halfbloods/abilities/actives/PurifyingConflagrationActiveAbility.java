package lexicalerror.halfbloods.abilities.actives;

import lexicalerror.halfbloods.ModComponents;
import lexicalerror.halfbloods.components.PlayerComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;

import static lexicalerror.halfbloods.damage_types.ModDamageTypes.PURIFYING_CONFLAGRATION;

public class PurifyingConflagrationActiveAbility implements ActiveAbility{
    private final String name = "URIEL_ACTIVE_PURIFYING_CONFLAGRATION";
    private final int tickSpeed = 20; // minecraft tickspeed, use this value times seconds for timing things
    private final int coolDown = tickSpeed * 10;
    private final float abilityRange = 8.0F;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void apply(PlayerEntity player) {}

    public boolean canActivate(PlayerEntity player) {
        PlayerComponent component = ModComponents.PLAYER_COMPONENT.get(player);
        int cooldown = component.getCooldown(name);
        if (cooldown > 0) {
            String msg = String.format("Purifying Conflagration on cooldown: %ds", cooldown / tickSpeed);
            player.sendMessage(Text.literal(msg).formatted(Formatting.GRAY), false);
            return false;
        }
        return true;
    }

    public void activate(PlayerEntity player) {
        PlayerComponent component = ModComponents.PLAYER_COMPONENT.get(player);
        ServerWorld world = (ServerWorld) player.getWorld();
        DamageSource damageSource = new DamageSource(world.getRegistryManager().getEntryOrThrow(PURIFYING_CONFLAGRATION));
        Box range = new Box(player.getBlockPos()).expand(abilityRange);
        for (LivingEntity entity : world.getEntitiesByClass(LivingEntity.class, range, e -> e != player)) {
            if (entity.distanceTo(player) > abilityRange){
                continue;
            }
            if (!entity.isTeammate(player)) {
                entity.setOnFireFor(4.0F);
                entity.damage(world, damageSource, 6.0F);
            } else {
                entity.clearStatusEffects();
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, coolDown / 2, 0));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, coolDown / 2, 1));
            }
        }
        int rings = 6;
        int pointsPerRing = 30;

        for (int r = 0; r < rings; r++) {
            double radius = (abilityRange / rings) * (r + 1);
            for (int i = 0; i < pointsPerRing; i++) {
                double angle = 2 * Math.PI * i / pointsPerRing;
                double xOffset = Math.cos(angle) * radius;
                double zOffset = Math.sin(angle) * radius;

                double px = player.getX() + xOffset;
                double py = player.getY() + 1.0;
                double pz = player.getZ() + zOffset;

                world.spawnParticles(ParticleTypes.SOUL_FIRE_FLAME, px, py, pz, 1, 0, 0, 0, 0);
                world.spawnParticles(ParticleTypes.FLAME, px, py + 0.2, pz, 1, 0, 0, 0, 0);
                if (r % 2 == 0) {
                    world.spawnParticles(ParticleTypes.END_ROD, px, py + 0.4, pz, 1, 0, 0, 0, 0);
                }
            }
        }
        
        player.sendMessage(Text.literal("Purifying Conflagration Activated!").formatted(Formatting.GOLD, Formatting.BOLD), false);

        component.setCooldown(name, coolDown);
    }
}
