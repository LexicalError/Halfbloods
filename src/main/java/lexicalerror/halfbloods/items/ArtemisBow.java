package lexicalerror.halfbloods.items;

import lexicalerror.halfbloods.ModComponents;
import lexicalerror.halfbloods.abilities.actives.ActiveAbility;
import lexicalerror.halfbloods.abilities.actives.MoonlitArrowsActiveAbility;
import lexicalerror.halfbloods.components.PlayerComponent;
import lexicalerror.halfbloods.deities.Deity;
import lexicalerror.halfbloods.entities.MoonlitArrowEntity;
import lexicalerror.halfbloods.mixin.PersistentProjectileEntityAccessor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.BowItem;

import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ArtemisBow extends BowItem {

    public ArtemisBow(Settings settings) {
        super(settings);
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity playerEntity)) {
            return false;
        } else {
            ItemStack itemStack = playerEntity.getProjectileType(stack);
            if (itemStack.isEmpty()) {
                return false;
            } else {
                int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
                float f = getPullProgress(i);
                if ((double)f < 0.1) {
                    return false;
                } else {
                    List<ItemStack> list = load(stack, itemStack, playerEntity);
                    if (world instanceof ServerWorld serverWorld) {
                        if (!list.isEmpty()) {
                            PlayerComponent component = ModComponents.PLAYER_COMPONENT.get(playerEntity);

                            Deity deity = component.getDeity();
                            if(deity == null || !Objects.equals(deity.getName(), "ARTEMIS"))
                                return false;

                            ActiveAbility ability = deity.getActives().get(1);

                            if (!serverWorld.isNight() || !(ability instanceof MoonlitArrowsActiveAbility) || !((MoonlitArrowsActiveAbility) ability).isActive(playerEntity))
                                this.shootAll(serverWorld, playerEntity, playerEntity.getActiveHand(), stack, list, f * 3.0F, 1.0F, f == 1.0F, (LivingEntity)null);
                            else
                                this.moonlitShootAll(serverWorld, playerEntity, playerEntity.getActiveHand(), stack, list, f * 5.0F, 0.5F, f == 1.0F, (LivingEntity)null);
                        }
                    }

                    world.playSound((Entity)null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                    return true;
                }
            }
        }
    }

    protected void moonlitShootAll(ServerWorld world, LivingEntity shooter, Hand hand, ItemStack stack, List<ItemStack> projectiles, float speed, float divergence, boolean critical, @Nullable LivingEntity target) {
        float f = EnchantmentHelper.getProjectileSpread(world, stack, shooter, 0.0F);
        float g = projectiles.size() == 1 ? 0.0F : 2.0F * f / (float)(projectiles.size() - 1);
        float h = (float)((projectiles.size() - 1) % 2) * g / 2.0F;
        float i = 1.0F;

        for(int j = 0; j < projectiles.size(); ++j) {
            ItemStack itemStack = (ItemStack)projectiles.get(j);
            float yawOffset = h + i * (float)((j + 1) / 2) * g;
            i = -i;

            PersistentProjectileEntity arrow = new MoonlitArrowEntity(world, shooter, itemStack.copyWithCount(1), stack);

            arrow.setVelocity(shooter, shooter.getPitch(), shooter.getYaw() + yawOffset, 0.0F, speed, divergence);
            arrow.setCritical(critical);
            ((PersistentProjectileEntityAccessor) arrow).halfbloods$setPierceLevel((byte) 10);
            arrow.setGlowing(true);

            world.spawnEntity(arrow);

            stack.damage(this.getWeaponStackDamage(itemStack), shooter, LivingEntity.getSlotForHand(hand));

            if (stack.isEmpty()) {
                break;
            }
        }
    }
}
