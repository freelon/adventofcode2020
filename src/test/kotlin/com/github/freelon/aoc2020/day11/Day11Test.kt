package com.github.freelon.aoc2020.day11

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11Test {
    private val day = Day11()
    private val testInput = "L.LL.LL.LL\n" +
            "LLLLLLL.LL\n" +
            "L.L.L..L..\n" +
            "LLLL.LL.LL\n" +
            "L.LL.LL.LL\n" +
            "L.LLLLL.LL\n" +
            "..L.L.....\n" +
            "LLLLLLLLLL\n" +
            "L.LLLLLL.L\n" +
            "L.LLLLL.LL"

    private val i = ".......#.\n" +
            "...#.....\n" +
            ".#.......\n" +
            ".........\n" +
            "..#L....#\n" +
            "....#....\n" +
            ".........\n" +
            "#........\n" +
            "...#....."

    @Test
    fun testFirstHalf() {
        assertEquals(37, day.partOne(testInput))
    }

    @Test
    fun testSecondHalf() {
        assertEquals(26, day.partTwo(testInput))
    }
}
