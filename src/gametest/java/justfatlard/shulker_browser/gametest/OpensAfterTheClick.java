package justfatlard.shulker_browser.gametest;

import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestServerConnection;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestServerContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * A right-click on a shulker box in the inventory opens it, after the click is done with rather than
 * inside it: the menu the click arrived on is still the one open when the click returns.
 */
public final class OpensAfterTheClick implements FabricClientGameTest {
	/** The inventory menu's slot for the first hotbar square. */
	private static final int HOTBAR_FIRST = 36;

	@Override
	public void runTest(ClientGameTestContext context) {
		try (TestSingleplayerContext world = context.worldBuilder().create()) {
			TestServerContext server = world.getServer();
			TestServerConnection connection = world.getConnection();
			connection.waitForChunksRender();

			server.runOnServer(s -> {
				ServerPlayer player = connection.getServerPlayer();
				player.getInventory().setItem(0, new ItemStack(Items.SHULKER_BOX));
				player.inventoryMenu.clicked(HOTBAR_FIRST, 1, ContainerInput.PICKUP, player);
				check(player.containerMenu == player.inventoryMenu,
					"the box opened inside the click that asked for it: " + player.containerMenu);
			});
			server.waitFor(s -> connection.getServerPlayer().containerMenu instanceof ChestMenu, 40);
		}
		context.waitTicks(10);
	}

	private static void check(boolean ok, String what) {
		if (!ok) throw new AssertionError(what);
	}
}
