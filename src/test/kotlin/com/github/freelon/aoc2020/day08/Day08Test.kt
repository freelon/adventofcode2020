package com.github.freelon.aoc2020.day08

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day08Test {
    private val testInput = "nop +0\n" +
            "acc +1\n" +
            "jmp +4\n" +
            "acc +3\n" +
            "jmp -3\n" +
            "acc -99\n" +
            "acc +1\n" +
            "jmp -4\n" +
            "acc +6"

    @Test
    fun testInput() {
        assertEquals(5, firstHalf(testInput))
        assertEquals(8, secondHalf(testInput))
    }
}
