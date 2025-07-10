package lexicalerror.halfbloods.abilities.actives;

import lexicalerror.halfbloods.ModComponents;
import lexicalerror.halfbloods.components.PlayerComponent;
import lexicalerror.halfbloods.entities.ArcadianHoundsEntity;
import lexicalerror.halfbloods.entities.ModEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class ArcadianHoundsActiveAbility implements ActiveAbility{
    private final String name = "ARTEMIS_ACTIVE_ARCADIAN_HOUNDS";
    private final int coolDown = 240 * 20;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void apply(PlayerEntity player) {

    }

    @Override
    public boolean canActivate(PlayerEntity player) {
        PlayerComponent component = ModComponents.PLAYER_COMPONENT.get(player);

        int cooldown = component.getCooldown(name);
        if (cooldown > 0) {
            String msg = String.format("Arcadian Hounds on cooldown: %ds", cooldown / 20);
            player.sendMessage(Text.literal(msg).formatted(Formatting.GRAY), false);
            return false;
        }
        return true;
    }

    @Override
    public void activate(PlayerEntity player) {
        PlayerComponent component = ModComponents.PLAYER_COMPONENT.get(player);

        World world = player.getWorld();

        player.sendMessage(Text.literal("Arcadian Hounds Activate!").formatted(Formatting.AQUA, Formatting.BOLD), false);

        for (int i = 0; i < 2; i++) {
            ArcadianHoundsEntity hound = new ArcadianHoundsEntity(ModEntities.ARCADIAN_HOUND, world);

            double angle = i * Math.PI / 2;
            double distance = 2.0;
            double dx = player.getX() + Math.cos(angle) * distance;
            double dz = player.getZ() + Math.sin(angle) * distance;
            hound.refreshPositionAndAngles(dx, player.getY(), dz, player.getYaw(), 0.0F);

            hound.setTamedBy(player);
            hound.setSitting(false);
            hound.setPersistent();
            hound.setHealth(hound.getMaxHealth());
            hound.setGlowing(true);
            hound.setCustomName(Text.literal("Arcadian Hound").formatted(Formatting.AQUA));
            world.spawnEntity(hound);

            hound.setLifeTime(20 * 120);
        }

        component.setCooldown(name,  coolDown);
    }
}
