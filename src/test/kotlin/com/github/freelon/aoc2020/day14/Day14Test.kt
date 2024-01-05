package com.github.freelon.aoc2020.day14

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day14Test {
    private val example = """mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
mem[8] = 11
mem[7] = 101
mem[8] = 0
"""

    @Test
    fun part1() {
        val result = Day14().partOne(example)
        assertEquals(165L, result)
    }

    @Test
    fun `part2 examples`() {

    }
}
