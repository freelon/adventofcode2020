package com.github.freelon.aoc2020.day25

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day25Test {

    private val testInput = "5764801\n17807724"

    @Test
    fun partOne() {
        assertEquals(14897079, Day25().partOne(testInput))
    }
}