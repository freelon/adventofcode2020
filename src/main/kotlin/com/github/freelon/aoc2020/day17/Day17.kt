package com.github.freelon.aoc2020.day17

import com.github.freelon.aoc2020.DayTemplate

class Day17 : DayTemplate() {
    override fun partOne(input: String): Any {
        var active = input.lines()
            .mapIndexed { y, s ->
                s.chars().mapToObj { Char(it) }.toList().mapIndexed { x, c -> Pair(x, c) }
                    .filter { p -> p.second == '#' }
                    .map { p -> P3(p.first, y, 0) }
            }
            .flatten()
            .toSet()

        for (round in 1..6) {
            val possiblyChanging = active + active.flatMap { it.neighbors() }
            active = possiblyChanging
                .filter { point ->
                    val isActive = active.contains(point)
                    val activeNeighbors = point.neighbors().count { n -> active.contains(n) }
                    if (isActive) {
                        activeNeighbors == 2 || activeNeighbors == 3
                    } else {
                        activeNeighbors == 3
                    }
                }
                .toSet()
        }

        return active.count()
    }

    override fun partTwo(input: String): Any {
        var active = input.lines()
            .mapIndexed { y, s ->
                s.chars().mapToObj { Char(it) }.toList().mapIndexed { x, c -> Pair(x, c) }
                    .filter { p -> p.second == '#' }
                    .map { p -> P4(p.first, y, 0, 0) }
            }
            .flatten()
            .toSet()

        for (round in 1..6) {
            val possiblyChanging = active + active.flatMap { it.neighbors() }
            active = possiblyChanging
                .filter { point ->
                    val isActive = active.contains(point)
                    val activeNeighbors = point.neighbors().count { n -> active.contains(n) }
                    if (isActive) {
                        activeNeighbors == 2 || activeNeighbors == 3
                    } else {
                        activeNeighbors == 3
                    }
                }
                .toSet()
        }

        return active.count()
    }
}

data class P3(val x: Int, val y: Int, val z: Int) {
    fun neighbors(): List<P3> {
        val result = mutableListOf<P3>()
        for (nx in x - 1..x + 1)
            for (ny in y - 1..y + 1)
                for (nz in z - 1..z + 1) {
                    val newPoint = P3(nx, ny, nz)
                    if (newPoint != this)
                        result.add(newPoint)
                }
        return result
    }
}

data class P4(val x: Int, val y: Int, val z: Int, val zz: Int) {
    fun neighbors(): List<P4> {
        val result = mutableListOf<P4>()
        for (nx in x - 1..x + 1)
            for (ny in y - 1..y + 1)
                for (nz in z - 1..z + 1)
                    for (nzz in zz - 1..zz + 1) {
                        val newPoint = P4(nx, ny, nz, nzz)
                        if (newPoint != this)
                            result.add(newPoint)
                    }
        return result
    }
}

fun main() {
    Day17().run()
}