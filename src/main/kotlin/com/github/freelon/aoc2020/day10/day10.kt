package com.github.freelon.aoc2020.day10

import java.io.File

fun main() {

    try {
        val input = File("input/day10.txt").readText()

        println("Part 1: ${firstHalf(input)}")
        println("Part 2: ${secondHalf(input)}")
    } catch (e: Exception) {

        e.printStackTrace()
    }
}

fun firstHalf(input: String): Long {
    val adapters = createAdapterList(input)
    val diffMap = adapters.zipWithNext()
        .fold(mutableMapOf<Long, Long>()) { diffMap, (j, k) ->
            val d = k - j
            diffMap[d] = 1 + diffMap.getOrDefault(d, 0)
            diffMap
        }
    return diffMap.getOrDefault(1, 0) * diffMap.getOrDefault(3, 0)
}

fun secondHalf(input: String): Long {
    val adapters = createAdapterList(input)
    return adapters.possibleChainingCount(adapters.indices)
}

fun List<Long>.possibleChainingCount(range: IntRange, resultCache: MutableMap<IntRange, Long> = mutableMapOf()): Long {
    if (range.first + 1 == range.last)
        return 1
    if (this[range.first + 1] - this[range.first] > 3)
        return 0
    return (range.first + 1 until range.last).filter { this[it] - this[range.first] <= 3 }
        .map {
            val r = it..range.last
            resultCache.computeIfAbsent(r) {
                possibleChainingCount(r, resultCache)
            }
        }.sum()
}

private fun createAdapterList(input: String): List<Long> {
    var adapters = input.lines().filterNot { it.isEmpty() }.map { it.toLong() }
    adapters = adapters + 0
    adapters = adapters + (adapters.maxOrNull()!! + 3)
    adapters = adapters.sorted()
    return adapters
}
