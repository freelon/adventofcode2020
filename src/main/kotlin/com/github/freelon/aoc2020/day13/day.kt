package com.github.freelon.aoc2020.day13

import com.github.freelon.aoc2020.DayTemplate
import java.time.Duration
import java.time.Instant

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
        Thread {
            val start = Instant.now()
            while (true) {
                println("${Duration.between(start, Instant.now()).seconds}s -- %,d".format(foo))
                Thread.sleep(1000)
            }
        }.start()
        return solve(input.lines()[1].split(",").mapNotNull { it.toLongOrNull() }, offset = 175710168444071L)
    }
}

var foo = 0L

fun solve(busses: List<Long?>, offset: Long = 100000000000000): Long {
    val indexedRelevantBusses = busses
        .withIndex()
        .mapNotNull { if (it.value != null) IndexedValue(it.index, it.value!!) else null }
    val mainBus = indexedRelevantBusses.maxBy { it.value }
    val startTime = (offset + 1..Long.MAX_VALUE).first { time -> (time + mainBus.index.toLong()) % mainBus.value == 0L }
    val times = LongRange(startTime, Long.MAX_VALUE).step(mainBus.value)
    return times
        .first { t ->
            foo = t
            indexedRelevantBusses.all { (index, bus) ->
                (t + index) % bus == 0L
            }
        }
}

fun main() {
    Day13().run()
}