package net.aleksandermg.moredifficulties.mixin;

import net.aleksandermg.moredifficulties.MoreDifficulties;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(OptionsScreen.class)
public class DifficultyOptionScreenMixin {
    @Redirect(method = "createDifficultyButton", at = @At(value = "INVOKE",target = "Lnet/minecraft/world/Difficulty;values()[Lnet/minecraft/world/Difficulty;"))

    @Unique
    private static Difficulty[] ReturnOrder() {
        return MoreDifficulties.getOrder();
    }
}

