package com.github.freelon.aoc2020

import java.io.File

fun main() {
    val input = File("input/day03.txt").readText()
    val map = Map(input)

    firstHalf(map)
    secondHalf(map)
}

private fun firstHalf(map: Map) {
    val count = treesBySlope(map, 3, 1)

    println("Trees on slope: $count")
}

private fun treesBySlope(map: Map, slopeX: Int, slopeY: Int): Int {
    return generateSequence(0) { y -> y + slopeY }
        .zip(generateSequence(0) { x -> x + slopeX })
        .map { (y, x) -> map.get(x, y) }
        .takeWhile { it != 'E' }
        .filter { it == '#' }
        .count()
}

private fun secondHalf(map: Map) {
    val slopes = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)

    val product = slopes
        .map { (slopeX, slopeY) -> treesBySlope(map, slopeX, slopeY) }
        .foldRight(1L) { product, value -> product * value }

    println("Product of trees on slopes: $product")
}

private class Map(input: String) {

    val lines = input.lines()
    val height = lines.count()
    val width = lines.first().count()

    fun get(x: Int, y: Int): Char {
        if (y >= height)
            return 'E'
        return lines[y][x % width]
    }

    override fun toString(): String {
        return "Map(lines=$lines, height=$height, width=$width)"
    }
}