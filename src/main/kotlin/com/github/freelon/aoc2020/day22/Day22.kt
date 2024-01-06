package com.github.freelon.aoc2020.day22

import com.github.freelon.aoc2020.DayTemplate

fun main() {
    Day22().run()
}

class Day22 : DayTemplate() {
    override fun partOne(input: String): Int {
        val players = input.trim().split("\n\n").map { block ->
            block.lines().drop(1).map { it.toInt() }.toMutableList()
        }
        while (players.all { it.isNotEmpty() }) {
            val a = players[0].removeAt(0)
            val b = players[1].removeAt(0)
            if (a > b) {
                players[0].add(a)
                players[0].add(b)
            } else {
                players[1].add(b)
                players[1].add(a)
            }
        }
        return players.find { it.isNotEmpty() }!!.reversed().mapIndexed { index, i -> i * (index + 1) }.sum()
    }

    override fun partTwo(input: String): Any {
        TODO("Not yet implemented")
    }
}