# Cull Less Leaves

**[English](README.md)** · **[Русский](README.ru.md)**

![иконка](ico.png)

Порт [Cull Less Leaves](https://modrinth.com/mod/cull-less-leaves) от isXander на **Minecraft 1.21.1** / NeoForge. Написан на Kotlin с [Kotlin for Forge](https://modrinth.com/mod/kotlin-for-forge).

Экран настроек есть на английском и русском.

## Загрузки

Jar публикуются в [GitHub Releases](https://github.com/Nergan/cull-less-leaves-for-neoforge/releases/latest) и на [Modrinth](https://modrinth.com/project/cull-less-leaves-for-neoforge). Пуш в `main` обновляет файлы релиза текущей версии.

Скачайте эти файлы и положите их в папку `mods`:

| Файл | Обязателен | Что это |
| --- | --- | --- |
| `culllessleaves-1.4.3.jar` | Да | этот мод |
| `kotlinforforge-5.8.0-all.jar` | Да | [Kotlin for Forge](https://modrinth.com/mod/kotlin-for-forge) |
| `sodium-neoforge-0.6.13+mc1.21.1.jar` | Нет | [Sodium](https://modrinth.com/mod/sodium). Подойдёт и более новая сборка NeoForge 1.21.1 |

Сборку релиза делает workflow: он собирает мод и скачивает соседний jar с Modrinth. SHA-256 GitHub показывает рядом с файлом на странице релиза. `*-sources.jar` в `mods` класть не нужно.

[Sodium](https://modrinth.com/mod/sodium) для NeoForge 1.21.1, версия `0.6.13+mc1.21.1` или новее (включая 0.8.x), необязателен. Без него мод отсекает листву в обычном рендере. С ним то же отсечение встраивается в Sodium.

## Что делает мод

Cull Less Leaves убирает внутренние грани листвы и оставляет заданное число внешних слоёв. Крона остаётся плотнее, чем с Cull Leaves: тот мод оставляет только самый внешний слой.

Так же убираются соприкасающиеся блоки рыхлого снега. Корни мангра отсекаются, только если включена отдельная опция. Если стоит Sodium, глубина следует его качеству листвы: Fast всегда даёт глубину 1, Fancy берёт глубину из конфига.

## Требования

| Компонент | Версия |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.209 (подойдёт любой 21.1.x) |
| Kotlin for Forge | 5.8.0, сборка **NeoForge** |
| Java | 21 |
| Sodium | необязателен, `0.6.13+mc1.21.1` или новее для NeoForge 1.21.1 |

Мод клиентский. На выделенный сервер его ставить не нужно.

## Настройки

В игре: Моды → Cull Less Leaves → Config.

Файл: `config/culllessleaves-client.toml`.

| Опция | По умолчанию | Смысл |
| --- | --- | --- |
| `enabled` | `true` | убирать внутренние слои листвы |
| `depth` | `2` | сколько слоёв оставить, прежде чем убирать внутренность (1–4). На быстрой графике всегда 1 |
| `random_rejection` | `0.2` | вероятность от 0 до 1 убрать внутренний лист, который ещё входит в оставленную глубину |
| `fast_mangrove_roots` | `false` | убирать корни мангра рядом с такими же корнями, всегда с глубиной 1 |

Смена опции пересобирает видимые чанки.

## Лицензия

Этот порт — [LGPL-3.0-only](LICENSE), как и [Cull Less Leaves](https://github.com/isXander/CullLessLeaves) от isXander. Правило отсечения взято оттуда. Kotlin for Forge — LGPL-2.1. Sodium — Polyform Shield 1.0.0, сюда он не вкладывается.
