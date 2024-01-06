package com.github.freelon.aoc2020.day22

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day22Test {

    private val input: String = """Player 1:
9
2
6
3
1

Player 2:
5
8
4
7
10
"""

    @Test
    fun partOne() {
        assertEquals(306, Day22().partOne(input))
    }

    @Test
    fun partTwo() {
    }
}