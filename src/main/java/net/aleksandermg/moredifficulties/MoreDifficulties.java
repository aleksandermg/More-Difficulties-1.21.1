package net.aleksandermg.moredifficulties;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Difficulty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class MoreDifficulties implements ModInitializer {
	public static final String MOD_ID = "moredifficulties";
	public static final ResourceLocation TRAP_EXTRA_RANGE_ID = ResourceLocation.fromNamespaceAndPath("moredifficulties","trap_range");
	public static final ResourceLocation TRAP_EXTRA_SPEED_ID = ResourceLocation.fromNamespaceAndPath("moredifficulties","trap_speed");
	public static final ResourceLocation HOSTILE_EXTRA_SPEED_ID = ResourceLocation.fromNamespaceAndPath("moredifficulties","hostile_speed");


	@Override
	public void onInitialize() {
	}

	public static Difficulty[] getOrder() {

		Difficulty[] DifficultyValues = Difficulty.values();
		Difficulty simple_value = Difficulty.valueOf("MOREDIFFICULTIES_SIMPLE");
		ArrayList<Difficulty> ordered = new ArrayList<>(Arrays.asList(DifficultyValues));
		ordered.remove(simple_value);
		ordered.add(ordered.indexOf(Difficulty.PEACEFUL)+1,simple_value);

        return ordered.toArray(new Difficulty[0]);
    }
}
