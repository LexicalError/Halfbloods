package lexicalerror.halfbloods.abilities.actives;

import lexicalerror.halfbloods.ModComponents;
import lexicalerror.halfbloods.components.PlayerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class MoonlitArrowsActiveAbility implements ActiveAbility{
    private final String name = "ARTEMIS_ACTIVE_MOONLIT_ARROWS";
    private final String durationName = "ARTEMIS_ACTIVE_MOONLIT_ARROWS_DURATION";
    private final int coolDown = 20 * 60;
    private final int duration = 20 * 30;

    public boolean isActive(PlayerEntity player) {
        PlayerComponent component = ModComponents.PLAYER_COMPONENT.get(player);
        return component.getCooldown(durationName) > 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void apply(PlayerEntity player) {}

    @Override
    public boolean canActivate(PlayerEntity player) {
        PlayerComponent component = ModComponents.PLAYER_COMPONENT.get(player);
        int cooldown = component.getCooldown(name);
        if (cooldown > 0) {
            String msg = String.format("Moonlit Arrows on cooldown: %ds", cooldown / 20);
            player.sendMessage(Text.literal(msg).formatted(Formatting.GRAY), false);
            return false;
        }
        return true;
    }

    @Override
    public void activate(PlayerEntity player) {
        PlayerComponent component = ModComponents.PLAYER_COMPONENT.get(player);

        player.sendMessage(Text.literal("Moonlit Arrows Activate!").formatted(Formatting.AQUA, Formatting.BOLD), false);

        component.setCooldown(durationName, duration);

        component.setCooldown(name, coolDown);
    }
}
