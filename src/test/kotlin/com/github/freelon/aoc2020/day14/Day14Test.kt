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
    fun part2() {
        val input = """mask = 000000000000000000000000000000X1001X
mem[42] = 100
mask = 00000000000000000000000000000000X0XX
mem[26] = 1
"""
        assertEquals(208L, Day14().partTwo(input))
    }
}
