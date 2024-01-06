package com.github.freelon.aoc2020.day23

import com.github.freelon.aoc2020.DayTemplate

fun main() {
    Day23().run()
}

class Day23 : DayTemplate() {
    override fun partOne(input: String): String {
        var cups = input.split("").filterNot { it.isBlank() }.map { it.toInt() }
        var current = cups[0]
        repeat(100) {
            println("-- move ${it + 1} --")
            println("cups: " + cups.joinToString(" ") { cup -> if (cup == current) "($cup)" else "$cup" })
            val currentIndex = cups.indexOf(current)
            cups = cups.rotateLeft(currentIndex)
            val toMove = cups.subList(1, 4)
            cups = cups.subList(0, 1) + cups.subList(4, cups.size)
            println("pick up: ${toMove.joinToString()}")
            var destinationCup = current - 1
            while (!cups.contains(destinationCup)) {
                if (destinationCup == 0) {
                    destinationCup = 9
                    continue
                }
                destinationCup--
            }
            println("destination: $destinationCup")
            val destinationCupIndex = cups.indexOf(destinationCup)
            cups = cups.rotateLeft(destinationCupIndex + 1)
            cups = toMove + cups
            val newCurrentIndex = cups.indexOf(current)
            current = cups[(newCurrentIndex + 1) % cups.size]
            println()
        }
        return cups.rotateLeft(cups.indexOf(1)).drop(1).joinToString("")
    }

    override fun partTwo(input: String): Any {
        TODO("Not yet implemented")
    }
}

private fun <E> List<E>.rotateLeft(steps: Int): List<E> {
    return this.subList(steps, this.size) + this.subList(0, steps)
}
