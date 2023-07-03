package com.github.freelon.aoc2020.day19

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day19Test {
    @Test
    fun part1() {
        val input = """0: 4 1 5
1: 2 3 | 3 2
2: 4 4 | 5 5
3: 4 5 | 5 4
4: "a"
5: "b"

ababbb
bababa
abbbab
aaabbb
aaaabbb
"""

        assertEquals(true, Solver(input).matches("ababbb"))
        assertEquals(false, Solver(input).matches("bababa"))
        assertEquals(true, Solver(input).matches("abbbab"))
        assertEquals(false, Solver(input).matches("aaabbb"))
        assertEquals(false, Solver(input).matches("aaaabbb"))

        assertEquals(2, Day19().partOne(input))
    }
}