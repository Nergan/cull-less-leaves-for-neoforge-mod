package com.culllessleaves.mod.config

import com.culllessleaves.mod.CullLessLeavesMod
import net.neoforged.fml.ModList
import net.neoforged.neoforge.common.ModConfigSpec

class ClientConfig(builder: ModConfigSpec.Builder) {
    val enabled: ModConfigSpec.BooleanValue
    val depth: ModConfigSpec.IntValue
    val randomRejection: ModConfigSpec.DoubleValue
    val fastMangroveRoots: ModConfigSpec.BooleanValue

    init {
        builder.push("culling")
        enabled = builder
            .comment(
                "Cull inner leaf layers. The outer layer stays.",
                "Убирать внутренние слои листвы. Внешний слой остаётся.",
            )
            .translation("$PREFIX.culling.enabled")
            .define("enabled", true)
        depth = builder
            .comment(
                "How many leaf layers to keep before the inside is culled. 1 is the thinnest, 2 is the original default, 4 keeps the most leaves.",
                "Сколько слоёв листвы оставить, прежде чем убирать внутренность. 1 — самый тонкий, 2 — как в оригинале, 4 — больше всего листьев.",
            )
            .translation("$PREFIX.culling.depth")
            .defineInRange("depth", 2, 1, 4)
        randomRejection = builder
            .comment(
                "Chance, from 0 to 1, to still cull a leaf that sits inside the kept depth but is not on the outer layer.",
                "Вероятность от 0 до 1 всё же убрать лист внутри оставленной глубины, если он не на внешнем слое.",
            )
            .translation("$PREFIX.culling.random_rejection")
            .defineInRange("random_rejection", 0.2, 0.0, 1.0)
        fastMangroveRoots = builder
            .comment(
                "Also cull mangrove roots that touch another mangrove root. Roots generate in a single layer, so the depth is always 1.",
                "Также убирать корни мангрового дерева, если рядом такой же корень. Корни растут одним слоем, поэтому глубина всегда 1.",
            )
            .translation("$PREFIX.culling.fast_mangrove_roots")
            .define("fast_mangrove_roots", false)
        builder.pop()
    }

    companion object {
        private const val PREFIX = "${CullLessLeavesMod.MOD_ID}.configuration"

        val SPEC: ModConfigSpec
        val CONFIG: ClientConfig

        init {
            val pair = ModConfigSpec.Builder().configure(::ClientConfig)
            CONFIG = pair.left
            SPEC = pair.right
        }
    }
}

object CullSettings {
    fun enabled(): Boolean = if (ClientConfig.SPEC.isLoaded) ClientConfig.CONFIG.enabled.get() else true

    fun depth(): Int = if (ClientConfig.SPEC.isLoaded) ClientConfig.CONFIG.depth.get() else 2

    fun randomRejection(): Float =
        if (ClientConfig.SPEC.isLoaded) ClientConfig.CONFIG.randomRejection.get().toFloat() else 0.2f

    fun fastMangroveRoots(): Boolean =
        if (ClientConfig.SPEC.isLoaded) ClientConfig.CONFIG.fastMangroveRoots.get() else false

    fun leafDepth(): Int = if (FancyLeaves.isFancy()) depth() else 1
}

object FancyLeaves {
    private val sodiumFancy: ((Any) -> Boolean)? = bindSodium()

    fun isFancy(): Boolean {
        val graphics = net.minecraft.client.Minecraft.getInstance().options.graphicsMode().get()
        val fromSodium = sodiumFancy
        if (fromSodium != null) {
            try {
                return fromSodium(graphics)
            } catch (_: Throwable) {
                // Sodium ещё не прочитал свой конфиг — берём графику ванили.
            }
        }
        return net.minecraft.client.Minecraft.useFancyGraphics()
    }

    private fun bindSodium(): ((Any) -> Boolean)? {
        if (!ModList.get().isLoaded("sodium")) return null
        return try {
            val sodium = Class.forName("net.caffeinemc.mods.sodium.client.SodiumClientMod")
            val options = sodium.getMethod("options")
            val qualityField = options.returnType.getField("quality")
            val leavesField = qualityField.type.getField("leavesQuality")
            val isFancy = leavesField.type.methods.first {
                it.name == "isFancy" && it.parameterCount == 1
            }
            return { graphics ->
                val opts = options.invoke(null)
                val leaves = leavesField.get(qualityField.get(opts))
                isFancy.invoke(leaves, graphics) as Boolean
            }
        } catch (_: Throwable) {
            null
        }
    }
}
