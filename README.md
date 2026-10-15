# Shulker Browser

A Fabric mod that opens a shulker box where it sits, without placing it.

## What This Mod Does

**Right-click a shulker box in your inventory** and its contents open on a chest screen. Take things out, put things in, close it. The box never leaves your pack.

The same idea [Bundle Browser](https://github.com/fatlard1993/bundle-browser) has for bundles, for the container that actually gets carried around full.

## Right-Click Only

Only with an empty hand, and only the right button. Picking a box up is left to the left button, which already does it.

## Details Worth Knowing

- **Written back on every change**, not on close. A screen can end without closing - a disconnect, a crash, a server going down - and a shulker that quietly forgot the last thing you put in it is worse than one you could not open at all.
- **Any open screen's slots**, not only your own pack's: a box sitting in an open chest opens the same way, and stays in the chest.
- **No box inside a box.** Vanilla's rule, kept: the contents of an inner box stop being visible to anything looking at the outer one.
- **With [Chest Utils](https://github.com/fatlard1993/chest-utils) installed**, boxes open on its screen instead, and pick up the sort and tidy buttons - which a container full of somebody's overflow wants more than most do.

## Installing

Server-side only, and Pandorical is not needed: it works for any client, including an unmodified one. Singleplayer works too.

## Development

Installing and the map of the source are in [DEVELOPMENT.md](DEVELOPMENT.md).

## License

MIT, see [LICENSE](LICENSE).
