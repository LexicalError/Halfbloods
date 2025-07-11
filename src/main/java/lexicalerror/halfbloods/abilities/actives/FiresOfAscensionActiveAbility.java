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
import net.minecraft.world.World;

import static lexicalerror.halfbloods.damage_types.ModDamageTypes.FIRES_OF_ASCENSION;

public class FiresOfAscensionActiveAbility implements ActiveAbility{
    private final String name = "URIEL_ACTIVE_FIRES_OF_ASCENSION";
    private final String durationName = "URIEL_ACTIVE_FIRES_OF_ASCENSION";
    private final int tickSpeed = 20; // minecraft tickspeed, use this value times seconds for timing things
    private final int coolDown = tickSpeed * 90;
    private final int duration = tickSpeed * 60;
    private static int tickCounter = 0;
    private static final float auraRadius = 8.0F;

    public boolean isActive(PlayerEntity player) {
        PlayerComponent component = ModComponents.PLAYER_COMPONENT.get(player);
        return component.getCooldown(durationName) > 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void apply(PlayerEntity player) {
        if(!isActive(player)) return;

        World world = player.getWorld();
        tickCounter++;
        if (tickCounter % tickSpeed != 0 || world.isClient) return;
        Box aura = new Box(player.getBlockPos()).expand(auraRadius);
        int particleCount = 250;

        double centerX = player.getX();
        double centerY = player.getY() + 1.0; 
        double centerZ = player.getZ();

        for (int i = 0; i < particleCount; i++) {
            double x = (Math.random() * 2 - 1) * auraRadius;
            double y = (Math.random() * 2 - 1) * auraRadius;
            double z = (Math.random() * 2 - 1) * auraRadius;

            if (x * x + y * y + z * z > auraRadius * auraRadius) continue;

            ((ServerWorld) world).spawnParticles(ParticleTypes.FLAME, centerX + x, centerY + y, centerZ + z, 1, 0, 0, 0, 0);
        }

        for (LivingEntity e : world.getEntitiesByClass(LivingEntity.class, aura, p -> p != player)) {
            if (e.distanceTo(player) <= auraRadius) {
                e.damage((ServerWorld) world, new DamageSource(world.getRegistryManager().getEntryOrThrow(FIRES_OF_ASCENSION)), 1.0F);
                e.setOnFireFor(2);
            }
        }
        if(tickCounter % duration == 0){
            player.getHungerManager().setFoodLevel(0);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, coolDown - duration, 1));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, coolDown - duration, 1));
        }
    }

    @Override
    public void activate(PlayerEntity player) {
        tickCounter = 0;
        PlayerComponent component = ModComponents.PLAYER_COMPONENT.get(player);

        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, duration, 2));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, duration, 1));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, duration / 12, 0));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, duration, 0));
        player.sendMessage(Text.literal("Fires of Ascension consume all!").formatted(Formatting.GOLD, Formatting.BOLD), false);
        component.setCooldown(durationName, duration);
        component.setCooldown(name, coolDown);

    }

    @Override
    public boolean canActivate(PlayerEntity player) {
        PlayerComponent component = ModComponents.PLAYER_COMPONENT.get(player);
        int cooldown = component.getCooldown(name);
        if (cooldown > 0) {
            String msg = String.format("Fires of Ascension on cooldown: %ds", cooldown / tickSpeed);
            player.sendMessage(Text.literal(msg).formatted(Formatting.GRAY), false);
            return false;
        }
        return true;
    }



}
