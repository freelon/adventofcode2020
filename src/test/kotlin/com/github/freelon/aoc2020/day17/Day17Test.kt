package com.github.freelon.aoc2020.day17

import com.github.freelon.aoc2020.Day17
import com.github.freelon.aoc2020.P3
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

const val testInput = ".#.\n" +
        "..#\n" +
        "###\n"

class Day17Test {
    @Test
    fun neighbors() {
        assertEquals(26, P3(0, 0, 0).neighbors().size)
    }

    @Test
    fun part1() {
        assertEquals(112, Day17().partOne(testInput))
    }

    @Test
    fun part2() {
        assertEquals(848, Day17().partTwo(testInput))
    }
}