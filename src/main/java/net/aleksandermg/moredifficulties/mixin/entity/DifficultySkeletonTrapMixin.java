package net.aleksandermg.moredifficulties.mixin.entity;

import net.aleksandermg.moredifficulties.MoreDifficulties;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.SkeletonTrapGoal;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SkeletonTrapGoal.class)
public class DifficultySkeletonTrapMixin {

    @Inject(at = @At("RETURN"), method = "createSkeleton")
    public void createBetterSkeleton(DifficultyInstance difficultyInstance, AbstractHorse abstractHorse, CallbackInfoReturnable<Skeleton> cir) {
        Skeleton skeleton = cir.getReturnValue();
        if (skeleton.level().getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE") && skeleton != null && abstractHorse != null){
            Holder<Enchantment> protection  = skeleton.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.PROTECTION);
            Holder<Enchantment> power  = skeleton.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.POWER);
            Holder<Enchantment> punch  = skeleton.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.PUNCH);


            ItemStack[] ironArmorSet = {
                    new ItemStack(Items.IRON_HELMET),
                    new ItemStack(Items.IRON_CHESTPLATE),
                    new ItemStack(Items.LEATHER_LEGGINGS),
                    new ItemStack(Items.LEATHER_BOOTS)
            };

            for (ItemStack ironArmor : ironArmorSet) {
                ironArmor.enchant(protection,4);
                skeleton.setItemSlot(skeleton.getEquipmentSlotForItem(ironArmor),ironArmor);
                skeleton.setDropChance(skeleton.getEquipmentSlotForItem(ironArmor),0.0F);

            }

            ItemStack bow = new ItemStack(Items.BOW);
            bow.enchant(punch,2);
            bow.enchant(power,3);
            skeleton.setItemInHand(InteractionHand.MAIN_HAND,bow);
            skeleton.setDropChance(EquipmentSlot.MAINHAND,0.0F);

            AttributeInstance skeletonAttributeRange = skeleton.getAttribute(Attributes.FOLLOW_RANGE);
            AttributeInstance abstractHorseAttributeRange = abstractHorse.getAttribute(Attributes.FOLLOW_RANGE);
            AttributeInstance abstractHorseAttributeSpeed = abstractHorse.getAttribute(Attributes.MOVEMENT_SPEED);

            if (!skeletonAttributeRange.hasModifier(MoreDifficulties.TRAP_EXTRA_RANGE_ID)){
                skeletonAttributeRange.addOrReplacePermanentModifier(new AttributeModifier(MoreDifficulties.TRAP_EXTRA_RANGE_ID, 1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            }
            if (!abstractHorseAttributeRange.hasModifier(MoreDifficulties.TRAP_EXTRA_RANGE_ID)){
                abstractHorseAttributeRange.addOrReplacePermanentModifier(new AttributeModifier(MoreDifficulties.TRAP_EXTRA_RANGE_ID, 1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            }
            if (!abstractHorseAttributeRange.hasModifier(MoreDifficulties.TRAP_EXTRA_SPEED_ID)) {
                abstractHorseAttributeSpeed.addOrReplacePermanentModifier(new AttributeModifier(MoreDifficulties.TRAP_EXTRA_SPEED_ID, 1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            }

        }

    }
}