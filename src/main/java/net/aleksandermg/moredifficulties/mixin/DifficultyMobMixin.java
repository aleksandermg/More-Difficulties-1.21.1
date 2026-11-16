package net.aleksandermg.moredifficulties.mixin;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public class DifficultyMobMixin {

    @ModifyConstant(method = "populateDefaultEquipmentSlots",constant = @Constant(floatValue = 0.25F))
    public float SetMobGetArmor(float old_number){
        if (((Mob)((Object) this)).level().getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")) {
            return 0.05F;
        }
        return old_number;
    }

    @Inject(method = "getEquipmentDropChance",cancellable = true,at = @At(value = "RETURN"))
    public void setArmorDrop(EquipmentSlot equipmentSlot, CallbackInfoReturnable<Float> cir) {
        if (equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR && ((Mob)((Object) this)).level().getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")) {
            if (0.09F > cir.getReturnValue() && cir.getReturnValue() > 0.08F) {
                cir.setReturnValue(0.01F);
            }
        }
    }
}
