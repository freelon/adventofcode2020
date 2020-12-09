package com.github.freelon.aoc2020.day09

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day09Test {
    private val testInput = "35\n" +
            "20\n" +
            "15\n" +
            "25\n" +
            "47\n" +
            "40\n" +
            "62\n" +
            "55\n" +
            "65\n" +
            "95\n" +
            "102\n" +
            "117\n" +
            "150\n" +
            "182\n" +
            "127\n" +
            "219\n" +
            "299\n" +
            "277\n" +
            "309\n" +
            "576"

    @Test
    fun testFirstHalf() {
        assertEquals(127, firstHalf(testInput, 5))
    }

    @Test
    fun testSecondHalf() {
        assertEquals(62, secondHalf(testInput, 5))
    }
}
