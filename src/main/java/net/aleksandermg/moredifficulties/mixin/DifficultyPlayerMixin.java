package net.aleksandermg.moredifficulties.mixin;

import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class DifficultyPlayerMixin {
    @ModifyVariable(at = @At("HEAD"),method = "hurt",argsOnly = true)
    public float hurtPossible(float constant, DamageSource damageSource){
        if (damageSource.scalesWithDifficulty() && ((Player)((Object) this)).level().getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")) {
            constant = constant * 3.5F/ 2.0F;
        }
        if (damageSource.scalesWithDifficulty() && ((Player)((Object) this)).level().getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_SIMPLE")) {
            constant = Math.min(constant / 2.0F + 1.0F, constant);
        }
        return constant;
    }

    @Inject(at = @At("HEAD"), method = "aiStep")
    public void naturalHealing(CallbackInfo ci){
        Player player = ((Player)((Object) this));
        if(player.level().getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_SIMPLE")){
            if (player.tickCount % 200 == 0){
                if(player.getHealth() < player.getMaxHealth()){
                    if(player.level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION)){
                        player.heal(1.0F);
                    }
                }
            }
        }
    }
}
