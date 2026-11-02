package net.aleksandermg.moredifficulties;

import net.fabricmc.api.ModInitializer;

import net.minecraft.world.Difficulty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// important comment
public class MoreDifficulties implements ModInitializer {
	public static final String MOD_ID = "moredifficulties";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

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
