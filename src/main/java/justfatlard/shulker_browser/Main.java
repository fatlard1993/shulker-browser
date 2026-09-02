package justfatlard.shulker_browser;

import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
	public static final String MOD_ID = "shulker-browser-justfatlard";

	@Override
	public void onInitialize() {
		// Nothing to register. The whole mod is one interception and a container view of an item
		// that already holds everything it needs.
		System.out.println("[" + MOD_ID + "] Loaded");
	}
}
