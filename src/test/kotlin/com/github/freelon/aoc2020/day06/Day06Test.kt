package com.github.freelon.aoc2020.day06

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day06Test {

    @Test
    fun testInput() {
        val input = "abc\n" +
                "\n" +
                "a\n" +
                "b\n" +
                "c\n" +
                "\n" +
                "ab\n" +
                "ac\n" +
                "\n" +
                "a\n" +
                "a\n" +
                "a\n" +
                "a\n" +
                "\n" +
                "b\n"

        assertEquals(11, firstHalf(input))
        assertEquals(6, secondHalf(input))
    }
}