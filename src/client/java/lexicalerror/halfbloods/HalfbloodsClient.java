package lexicalerror.halfbloods;

import lexicalerror.halfbloods.components.PlayerComponent;
import lexicalerror.halfbloods.entities.ModEntitiesClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class HalfbloodsClient implements ClientModInitializer {
	private static KeyBinding activeAbility1Key;
	private static KeyBinding activeAbility2Key;

	@Override
	public void onInitializeClient() {
		ModEntitiesClient.registerModEntityRenderers();

		activeAbility1Key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"Active Ability 1", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
				GLFW.GLFW_KEY_C, // The keycode of the key
				"Halfbloods" // The translation key of the keybinding's category.
		));
		activeAbility2Key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"Active Ability 2", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
				GLFW.GLFW_KEY_X, // The keycode of the key
				"Halfbloods" // The translation key of the keybinding's category.
		));
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player == null)
				return;
            PlayerComponent component = ModComponents.PLAYER_COMPONENT.get(client.player);
			if (activeAbility1Key.wasPressed()) {
				component.sendC2SMessage(buf -> {buf.writeVarInt(0);});
			}
			if (activeAbility2Key.wasPressed()) {
				component.sendC2SMessage(buf -> {buf.writeVarInt(1);});
			}
		});
	}
}