package net.aleksandermg.moredifficulties.mixin;

import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public class DifficultyTotemMixin {
    @Redirect(method = "checkTotemDeathProtection", at = @At(value = "INVOKE",target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z"))
    private boolean noTotemEffect(LivingEntity instance, MobEffectInstance mobEffectInstance) {
        LivingEntity living = ((LivingEntity)((Object) this));
        return living.level().getDifficulty() != Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE");
    }
}
