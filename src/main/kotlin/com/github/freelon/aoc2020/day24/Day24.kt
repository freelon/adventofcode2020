package com.github.freelon.aoc2020.day24

import com.github.freelon.aoc2020.DayTemplate

fun main() {
    Day24().run()
}

class Day24 : DayTemplate() {
    override fun partOne(input: String): Int {
        val blackTiles = mutableSetOf<Pair<Int, Int>>()
        input.lines().forEach { line ->
            val p = walk(line)
            if (blackTiles.contains(p)) {
                blackTiles.remove(p)
            } else {
                blackTiles.add(p)
            }
        }

        return blackTiles.size
    }

    private fun walk(line: String): Pair<Int, Int> {
        var x = 0
        var y = 0
        var rem = line
        while (rem.isNotEmpty()) {
            if (rem.startsWith("nw")) {
                y++
                x--
                rem = rem.substring(2)
            } else if (rem.startsWith("ne")) {
                y++
                x++
                rem = rem.substring(2)
            } else if (rem.startsWith("e")) {
                x += 2
                rem = rem.substring(1)
            } else if (rem.startsWith("se")) {
                y--
                x++
                rem = rem.substring(2)
            } else if (rem.startsWith("sw")) {
                y--
                x--
                rem = rem.substring(2)
            } else if (rem.startsWith("w")) {
                x -= 2
                rem = rem.substring(1)
            } else {
                throw IllegalArgumentException("bad start $rem")
            }
        }

        return Pair(x, y)
    }

    override fun partTwo(input: String): Any {
        TODO("Not yet implemented")
    }
}