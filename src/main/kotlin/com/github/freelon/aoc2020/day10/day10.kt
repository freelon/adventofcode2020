package com.github.freelon.aoc2020.day10

import com.github.freelon.aoc2020.DayTemplate

class Day10 : DayTemplate() {
    override val dayNumber: Int
        get() = 10

    override fun partOne(input: String): String {
        val adapters = createAdapterList(input)
        val diffMap = adapters.zipWithNext()
            .fold(mutableMapOf<Long, Long>()) { diffMap, (j, k) ->
                val d = k - j
                diffMap[d] = 1 + diffMap.getOrDefault(d, 0)
                diffMap
            }
        return (diffMap.getOrDefault(1, 0) * diffMap.getOrDefault(3, 0)).toString()
    }

    override fun partTwo(input: String): String {
        val adapters = createAdapterList(input)
        return adapters.possibleChainingCount(adapters.indices).toString()
    }

    private fun List<Long>.possibleChainingCount(
        range: IntRange,
        resultCache: MutableMap<IntRange, Long> = mutableMapOf()
    ): Long {
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
}