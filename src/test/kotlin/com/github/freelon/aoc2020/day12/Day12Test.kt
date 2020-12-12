package com.github.freelon.aoc2020.day12

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day12Test {
    private val day = Day12()
    private val testInput = "F10\n" +
            "N3\n" +
            "F7\n" +
            "R90\n" +
            "F11"

    @Test
    fun testFirstHalf() {
        assertEquals(25, day.partOne(testInput))
    }

    @Test
    fun testSecondHalf() {
        assertEquals(286, day.partTwo(testInput))
    }
}
