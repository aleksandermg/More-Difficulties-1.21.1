package net.aleksandermg.moredifficulties.mixin;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.monster.Ghast;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(targets = "net.minecraft.world.entity.monster.Ghast$GhastShootFireballGoal")
public class DifficultyGhastMixin2 {
    @Shadow
    @Final
    Ghast ghast;

    @ModifyConstant(method = "tick",constant = @Constant(intValue = -40))
    public int tickOverride(int chargeTime){
        if (ghast.level().getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")){
            return -20;
        }
        return chargeTime;
    }
}
