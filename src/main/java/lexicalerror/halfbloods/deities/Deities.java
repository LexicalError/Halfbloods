package lexicalerror.halfbloods.deities;

import lexicalerror.halfbloods.abilities.actives.ArcadianHoundsActiveAbility;
import lexicalerror.halfbloods.abilities.actives.FiresOfAscensionActiveAbility;
import lexicalerror.halfbloods.abilities.actives.MoonlitArrowsActiveAbility;
import lexicalerror.halfbloods.abilities.actives.PurifyingConflagrationActiveAbility;
import lexicalerror.halfbloods.abilities.passives.DivineRadiancePassiveAbility;
import lexicalerror.halfbloods.abilities.passives.FlameWardPassiveAbility;
import lexicalerror.halfbloods.abilities.passives.HuntressPassiveAbility;
import lexicalerror.halfbloods.abilities.passives.NightsMaidenPassiveAbility;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public enum Deities {
    ARTEMIS(new Deity(
            "ARTEMIS",
            List.of(new HuntressPassiveAbility(), new NightsMaidenPassiveAbility()),
            List.of(new ArcadianHoundsActiveAbility(), new MoonlitArrowsActiveAbility())
    )),
    URIEL(new Deity(
            "URIEL",
            List.of(new FlameWardPassiveAbility(), new DivineRadiancePassiveAbility()),
            List.of(new PurifyingConflagrationActiveAbility(), new FiresOfAscensionActiveAbility())
    )),
    ;



    private final Deity deity;

    Deities(Deity deity) {
        this.deity = deity;
    }

    public Deity getDeity() {
        return deity;
    }

    public static @Nullable Deities fromString(String input) {
        try {
            return Deities.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
