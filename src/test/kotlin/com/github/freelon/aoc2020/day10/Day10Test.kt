package com.github.freelon.aoc2020.day10

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day10Test {
    private val day = Day10()
    private val testInput1 = "16\n" +
            "10\n" +
            "15\n" +
            "5\n" +
            "1\n" +
            "11\n" +
            "7\n" +
            "19\n" +
            "6\n" +
            "12\n" +
            "4"

    private val testInput2 = "28\n" +
            "33\n" +
            "18\n" +
            "42\n" +
            "31\n" +
            "14\n" +
            "46\n" +
            "20\n" +
            "48\n" +
            "47\n" +
            "24\n" +
            "23\n" +
            "49\n" +
            "45\n" +
            "19\n" +
            "38\n" +
            "39\n" +
            "11\n" +
            "1\n" +
            "32\n" +
            "25\n" +
            "35\n" +
            "8\n" +
            "17\n" +
            "7\n" +
            "9\n" +
            "4\n" +
            "2\n" +
            "34\n" +
            "10\n" +
            "3\n"

    @Test
    fun testFirstHalf() {
        assertEquals((7 * 5).toString(), day.partOne(testInput1))
        assertEquals((22 * 10).toString(), day.partOne(testInput2))
    }

    @Test
    fun testSecondHalf() {
        assertEquals("8", day.partTwo(testInput1))
        assertEquals("19208", day.partTwo(testInput2))
    }
}
