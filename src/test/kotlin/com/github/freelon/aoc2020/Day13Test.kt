package com.github.freelon.aoc2020

import com.github.freelon.aoc2020.day13.Day13
import com.github.freelon.aoc2020.day13.solve
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day13Test {
    @Test
    fun part1() {
        val result = Day13().partOne(EXAMPLE_INPUT)
        assertEquals(295L, result)
    }

    @Test
    fun `part1 value`() {
        val result =
            Day13().partOne("1000510\n19,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,x,523,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,17,13,x,x,x,x,x,x,x,x,x,x,29,x,853,x,x,x,x,x,37,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,23\n")
        assertEquals(259L, result)
    }

    @Test
    fun `part2 examples`() {
        assertEquals(1068781L, solve(listOf(7, 13, null, null, 59, null, 31, 19), 0))

        assertEquals(3417L, solve(listOf(17, null, 13, 19), 0))
        assertEquals(754018L, solve(listOf(67, 7, 59, 61), 0))
        assertEquals(779210L, solve(listOf(67, null, 7, 59, 61), 0))
        assertEquals(1261476L, solve(listOf(67, 7, null, 59, 61), 0))
        assertEquals(1202161486L, solve(listOf(1789, 37, 47, 1889), 0))
    }
}

const val EXAMPLE_INPUT = """939
7,13,x,x,59,x,31,19"""
