package net.aleksandermg.moredifficulties.mixin.entity;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Spider.class)
public class DifficultySpiderMixin {
    @Redirect(method = "finalizeSpawn", at = @At(value = "INVOKE",target = "Lnet/minecraft/world/level/ServerLevelAccessor;getDifficulty()Lnet/minecraft/world/Difficulty;"))
    public Difficulty asHardSpiderSpawn(ServerLevelAccessor instance){
        if(instance.getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")){
            return Difficulty.HARD;
        }
        return instance.getDifficulty();
    }
}
