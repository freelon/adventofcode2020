package com.github.freelon.aoc2020.day19

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
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

    @Test
    fun part2() {
        assertTrue(Solver(EXAMPLE_BIG).matchesFunky("bbabbbbaabaabba"))
        assertTrue(Solver(EXAMPLE_BIG).matchesFunky("babbbbaabbbbbabbbbbbaabaaabaaa"))
        assertTrue(Solver(EXAMPLE_BIG).matchesFunky("aaabbbbbbaaaabaababaabababbabaaabbababababaaa"))
        assertTrue(Solver(EXAMPLE_BIG).matchesFunky("bbbbbbbaaaabbbbaaabbabaaa"))
        assertTrue(Solver(EXAMPLE_BIG).matchesFunky("bbbababbbbaaaaaaaabbababaaababaabab"))
        assertTrue(Solver(EXAMPLE_BIG).matchesFunky("ababaaaaaabaaab"))
        assertTrue(Solver(EXAMPLE_BIG).matchesFunky("ababaaaaabbbaba"))
        assertTrue(Solver(EXAMPLE_BIG).matchesFunky("baabbaaaabbaaaababbaababb"))
        assertTrue(Solver(EXAMPLE_BIG).matchesFunky("abbbbabbbbaaaababbbbbbaaaababb"))
        assertTrue(Solver(EXAMPLE_BIG).matchesFunky("aaaaabbaabaaaaababaa"))
        assertTrue(Solver(EXAMPLE_BIG).matchesFunky("aaaabbaabbaaaaaaabbbabbbaaabbaabaaa"))
        assertTrue(Solver(EXAMPLE_BIG).matchesFunky("aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba"))
        assertFalse(Solver(EXAMPLE_BIG).matchesFunky("abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa"))
        assertFalse(Solver(EXAMPLE_BIG).matchesFunky("aaaabbaaaabbaaa"))
        assertFalse(Solver(EXAMPLE_BIG).matchesFunky("babaaabbbaaabaababbaabababaaab"))

        assertEquals(12, Day19().partTwo(EXAMPLE_BIG))
    }

    @Test
    fun single() {
        assertTrue(Solver(EXAMPLE_BIG).matchesFunky("babbbbaabbbbbabbbbbbaabaaabaaa"))
    }

    @Test
    fun splitter() {
        val input = "aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba"
        for ((a, b) in input.splits())
            assertEquals(input, a.toString() + b)

        assertEquals(listOf(Pair("1", "234"), Pair("12", "34"), Pair("123", "4"), Pair("1234", "")), "1234".splits())
    }

    @Test
    fun `expansion for small example`() {
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
        val all = Solver(input).expand(0)

        assertEquals(true, "ababbb" in all)
        assertEquals(false, "bababa" in all)
        assertEquals(true, "abbbab" in all)
        assertEquals(false, "aaabbb" in all)
        assertEquals(false, "aaaabbb" in all)

        println("Size: ${all.size}")
    }

    @Test
    fun `expansion for big example`() {
        val input = EXAMPLE_BIG
        val all = Solver(input).expand(0)
        println("Size: ${all.size}")

        assertTrue("bbabbbbaabaabba" in all)
        assertTrue("babbbbaabbbbbabbbbbbaabaaabaaa" in all)
        assertTrue("aaabbbbbbaaaabaababaabababbabaaabbababababaaa" in all)
        assertTrue("bbbbbbbaaaabbbbaaabbabaaa" in all)
        assertTrue("bbbababbbbaaaaaaaabbababaaababaabab" in all)
        assertTrue("ababaaaaaabaaab" in all)
        assertTrue("ababaaaaabbbaba" in all)
        assertTrue("baabbaaaabbaaaababbaababb" in all)
        assertTrue("abbbbabbbbaaaababbbbbbaaaababb" in all)
        assertTrue("aaaaabbaabaaaaababaa" in all)
        assertTrue("aaaabbaabbaaaaaaabbbabbbaaabbaabaaa" in all)
        assertTrue("aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba" in all)
        assertFalse("abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa" in all)
        assertFalse("aaaabbaaaabbaaa" in all)
        assertFalse("babaaabbbaaabaababbaabababaaab" in all)
    }
}

private const val EXAMPLE_BIG = """42: 9 14 | 10 1
9: 14 27 | 1 26
10: 23 14 | 28 1
1: "a"
11: 42 31
5: 1 14 | 15 1
19: 14 1 | 14 14
12: 24 14 | 19 1
16: 15 1 | 14 14
31: 14 17 | 1 13
6: 14 14 | 1 14
2: 1 24 | 14 4
0: 8 11
13: 14 3 | 1 12
15: 1 | 14
17: 14 2 | 1 7
23: 25 1 | 22 14
28: 16 1
4: 1 1
20: 14 14 | 1 15
3: 5 14 | 16 1
27: 1 6 | 14 18
14: "b"
21: 14 1 | 1 14
25: 1 1 | 1 14
22: 14 14
8: 42
26: 14 22 | 1 20
18: 15 15
7: 14 5 | 1 21
24: 14 1

abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
bbabbbbaabaabba
babbbbaabbbbbabbbbbbaabaaabaaa
aaabbbbbbaaaabaababaabababbabaaabbababababaaa
bbbbbbbaaaabbbbaaabbabaaa
bbbababbbbaaaaaaaabbababaaababaabab
ababaaaaaabaaab
ababaaaaabbbaba
baabbaaaabbaaaababbaababb
abbbbabbbbaaaababbbbbbaaaababb
aaaaabbaabaaaaababaa
aaaabbaaaabbaaa
aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
babaaabbbaaabaababbaabababaaab
aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba"""
