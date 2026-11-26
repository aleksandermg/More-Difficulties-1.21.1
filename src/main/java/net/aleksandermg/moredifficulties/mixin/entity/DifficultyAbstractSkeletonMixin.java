package net.aleksandermg.moredifficulties.mixin.entity;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractSkeleton.class)
public class DifficultyAbstractSkeletonMixin {

    @Redirect(method = "performRangedAttack", at = @At(value = "INVOKE",target = "Lnet/minecraft/world/level/Level;getDifficulty()Lnet/minecraft/world/Difficulty;"))
    private Difficulty performRangedAttackDifficultyChange(Level instance) {
        if (instance.getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")) {
            return Difficulty.HARD;
        }
        return instance.getDifficulty();
    }

    @Redirect(method = "reassessWeaponGoal", at = @At(value = "INVOKE",target = "Lnet/minecraft/world/level/Level;getDifficulty()Lnet/minecraft/world/Difficulty;"))
    private Difficulty reassessWeaponGoalDifficultyChange(Level instance) {
        if (instance.getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")) {
            return Difficulty.HARD;
        }
        return instance.getDifficulty();
    }

}
