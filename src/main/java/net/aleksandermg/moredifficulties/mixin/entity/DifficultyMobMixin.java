package net.aleksandermg.moredifficulties.mixin.entity;

import net.aleksandermg.moredifficulties.MoreDifficulties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public class DifficultyMobMixin {
    @Unique
    private static final ResourceLocation BOSS_EXTRA_HEALTH_ID = ResourceLocation.fromNamespaceAndPath("moredifficulties","boss_health");

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
    @Inject(method = "tick", at = @At(value = "TAIL"))
    public void updateBossHP(CallbackInfo ci) {
        Mob mob = (Mob)(Object) this;
        if(!mob.level().isClientSide()){
            if (mob instanceof WitherBoss || mob instanceof EnderDragon){
                AttributeInstance bossMaxHealth = mob.getAttribute(Attributes.MAX_HEALTH);

                if (mob.level().getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE") && !bossMaxHealth.hasModifier(BOSS_EXTRA_HEALTH_ID)){
                    bossMaxHealth.addOrReplacePermanentModifier(new AttributeModifier(BOSS_EXTRA_HEALTH_ID, 1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                    mob.setHealth(mob.getMaxHealth());
                }
                if (mob.level().getDifficulty() != Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE") && bossMaxHealth.hasModifier(BOSS_EXTRA_HEALTH_ID)){
                    mob.setHealth(mob.getHealth()/2);
                    bossMaxHealth.removeModifier(BOSS_EXTRA_HEALTH_ID);
                }
            }
        }
    }

    @Inject(method = "finalizeSpawn",at = @At("RETURN"))
    public void hostileMobSpeedIncrease(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir){
        Mob mob = (Mob)(Object) this;
        if(mob instanceof Zombie || mob instanceof AbstractSkeleton || mob instanceof Spider || mob instanceof Creeper){
            AttributeInstance mobAttributeSpeed = mob.getAttribute(Attributes.MOVEMENT_SPEED);

            if(mob.level().getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")){

                if (!mobAttributeSpeed.hasModifier(MoreDifficulties.HOSTILE_EXTRA_SPEED_ID) && mobSpawnType != MobSpawnType.TRIGGERED) {
                    mobAttributeSpeed.addOrReplacePermanentModifier(new AttributeModifier(MoreDifficulties.HOSTILE_EXTRA_SPEED_ID, 0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                }
            }
        }

    }


}
