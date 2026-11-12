package net.aleksandermg.moredifficulties.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(targets = "net.minecraft.world.entity.raid.Raid$RaiderType")
public class RaidTableMixin {
    @Shadow
    @Mutable
    int[] spawnsPerWaveBeforeBonus;

    @Inject(at = @At("RETURN"), method = "<init>")
    private void ExtraRaidType(CallbackInfo info) {
        spawnsPerWaveBeforeBonus  = Arrays.copyOf(spawnsPerWaveBeforeBonus, spawnsPerWaveBeforeBonus.length + 1);
        spawnsPerWaveBeforeBonus[spawnsPerWaveBeforeBonus.length - 1] = spawnsPerWaveBeforeBonus[spawnsPerWaveBeforeBonus.length - 2]+2;
    }
}
