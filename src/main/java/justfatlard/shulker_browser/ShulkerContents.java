package justfatlard.shulker_browser;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

/**
 * A shulker box's contents, as a container you can actually work in.
 *
 * <p>The box is an item and its contents are a component on that item, which is a picture of an
 * inventory rather than an inventory: nothing can be put into it or taken out of it in place. This
 * unpacks that picture into a real container, lets a menu use it, and paints it back onto the item
 * every time something moves.
 *
 * <p>Written back on every change rather than on close, because a screen can end without closing -
 * a disconnect, a crash, a server going down - and a shulker that quietly forgot the last thing
 * you put in it is worse than one you could not open at all.
 */
public final class ShulkerContents extends SimpleContainer {
	/** A shulker box holds a chest's worth, which is what the menu below it assumes too. */
	public static final int SLOTS = 27;

	private final ItemStack box;

	public ShulkerContents(ItemStack box) {
		super(SLOTS);
		this.box = box;

		NonNullList<ItemStack> unpacked = NonNullList.withSize(SLOTS, ItemStack.EMPTY);
		box.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(unpacked);

		for (int slot = 0; slot < SLOTS; slot++) {
			setItem(slot, unpacked.get(slot));
		}
	}

	/** The box this is a view of, so a caller can check it is still the one it opened. */
	public ItemStack box() {
		return this.box;
	}

	@Override
	public void setChanged() {
		super.setChanged();

		java.util.List<ItemStack> items = new java.util.ArrayList<>(SLOTS);
		for (int slot = 0; slot < SLOTS; slot++) {
			items.add(getItem(slot));
		}
		this.box.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(items));
	}

	/**
	 * Whether this box is allowed to hold that.
	 *
	 * <p>No shulker inside a shulker, which is vanilla's rule and worth keeping: a box that holds
	 * boxes is a bag of holding, and the reason vanilla says no is that the contents of the inner
	 * one stop being visible to anything that looks at the outer one.
	 */
	@Override
	public boolean canPlaceItem(int slot, ItemStack stack) {
		return stack.isEmpty() || stack.getItem().canFitInsideContainerItems();
	}
}
