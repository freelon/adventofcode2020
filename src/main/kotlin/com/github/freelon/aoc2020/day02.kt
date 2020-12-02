package com.github.freelon.aoc2020

import java.io.File

fun main() {
    val input = File("input/day02.txt").readLines()

    val regex = Regex("(\\d+)-(\\d+) (\\w): (\\w+)")
    val passwords = input.filter { regex.matches(it) }
        .map {
            val matchEntire = regex.matchEntire(it)!!
            Line(
                Integer.parseInt(matchEntire.groupValues[1]),
                Integer.parseInt(matchEntire.groupValues[2]),
                matchEntire.groupValues[3][0],
                matchEntire.groupValues[4]
            )
        }

    firstHalf(passwords)
    secondHalf(passwords)
}

private fun firstHalf(input: List<Line>) {
    val count = input
        .filter(Line::isPasswordOkayByOldPolicy)
        .count()

    println("Count part 1: $count")
}

private fun secondHalf(input: List<Line>) {
    val count = input
        .filter(Line::isPasswordOkayTobogganPolicy)
        .count()

    println("Count part 1: $count")
}

data class Line(val nMin: Int, val nMax: Int, val c: Char, val password: String) {

    fun isPasswordOkayByOldPolicy(): Boolean =
        password.count { it == c } in nMin..nMax

    fun isPasswordOkayTobogganPolicy(): Boolean = (password[nMin - 1] == c) xor (password[nMax - 1] == c)
}
