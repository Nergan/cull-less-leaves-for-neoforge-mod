# Cull Less Leaves

**[English](README.md)** · **[Русский](README.ru.md)**

![icon](ico.png)

A **Minecraft 1.21.1** NeoForge port of [Cull Less Leaves](https://modrinth.com/mod/cull-less-leaves) by isXander. Written in Kotlin with [Kotlin for Forge](https://modrinth.com/mod/kotlin-for-forge).

The config screen is available in English and Russian.

## Downloads

Jars are published to [GitHub Releases](https://github.com/Nergan/cull-less-leaves-for-neoforge/releases/latest) and [Modrinth](https://modrinth.com/project/cull-less-leaves-for-neoforge). A push to `main` updates the files on the current version’s release.

Download these files and put them in the `mods` folder:

| File | Required | What it is |
| --- | --- | --- |
| `culllessleaves-1.4.2.jar` | Yes | this mod |
| `kotlinforforge-5.8.0-all.jar` | Yes | [Kotlin for Forge](https://modrinth.com/mod/kotlin-for-forge) |

The release workflow builds the mod and fetches the companion jar from Modrinth. GitHub shows a SHA-256 digest next to each file on the release page. Do not install `*-sources.jar`.

[Sodium](https://modrinth.com/mod/sodium) for NeoForge 1.21.1, version `0.6.13+mc1.21.1` or newer (including 0.8.x), is optional. Without it the mod still culls leaves in the vanilla renderer. With it, the same culling is applied inside Sodium.

## What it does

Cull Less Leaves skips inner leaf faces and keeps a configurable number of outer layers, so trees stay fuller than with Cull Leaves (that mod keeps only the outermost layer).

It also culls touching powder snow, and can cull mangrove roots when that option is on. Depth follows Sodium’s leaves quality when Sodium is installed: Fast leaves use depth 1, Fancy leaves use the configured depth.

## Requirements

| Component | Version |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.209 (any 21.1.x should work) |
| Kotlin for Forge | 5.8.0, **NeoForge** build |
| Java | 21 |
| Sodium | optional, `0.6.13+mc1.21.1` or newer for NeoForge 1.21.1 |

The mod is client-side. It does not have to be installed on a dedicated server.

## Configuration

In-game: Mods → Cull Less Leaves → Config.

The file is `config/culllessleaves-client.toml`.

| Option | Default | Meaning |
| --- | --- | --- |
| `enabled` | `true` | cull inner leaf layers |
| `depth` | `2` | layers to keep before the inside is culled (1–4). Fast graphics always use 1 |
| `random_rejection` | `0.2` | chance from 0 to 1 to cull an inner leaf that is still inside the kept depth |
| `fast_mangrove_roots` | `false` | cull mangrove roots against other mangrove roots, always at depth 1 |

Changing an option rebuilds visible chunks.

## License

This port is [LGPL-3.0-only](LICENSE), the same license as [Cull Less Leaves](https://github.com/isXander/CullLessLeaves) by isXander. The culling rule is taken from that mod. Kotlin for Forge is LGPL-2.1. Sodium is Polyform Shield 1.0.0 and is not redistributed here.
