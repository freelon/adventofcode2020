package com.github.freelon.aoc2020.day11

import com.github.freelon.aoc2020.DayTemplate

class Day11 : DayTemplate() {

    override fun partOne(input: String): Any {
        // x, y
        val initialMap = extractMap(input)

        var currentMap: Map<Pair<Int, Int>, Char> = initialMap
        var oldMap: Map<Pair<Int, Int>, Char>
        do {
            oldMap = currentMap
            val newMap: Map<Pair<Int, Int>, Char> = currentMap.keys.map { coordinate ->
                val newValue = when (val oldValue = currentMap[coordinate]) {
                    '.' -> '.'
                    'L' -> {
                        if (currentMap.getOccupiedNeighborCount(coordinate) == 0)
                            '#'
                        else
                            'L'
                    }
                    '#' -> {
                        if (currentMap.getOccupiedNeighborCount(coordinate) >= 4)
                            'L'
                        else
                            '#'
                    }
                    else -> throw IllegalStateException("Illegal char $oldValue")
                }
                coordinate to newValue
            }.toMap()
            currentMap = newMap

        } while (currentMap != oldMap)

        return currentMap.values.count { it == '#' }
    }

    override fun partTwo(input: String): Any {
        // x, y
        val initialMap = extractMap(input)

        var currentMap: Map<Pair<Int, Int>, Char> = initialMap
        var oldMap: Map<Pair<Int, Int>, Char>
        do {
            oldMap = currentMap
            val newMap: Map<Pair<Int, Int>, Char> = currentMap.keys.map { coordinate ->
                val newValue = when (val oldValue = currentMap[coordinate]) {
                    '.' -> '.'
                    'L' -> {
                        if (currentMap.countInLineOfSight(coordinate) == 0)
                            '#'
                        else
                            'L'
                    }
                    '#' -> {
                        if (currentMap.countInLineOfSight(coordinate) >= 5)
                            'L'
                        else
                            '#'
                    }
                    else -> throw IllegalStateException("Illegal char $oldValue")
                }
                coordinate to newValue
            }.toMap()
            currentMap = newMap
        } while (currentMap != oldMap)

        return currentMap.values.count { it == '#' }
    }

    private fun extractMap(input: String): MutableMap<Pair<Int, Int>, Char> {
        val initialMap = mutableMapOf<Pair<Int, Int>, Char>()
        input.lines().forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                initialMap[x to y] = c
            }
        }
        return initialMap
    }

    private fun printMap(map: Map<Pair<Int, Int>, Char>) {
        val width = map.keys.map { it.first }.maxOrNull()!!
        val height = map.keys.map { it.second }.maxOrNull()!!

        (0..height).forEach { y ->
            (0..width).forEach { x ->
                print(map[x to y])
            }
            println()
        }
        println()
    }
}

private fun Map<Pair<Int, Int>, Char>.getOccupiedNeighborCount(c: Pair<Int, Int>): Int {
    val coords = listOf(
        c.first - 1 to c.second - 1, c.first - 1 to c.second, c.first - 1 to c.second + 1,
        c.first to c.second - 1, c.first to c.second + 1,
        c.first + 1 to c.second - 1, c.first + 1 to c.second, c.first + 1 to c.second + 1
    )

    return coords.map { this.getOrDefault(it, '.') }.count { it == '#' }
}

private fun Map<Pair<Int, Int>, Char>.countInLineOfSight(c: Pair<Int, Int>): Int {
    val width = this.keys.map { it.first }.maxOrNull()!!
    val height = this.keys.map { it.second }.maxOrNull()!!
    val r = (getDiagonals(c, -1 to 0, width, height).map { this[it] }.filter { it != '.' }
        .firstOrNull() == '#').toNumber() +
            (getDiagonals(c, 0 to -1, width, height).map { this[it] }.filter { it != '.' }
                .firstOrNull() == '#').toNumber() +
            (getDiagonals(c, 1 to 0, width, height).map { this[it] }.filter { it != '.' }
                .firstOrNull() == '#').toNumber() +
            (getDiagonals(c, 0 to 1, width, height).map { this[it] }.filter { it != '.' }
                .firstOrNull() == '#').toNumber() +
            (getDiagonals(c, -1 to -1, width, height).map { this[it] }.filter { it != '.' }
                .firstOrNull() == '#').toNumber() +
            (getDiagonals(c, -1 to +1, width, height).map { this[it] }.filter { it != '.' }
                .firstOrNull() == '#').toNumber() +
            (getDiagonals(c, +1 to -1, width, height).map { this[it] }.filter { it != '.' }
                .firstOrNull() == '#').toNumber() +
            (getDiagonals(c, +1 to +1, width, height).map { this[it] }.filter { it != '.' }
                .firstOrNull() == '#').toNumber()
    return r
}

private fun Boolean.toNumber() = if (this) 1 else 0

private fun getDiagonals(c: Pair<Int, Int>, delta: Pair<Int, Int>, width: Int, height: Int): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()

    var current = c
    while (true) {
        val x = current.first + delta.first to current.second + delta.second

        if (x.first < 0 || x.first > width || x.second < 0 || x.second > height)
            return result
        result.add(x)
        current = x
    }
}