package com.culllessleaves.mod

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import java.util.function.Predicate

/**
 * Решение, прятать ли грань. Вынесено из [isXander/CullLessLeaves](https://github.com/isXander/CullLessLeaves)
 * (LGPL-3.0-only): внешний слой не трогаем, внутри глубины случайно оставляем часть блоков.
 */
object LeafCulling {
    @JvmStatic
    fun shouldCullSide(
        depth: Int,
        view: BlockGetter,
        pos: BlockPos,
        facing: Direction,
        rejectionChance: Float,
        blockCheck: Predicate<Block>,
    ): Boolean {
        val step = facing.normal
        return decide(depth, rejectionChance, constantRandomSeeded(pos.asLong())) { steps ->
            val state = view.getBlockState(pos.offset(step.multiply(steps)))
            blockCheck.test(state.block)
        }
    }

    fun decide(
        depth: Int,
        rejectionChance: Float,
        randomValue: Float,
        sameAtStep: (Int) -> Boolean,
    ): Boolean {
        var cull = true
        var outerMiss = false
        for (step in 1..depth) {
            cull = cull && sameAtStep(step)
            if (!cull && step == 1) {
                outerMiss = true
            }
        }
        if (!outerMiss && !cull && randomValue <= rejectionChance) {
            cull = true
        }
        return cull
    }

    /** Тот же генератор, что в оригинале: seed от упакованной позиции блока. */
    fun constantRandomSeeded(packedPos: Long): Float {
        var seed = (packedPos xor 25214903917L) and 281474976710655L
        seed = (seed * 25214903917L + 11L) and 281474976710655L
        return (seed shr 24).toInt() * 5.9604645E-8f
    }
}
