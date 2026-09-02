package justfatlard.shulker_browser;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ShulkerBoxBlock;

/** Opening a shulker box that is still in somebody's pocket. */
public final class ShulkerBrowser {
	private ShulkerBrowser() {}

	public static boolean isShulker(ItemStack stack) {
		return stack.getItem() instanceof BlockItem placed
			&& placed.getBlock() instanceof ShulkerBoxBlock;
	}

	/**
	 * Show this box's contents on an ordinary chest screen.
	 *
	 * <p>A real container menu rather than a drawn one, which is the whole reason this works on a
	 * client that has never heard of the mod: what the player gets is the chest screen they
	 * already know, with the shulker's own items in it, and every click is a click vanilla
	 * already knows how to send.
	 */
	public static void open(ServerPlayer player, ItemStack box) {
		ShulkerContents contents = new ShulkerContents(box);
		Component title = box.getHoverName();

		// chest-utils, where it is installed, for the sort and tidy buttons a container screen
		// full of somebody's overflow wants more than most.
		if (justfatlard.shulker_browser.integration.ChestUtilsScreen.show(player, contents, title, 3)) {
			return;
		}

		player.openMenu(new SimpleMenuProvider(
			(syncId, inventory, opener) -> ChestMenu.threeRows(syncId, inventory, contents), title));
	}
}
