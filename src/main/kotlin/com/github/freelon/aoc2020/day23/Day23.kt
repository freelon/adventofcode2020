package com.github.freelon.aoc2020.day23

import com.github.freelon.aoc2020.DayTemplate

fun main() {
    Day23().run()
}

class Day23 : DayTemplate() {
    override fun partOne(input: String): String {
        val cups = play(input, 9, 100)
        return follow(cups).drop(1).joinToString("")
    }

    override fun partTwo(input: String): Long {
        val cups = play(input, 1_000_000, 10_000_000)
        return cups[1].successor.toLong() * cups[cups[1].successor].successor.toLong()
    }

    private fun play(input: String, nCups: Int, nRounds: Int): List<Cup> {
        val beginning = input.split("").filterNot { it.isBlank() }.map { it.toInt() }
        val cups = List(nCups + 1) {
            Cup(
                it, it - 1, if (it == nCups) {
                    1
                } else {
                    it + 1
                }
            )
        }
        var last = nCups
        for (b in beginning) {
            cups[b].predecessor = last
            cups[last].successor = b
            last = b
        }
        if (nCups > 9) {
            cups[last].successor = 10
            cups[10].predecessor = last
        } else {
            cups[last].successor = beginning.first()
            cups[beginning.first()].predecessor = last
        }

        var current = beginning.first()
        repeat(nRounds) {
            val pickUp = listOf(
                cups[current].successor,
                cups[cups[current].successor].successor,
                cups[cups[cups[current].successor].successor].successor
            )
            var destinationCup = current - 1
            while (destinationCup < 1 || pickUp.contains(destinationCup)) {
                if (destinationCup == 0) {
                    destinationCup = nCups
                    continue
                }
                destinationCup--
            }

            val nextToCurrent = cups[pickUp[2]].successor

            cups[current].successor = nextToCurrent
            cups[nextToCurrent].predecessor = current

            val nextToDestination = cups[destinationCup].successor

            cups[destinationCup].successor = pickUp[0]
            cups[pickUp[0]].predecessor = destinationCup
            cups[pickUp[2]].successor = nextToDestination
            cups[nextToDestination].predecessor = pickUp[2]

            current = cups[current].successor
        }
        return cups
    }

    private fun follow(cups: List<Cup>): List<Int> {
        val result = mutableListOf<Int>()
        var c = 1
        repeat(9) {
            result.add(cups[c].id)
            c = cups[c].successor
        }
        return result
    }
}

data class Cup(val id: Int, var predecessor: Int, var successor: Int)
