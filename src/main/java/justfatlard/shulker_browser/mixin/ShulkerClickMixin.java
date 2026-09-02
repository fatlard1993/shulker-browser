package justfatlard.shulker_browser.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import justfatlard.shulker_browser.ShulkerBrowser;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * Right-clicking a shulker box opens it instead of picking it up.
 *
 * <p>Only with an empty hand, and only on a right-click. What that costs is picking a box up with
 * the right button, which is a thing the left button already does and which nobody does
 * deliberately - a shulker box does not stack, so the half-a-stack that right-click exists for has
 * never applied to one.
 *
 * <p>On the menu rather than on the item, because the point is to open a box that is still in your
 * pack. An item hook would only reach the one in your hand, and the box you want is always the
 * one at the bottom of the bag.
 */
@Mixin(AbstractContainerMenu.class)
public abstract class ShulkerClickMixin {

	@Shadow
	public NonNullList<Slot> slots;

	@Shadow
	public abstract ItemStack getCarried();

	@Inject(method = "clicked", at = @At("HEAD"), cancellable = true)
	private void shulkerBrowser$openInstead(int slotId, int button, ContainerInput input,
			Player player, CallbackInfo ci) {
		if (input != ContainerInput.PICKUP || button != 1) return;
		if (slotId < 0 || slotId >= this.slots.size()) return;
		if (!(player instanceof ServerPlayer opener)) return;
		if (!getCarried().isEmpty()) return;

		ItemStack stack = this.slots.get(slotId).getItem();
		if (!ShulkerBrowser.isShulker(stack)) return;

		// Queued rather than opened here. Opening a menu closes the one this click arrived on,
		// and tearing that down from inside its own click handler is asking the menu to finish
		// reading a message it has already been told to forget.
		opener.level().getServer().execute(() -> ShulkerBrowser.open(opener, stack));
		ci.cancel();
	}
}
