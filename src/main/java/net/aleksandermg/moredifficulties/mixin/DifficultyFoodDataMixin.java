package net.aleksandermg.moredifficulties.mixin;

import net.minecraft.world.Difficulty;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FoodData.class)
public class DifficultyFoodDataMixin {
    @Redirect(method = "tick", at = @At(value = "INVOKE",target = "Lnet/minecraft/world/level/Level;getDifficulty()Lnet/minecraft/world/Difficulty;"))

    private Difficulty returnDifferentDifficulty(Level instance) {
        if (instance.getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_SIMPLE")) {
            return Difficulty.EASY;
        }
        if (instance.getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")) {
            return Difficulty.HARD;
        }

            return instance.getDifficulty();
    }
}