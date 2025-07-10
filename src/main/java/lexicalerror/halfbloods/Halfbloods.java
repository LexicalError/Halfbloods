package lexicalerror.halfbloods;

import lexicalerror.halfbloods.commands.ModCommands;
import lexicalerror.halfbloods.entities.ModEntities;
import lexicalerror.halfbloods.items.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Halfbloods implements ModInitializer {
	public static final String MOD_ID = "halfbloods";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModCommands.register();
		ModEntities.registerModEntities();
		ModItems.initialize();
	}
}