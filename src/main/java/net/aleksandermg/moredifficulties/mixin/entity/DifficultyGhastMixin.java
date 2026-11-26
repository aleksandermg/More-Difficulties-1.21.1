package net.aleksandermg.moredifficulties.mixin.entity;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.monster.Ghast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Ghast.class)
public class DifficultyGhastMixin {
    @Inject(at = @At("HEAD"), method = "getExplosionPower", cancellable = true)
    public void getExplosionPower(CallbackInfoReturnable<Integer> cir){
        Ghast ghast = ((Ghast)((Object) this));
        if (ghast.level().getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")){
            cir.setReturnValue(2);
        }
    }
}
