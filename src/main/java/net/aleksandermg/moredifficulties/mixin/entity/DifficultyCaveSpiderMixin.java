package net.aleksandermg.moredifficulties.mixin.entity;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CaveSpider.class)
public class DifficultyCaveSpiderMixin {
    @Redirect(method = "doHurtTarget", at = @At(value = "INVOKE",target = "Lnet/minecraft/world/level/Level;getDifficulty()Lnet/minecraft/world/Difficulty;"))
    public Difficulty asHardHurtTarget(Level instance){
        if (instance.getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")){
            return Difficulty.HARD;
        }
        return instance.getDifficulty();
    }

    @ModifyConstant(method = "doHurtTarget",constant = @Constant(intValue = 15))
    public int extraDoHurtTarget(int constant){
        if(((CaveSpider)(Object) this).level().getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")){
            return 20;
        }
        return constant;
    }
}
