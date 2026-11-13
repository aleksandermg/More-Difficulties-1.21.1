package net.aleksandermg.moredifficulties.mixin;

import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DifficultyInstance.class)
public class DifficultyInstanceMixin {
    @Redirect(method = "calculateDifficulty", at = @At(value = "INVOKE",target = "Lnet/minecraft/world/Difficulty;getId()I"))

    private int returnDifferentDifficulty(Difficulty difficulty) {
        if (difficulty == Difficulty.valueOf("MOREDIFFICULTIES_SIMPLE")) {
            return 1;
        }

        return difficulty.getId();
    }
}
