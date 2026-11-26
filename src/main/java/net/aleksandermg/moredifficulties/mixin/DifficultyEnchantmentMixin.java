package net.aleksandermg.moredifficulties.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(Enchantment.class)
public class DifficultyEnchantmentMixin {

    @WrapOperation(method = "modifyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;modifyDamageFilteredValue(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/server/level/ServerLevel;ILnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lorg/apache/commons/lang3/mutable/MutableFloat;)V"))
    public void sharpnessEnchantmentDecrease(Enchantment instance, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>> dataComponentType, ServerLevel serverLevel, int i, ItemStack itemStack, Entity entity, DamageSource damageSource, MutableFloat mutableFloat, Operation<Void> original) {
        float old_value = mutableFloat.floatValue();
        original.call(instance, dataComponentType, serverLevel, i, itemStack, entity, damageSource, mutableFloat);
        if(serverLevel.getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")){
            if(instance.description().getContents() instanceof TranslatableContents translatableContents) {
                if(translatableContents.getKey().equals("enchantment.minecraft.sharpness")){
                    if(damageSource.getEntity() instanceof Player){

                        float new_value = mutableFloat.floatValue();
                        mutableFloat.setValue(old_value+(new_value-old_value)*0.75);

                    }
                }
            }
        }

    }
    @WrapMethod(method = "modifyDamageProtection")
    public void protectionEnchantmentDecrease(ServerLevel serverLevel, int i, ItemStack itemStack, Entity entity, DamageSource damageSource, MutableFloat mutableFloat, Operation<Void> original){
        float old_value = mutableFloat.floatValue();
        original.call(serverLevel, i, itemStack, entity, damageSource, mutableFloat);
        if(serverLevel.getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")){
            if(((Enchantment)(Object) this).description().getContents() instanceof TranslatableContents translatableContents) {
                if(translatableContents.getKey().equals("enchantment.minecraft.protection")){
                    if(entity instanceof Player){

                        float new_value = mutableFloat.floatValue();
                        mutableFloat.setValue(old_value+(new_value-old_value)*0.75);

                    }
                }
            }
        }
    }
}
