package net.aleksandermg.moredifficulties.mixin.entity;

import net.aleksandermg.moredifficulties.MoreDifficulties;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractArrow.class)
public class DifficultyAbstractArrowMixin {

    @Inject(at = @At("HEAD"),cancellable = true ,method = "canHitEntity")
    public void canHitSkeleton(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        Entity Shooter = ((AbstractArrow)(Object) this).getOwner();
        if(entity.level().getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")){
            if ((entity instanceof AbstractSkeleton || entity instanceof SkeletonHorse) && Shooter instanceof AbstractSkeleton skeletonShooter){
                if (skeletonShooter.getAttributes().hasModifier(Attributes.FOLLOW_RANGE, MoreDifficulties.TRAP_EXTRA_RANGE_ID)) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
