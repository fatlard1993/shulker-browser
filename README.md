# Shulker Browser

A Fabric mod that opens a shulker box where it sits, without placing it.

## What This Mod Does

**Right-click a shulker box in your inventory** and its contents open on a chest screen. Take
things out, put things in, close it. The box never leaves your pack.

The same idea [Bundle Browser](https://github.com/fatlard1993/bundle-browser) has for bundles, for
the container that actually gets carried around full.

## Right-Click, And Only Right-Click

Only with an empty hand, and only the right button. What that costs is picking a box up with the
right button - which the left button already does, and which nobody does on purpose: a shulker box
does not stack, so the half-a-stack that right-click exists for has never applied to one.

## The Other Way Round From Bundle Browser

Bundle Browser is client-side and works on any server. This is server-side and works for any
client, including one that has never heard of it.

That is not a preference, it is the only way round that exists. Vanilla lets you click items out of
a bundle, so a client mod can drive that with ordinary clicks; nothing in vanilla takes an item out
of a shulker box that has not been placed, so there is no interaction for a client to drive and the
server has to be the one holding the box open.

What the player gets is a real container menu, not a drawn one: the chest screen they already know,
with the shulker's own items in it, and every click is one vanilla already knows how to send.

## Details Worth Knowing

- **Written back on every change**, not on close. A screen can end without closing - a disconnect, a
  crash, a server going down - and a shulker that quietly forgot the last thing you put in it is
  worse than one you could not open at all.
- **No box inside a box.** Vanilla's rule, kept: the contents of an inner box stop being visible to
  anything looking at the outer one.
- **With [Chest Utils](https://github.com/fatlard1993/chest-utils) installed**, boxes open on its
  screen instead, and pick up the sort and tidy buttons - which a container full of somebody's
  overflow wants more than most do. Optional and reached by name, so this mod neither compiles nor
  runs against it.

## Installation

Install server-side. Clients need nothing at all. Version targets live in `gradle.properties`
(Minecraft, loader, Fabric API) and `fabric.mod.json` (Java).

## Key Files

| File | Responsibility |
|------|---------------|
| `Main.java` | Entry point, and not much else |
| `ShulkerBrowser.java` | Opening a box that is still in somebody's pocket |
| `ShulkerContents.java` | A box's contents, as a container you can work in |
| `mixin/ShulkerClickMixin.java` | Right-clicking a box opens it instead of picking it up |
| `integration/ChestUtilsScreen.java` | Showing one through Chest Utils, when it is installed |

## License

MIT, see [LICENSE](LICENSE).
