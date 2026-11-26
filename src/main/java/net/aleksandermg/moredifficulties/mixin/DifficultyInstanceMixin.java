package net.aleksandermg.moredifficulties.mixin;

import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DifficultyInstance.class)
public class DifficultyInstanceMixin {
    @Shadow
    @Final
    private Difficulty base;
    @Shadow
    @Final
    private float effectiveDifficulty;

    @Redirect(method = "calculateDifficulty", at = @At(value = "INVOKE",target = "Lnet/minecraft/world/Difficulty;getId()I"))
    private int returnDifferentDifficulty(Difficulty difficulty) {
        if (difficulty == Difficulty.valueOf("MOREDIFFICULTIES_SIMPLE")) {
            return 1;
        }
        return difficulty.getId();
    }

    @Inject(at = @At("HEAD"),cancellable = true, method = "getSpecialMultiplier")
    public void injectSpecialMultiplier(CallbackInfoReturnable<Float> cir) { // local difficulty
        if (this.base == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")) {
            cir.setReturnValue(this.effectiveDifficulty>10.0F ? 2.0F : (this.effectiveDifficulty/10.0F)*2.0F);
        }
    }
}
