package lexicalerror.halfbloods.deities;

import lexicalerror.halfbloods.abilities.actives.ActiveAbility;
import lexicalerror.halfbloods.abilities.passives.PassiveAbility;

import java.util.List;

public class Deity{
    private final String name;
    private final List<PassiveAbility> passives;
    private final List<ActiveAbility> actives;

    public Deity(String name, List<PassiveAbility> passives, List<ActiveAbility> actives) {
        this.name = name;
        this.passives = passives;
        this.actives = actives;
    }

    public String getName() {
        return name;
    }

    public List<PassiveAbility> getPassives() {
        return passives;
    }

    public List<ActiveAbility> getActives() {
        return actives;
    }
}
