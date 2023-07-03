package com.github.freelon.aoc2020.day18

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day18Test {
    @Test
    fun part1() {
        assertEquals(26L, Day18().partOne("2 * 3 + (4 * 5)"))
        assertEquals(437L, Day18().partOne("5 + (8 * 3 + 9 + 3 * 4 * 3)"))
        assertEquals(12240L, Day18().partOne("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"))
        assertEquals(13632L, Day18().partOne("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"))
    }

    @Test
    fun part2() {
        assertEquals(51L, Day18().partTwo("1 + (2 * 3) + (4 * (5 + 6))"))
        assertEquals(46L, Day18().partTwo("2 * 3 + (4 * 5)"))
        assertEquals(1445L, Day18().partTwo("5 + (8 * 3 + 9 + 3 * 4 * 3)"))
        assertEquals(669060L, Day18().partTwo("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"))
        assertEquals(23340L, Day18().partTwo("11664+2+4*2"))
        assertEquals(23340L, Day18().partTwo("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"))
    }

    @Test
    fun regex() {
        var m = subExpressionPattern.matcher("(1)")
        assertTrue(m.matches())

        m = subExpressionPattern.matcher("2+3*(1)+3")
        assertTrue(m.matches())
        var result = m.toMatchResult()
        assertEquals("(1)", result.group(1))

        m = subExpressionPattern.matcher("2*3+(4*5)")
        assertTrue(m.matches())
        result = m.toMatchResult()
        assertEquals("(4*5)", result.group(1))
    }
}