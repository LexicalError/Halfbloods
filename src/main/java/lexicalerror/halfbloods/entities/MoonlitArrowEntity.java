package lexicalerror.halfbloods.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class MoonlitArrowEntity extends ArrowEntity {

    public MoonlitArrowEntity(EntityType<? extends ArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public MoonlitArrowEntity(ServerWorld world, LivingEntity shooter, ItemStack itemStack, ItemStack stack) {
        super(world, shooter, itemStack, stack);
    }



    @Override
    public void tick() {
        super.tick();

        if (!this.getWorld().isClient()) {
            ServerWorld serverWorld = (ServerWorld) this.getWorld();
            double stepSize = 0.2;


            double dx = this.getX() - this.lastX;
            double dy = this.getY() - this.lastY;
            double dz = this.getZ() - this.lastZ;

            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
            int steps = Math.max(1, (int)(distance / stepSize));
            double t;
            double x;
            double y;
            double z;
            for (int i = 0; i < steps; i++) {
                t = (double) i / steps;
                x = this.lastX + dx * t;
                y = this.lastY + dy * t;
                z = this.lastZ + dz * t;

                serverWorld.spawnParticles(ParticleTypes.GLOW, x, y, z, 1, 0.05, 0.05, 0.05, 0.0);
            }
            System.out.print("\n");

        }
    }

}
