package net.aleksandermg.moredifficulties.mixin;

import net.aleksandermg.moredifficulties.MoreDifficulties;

import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.client.gui.screens.worldselection.CreateWorldScreen$GameTab")
//@Mixin(CreateWorldScreen$GameTab$)
public class DifficultyGameTabMixin {
    @Redirect(method = "<init>", at = @At(value = "INVOKE",target = "Lnet/minecraft/world/Difficulty;values()[Lnet/minecraft/world/Difficulty;"))

    private static Difficulty[] ReturnOrder() {
        return MoreDifficulties.getOrder();
    }
}

