package com.github.freelon.aoc2020

import com.github.freelon.aoc2020.day13.Day13
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day13Test {
    @Test
    fun part1() {
        val result = Day13().partOne(EXAMPLE_INPUT)
        assertEquals(295L, result)
    }
}

const val EXAMPLE_INPUT = """939
7,13,x,x,59,x,31,19"""
