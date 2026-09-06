# Shulker Browser - Development Guide

For what the mod is and how it plays, see [README.md](README.md).

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
