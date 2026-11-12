package net.aleksandermg.moredifficulties.mixin;

import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Raid.class)
public class DifficultyRaidMixin {
    @Inject(at = @At("HEAD"),cancellable = true, method = "getNumGroups")

    private void getNumGroups(Difficulty difficulty, CallbackInfoReturnable<Integer> cir) {
        if (difficulty == Difficulty.valueOf("MOREDIFFICULTIES_SIMPLE")) {
            cir.setReturnValue(3);
        }
        if (difficulty == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")) {
            cir.setReturnValue(8);
        }
    }
    @Redirect(method = "getPotentialBonusSpawns", at = @At(value = "INVOKE",target = "Lnet/minecraft/world/DifficultyInstance;getDifficulty()Lnet/minecraft/world/Difficulty;"))

    private Difficulty getPotentialBonusSpawns(DifficultyInstance instance) {
        if (instance.getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_SIMPLE")) {
            return Difficulty.EASY;
        }
        return instance.getDifficulty();
    }


}
