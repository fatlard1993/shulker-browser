package justfatlard.shulker_browser.integration;

import java.lang.reflect.Method;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;

/**
 * Shows a box's contents through chest-utils, when it is installed.
 *
 * <p>Reached by name rather than by importing it, so this mod neither compiles nor runs against
 * chest-utils: without it the lookup fails once, is remembered as absent, and every box opens on
 * the plain chest screen it always did.
 */
public final class ChestUtilsScreen {
	private ChestUtilsScreen() {}

	private static final String CLASS = "justfatlard.chest_utils.screen.ChestScreens";

	private static boolean looked = false;
	private static Method open = null;

	public static boolean show(ServerPlayer player, Container container, Component title, int rows) {
		Method method = resolve();
		if (method == null) return false;

		try {
			method.invoke(null, player, container, title, rows);
			return true;
		} catch (ReflectiveOperationException | RuntimeException e) {
			// Once is a mishap; every box after this one opens the plain way.
			open = null;
			return false;
		}
	}

	private static synchronized Method resolve() {
		if (looked) return open;
		looked = true;

		if (!FabricLoader.getInstance().isModLoaded("chest-utils")) return null;

		try {
			open = Class.forName(CLASS).getMethod(
				"open", ServerPlayer.class, Container.class, Component.class, int.class);
		} catch (ReflectiveOperationException e) {
			open = null;
		}
		return open;
	}
}
