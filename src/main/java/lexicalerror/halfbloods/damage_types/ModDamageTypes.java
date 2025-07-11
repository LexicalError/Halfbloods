package lexicalerror.halfbloods.damage_types;

import lexicalerror.halfbloods.Halfbloods;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModDamageTypes {
    public static final RegistryKey<DamageType> FIRES_OF_ASCENSION = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Halfbloods.MOD_ID, "fires_of_ascension"));
    public static final RegistryKey<DamageType> PURIFYING_CONFLAGRATION = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Halfbloods.MOD_ID, "purifying_conflagration"));
}
