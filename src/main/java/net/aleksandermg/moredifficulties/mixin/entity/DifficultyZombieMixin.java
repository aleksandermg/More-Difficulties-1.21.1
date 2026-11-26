package net.aleksandermg.moredifficulties.mixin.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Zombie.class)
public class DifficultyZombieMixin {

    @Redirect(method = "killedEntity", at = @At(value = "INVOKE",target = "Lnet/minecraft/server/level/ServerLevel;getDifficulty()Lnet/minecraft/world/Difficulty;"))
    public Difficulty asHardTransformZombieVillager(ServerLevel instance){
        return instance.getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE") ? Difficulty.HARD : instance.getDifficulty() ;
    }
    @Redirect(method = "populateDefaultEquipmentSlots", at = @At(value = "INVOKE",target = "Lnet/minecraft/world/level/Level;getDifficulty()Lnet/minecraft/world/Difficulty;"))
    public Difficulty asHardZombieMainHand(Level instance){
        if(instance.getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")){
            return Difficulty.HARD;
        }
        return instance.getDifficulty();
    }
    @Redirect(method = "hurt", at = @At(value = "INVOKE",target = "Lnet/minecraft/world/level/Level;getDifficulty()Lnet/minecraft/world/Difficulty;"))
    public Difficulty asHardHurt(Level instance){
        if(instance.getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")){
            return Difficulty.HARD;
        }
        return instance.getDifficulty();
    }

}
