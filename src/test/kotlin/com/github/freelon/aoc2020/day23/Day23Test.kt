package com.github.freelon.aoc2020.day23

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day23Test {

    @Test
    fun partOne() {
        assertEquals("67384529", Day23().partOne("389125467"))
    }

    @Test
    fun partTwo() {
        assertEquals(149245887792L, Day23().partTwo("389125467"))
    }
}