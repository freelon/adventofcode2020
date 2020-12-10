package com.github.freelon.aoc2020

import com.github.freelon.aoc2020.day10.Day10
import com.github.freelon.aoc2020.util.ConsoleColor
import java.io.File
import java.lang.Exception
import kotlin.system.measureTimeMillis

abstract class DayTemplate {

    abstract val dayNumber: Int

    fun run() {
        try {
            val input = File("input${File.separatorChar}day$dayNumber.txt").readText()
            println("Running for ~~ Day $dayNumber ~~\n")
            measureTimeMillis {
                println("Part One: ${ConsoleColor.GREEN_BOLD}${partOne(input)}${ConsoleColor.RESET}")
            }.also { println("Part one took ${it}ms") }
            measureTimeMillis {
                println("Part Two: ${ConsoleColor.GREEN_BOLD}${partTwo(input)}${ConsoleColor.RESET}")
            }.also { println("Part one took ${it}ms") }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    abstract fun partOne(input: String): String
    abstract fun partTwo(input: String): String
}

fun main() {
    Day10().run()
}
