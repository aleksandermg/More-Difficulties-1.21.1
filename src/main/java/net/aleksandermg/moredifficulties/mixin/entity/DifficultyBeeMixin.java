package net.aleksandermg.moredifficulties.mixin.entity;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Bee.class)
public class DifficultyBeeMixin {
    @Redirect(method = "doHurtTarget", at = @At(value = "INVOKE",target = "Lnet/minecraft/world/level/Level;getDifficulty()Lnet/minecraft/world/Difficulty;"))
    public Difficulty asHardHurtTarget(Level instance){
        if (instance.getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")){
            return Difficulty.HARD;
        }
        return instance.getDifficulty();
    }

    @ModifyConstant(method = "doHurtTarget",constant = @Constant(intValue = 18))
    public int extraDoHurtTarget(int constant){
        if(((Bee)(Object) this).level().getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")){
            return 25;
        }
        return constant;
    }
}

