package com.github.freelon.aoc2020.day13

import com.github.freelon.aoc2020.DayTemplate

class Day13 : DayTemplate() {

    override fun partOne(input: String): Long {
        val (first, second) = input.lines()
        val startTime = first.toLong()
        val busFrequencies = second.split(",").mapNotNull { it.toLongOrNull() }
        val departureTime = (startTime..Long.MAX_VALUE).first { time ->
            busFrequencies.any { bus -> (time % bus) == 0L }
        }
        val bus = busFrequencies.first() { bus -> (departureTime % bus) == 0L }
        val waitingTime = departureTime - startTime
        return bus * waitingTime
    }

    override fun partTwo(input: String): Any {
        return ""
    }
}
