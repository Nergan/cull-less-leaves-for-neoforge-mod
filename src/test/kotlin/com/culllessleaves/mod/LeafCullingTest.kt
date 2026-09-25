package com.culllessleaves.mod

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class LeafCullingTest {
    @Test
    fun outerLayerIsKeptWhenTheNeighborIsNotTheSameBlock() {
        val culled = LeafCulling.decide(depth = 2, rejectionChance = 1f, randomValue = 0f) { step -> step != 1 }
        assertFalse(culled)
    }

    @Test
    fun sideIsCulledWhenEveryLayerWithinDepthMatches() {
        val culled = LeafCulling.decide(depth = 2, rejectionChance = 0f, randomValue = 1f) { true }
        assertTrue(culled)
    }

    @Test
    fun innerHoleCanBeRejectedAtRandom() {
        val kept = LeafCulling.decide(depth = 2, rejectionChance = 0.2f, randomValue = 0.9f) { step -> step == 1 }
        val culled = LeafCulling.decide(depth = 2, rejectionChance = 0.2f, randomValue = 0.2f) { step -> step == 1 }
        assertFalse(kept)
        assertTrue(culled)
    }

    @Test
    fun seededRandomStaysOnTheSameBlock() {
        val first = LeafCulling.constantRandomSeeded(0x3FF_0000_0001L)
        val second = LeafCulling.constantRandomSeeded(0x3FF_0000_0001L)
        assertEquals(first, second)
        assertTrue(first in 0f..1f)
    }
}
