package lexicalerror.halfbloods.entities;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.Variants;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.WolfSoundVariants;
import net.minecraft.entity.passive.WolfVariant;
import net.minecraft.entity.passive.WolfVariants;
import net.minecraft.entity.spawn.SpawnContext;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ArcadianHoundsEntity extends WolfEntity {
    private int lifeTime = 0;

    public ArcadianHoundsEntity(EntityType<? extends WolfEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createCustomWolfAttributes() {
        return WolfEntity.createWolfAttributes()
                .add(EntityAttributes.MAX_HEALTH, 60.0D)
                .add(EntityAttributes.ATTACK_DAMAGE, 10.0D)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.35D)
                .add(EntityAttributes.SCALE, 1.7f);
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    @Override
    public Identifier getTextureId() {
        RegistryEntry<WolfVariant> variantEntry = this.getRegistryManager()
                .getOrThrow(RegistryKeys.WOLF_VARIANT)
                .getEntry(WolfVariants.ASHEN.getValue())
                .orElseThrow(() -> new IllegalStateException("Variant not found"));
        WolfVariant wolfVariant = variantEntry.value();
        if (this.isTamed()) {
            return wolfVariant.assetInfo().tame().texturePath();
        } else {
            return this.hasAngerTime() ? wolfVariant.assetInfo().angry().texturePath() : wolfVariant.assetInfo().wild().texturePath();
        }
    }

    @Override
    protected void updateAttributesForTamed() {
        if (this.isTamed()) {
            this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(60.0D);
            this.setHealth(100.0F);
        } else {
            this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(60.0D);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient && this.lifeTime > 0) {
            this.lifeTime--;
            if (this.lifeTime <= 0) {
                this.discard(); // remove from world
            }
        }
    }
}
