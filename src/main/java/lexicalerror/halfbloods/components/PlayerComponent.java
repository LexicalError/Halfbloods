package lexicalerror.halfbloods.components;


import lexicalerror.halfbloods.abilities.Ability;
import lexicalerror.halfbloods.abilities.actives.ActiveAbility;
import lexicalerror.halfbloods.deities.Deity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;
import org.ladysnake.cca.api.v3.entity.C2SSelfMessagingComponent;
import org.ladysnake.cca.api.v3.entity.RespawnableComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Reference: https://ladysnake.org/wiki/cardinal-components-api/modules/entity

public class PlayerComponent implements C2SSelfMessagingComponent, AutoSyncedComponent, ServerTickingComponent, RespawnableComponent {
    private final PlayerEntity player;
    private Deity deity = null;
    private final Map<String, Integer> cooldowns = new HashMap<>();


    public PlayerComponent(PlayerEntity player) {
        this.player = player;
    }

    public void setDeity(Deity deity) {
        this.deity = deity;
    }

    public Deity getDeity() {
        return deity;
    }

    public Deity getString() {
        return this.deity;
    }

    public int getCooldown(String abilityId) {
        return cooldowns.getOrDefault(abilityId, 0);
    }

    public void setCooldown(String abilityId, int ticks) {
        cooldowns.put(abilityId, ticks);
    }

    @Override
    public void readData(ReadView readView) {}

    @Override
    public void writeData(WriteView writeView) {}

    @Override
    public void handleC2SMessage(RegistryByteBuf buf){
        if (deity == null)
            return;
        int key = buf.readVarInt();
        List<ActiveAbility> actives = deity.getActives();
        if (key < 0 || key > 1)
            return;
        System.out.println(key);
        ActiveAbility ability = actives.get(key);
        if (ability.canActivate(player)) {
            ability.activate(player);
        }
    }


    @Override
    public void serverTick() {
        if (this.deity == null)
            return;
        for (Ability passive : deity.getPassives()) {
            passive.apply(player);
        }
        for (Ability active : deity.getActives()) {
            active.apply(player);
        }
        cooldowns.replaceAll((abilityId, cooldown) -> Math.max(0, cooldown - 1));
    }
}
