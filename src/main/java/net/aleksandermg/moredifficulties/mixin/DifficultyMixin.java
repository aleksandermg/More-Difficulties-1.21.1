package net.aleksandermg.moredifficulties.mixin;

import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinIntrinsics;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Difficulty.class)
enum DifficultyMixin {
    MOREDIFFICULTIES_SIMPLE(MixinIntrinsics.currentEnumOrdinal(),"simple"),
    MOREDIFFICULTIES_POSSIBLE(MixinIntrinsics.currentEnumOrdinal(),"possible");

    @Shadow
    DifficultyMixin(int j, String string2) {
    }
}
