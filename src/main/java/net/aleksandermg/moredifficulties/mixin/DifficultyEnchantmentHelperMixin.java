package net.aleksandermg.moredifficulties.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class DifficultyEnchantmentHelperMixin {
    @Inject(method = "modifyDurabilityToRepairFromXp",cancellable = true,at=@At("RETURN"))
    private static void Mending(ServerLevel serverLevel, ItemStack itemStack, int i, CallbackInfoReturnable<Integer> cir){
        if(serverLevel.getDifficulty() == Difficulty.valueOf("MOREDIFFICULTIES_POSSIBLE")){
            cir.setReturnValue(cir.getReturnValue()/2);
        }
    }
}
