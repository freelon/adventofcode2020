package com.github.freelon.aoc2020.day09

import java.io.File

fun main() {

    try {
        val input = File("input/day09.txt").readText()

        println("Part 1: ${firstHalf(input, 25)}")
        println("Part 2: ${secondHalf(input, 25)}")
    } catch (e: Exception) {

        e.printStackTrace()
    }
}

fun firstHalf(input: String, preambleLength: Int): Long {

    val values = input.lines().map { it.toLong(); }

    return getFirstInvalidNumber(values, preambleLength)
}

fun secondHalf(input: String, preambleLength: Int): Long {

    val values = input.lines().map { it.toLong(); }
    val searchedNumber = getFirstInvalidNumber(values, preambleLength)
    val sumMatchingRange = getRangeWithMatchingSum(values, searchedNumber)
    return sumMatchingRange.minOrNull()!! + sumMatchingRange.maxOrNull()!!
}

private fun getRangeWithMatchingSum(values: List<Long>, requiredSum: Long): List<Long> =
    values.indices.asSequence()
        .allPairs()
        .map { (startIndex, inclusiveEndIndex) -> values.slice(startIndex..inclusiveEndIndex) }
        .first { list -> list.sum() == requiredSum }

private fun getFirstInvalidNumber(values: List<Long>, preambleLength: Int) = values
    .drop(preambleLength).filterIndexed { index, number ->
        values.slice(index until index + preambleLength).asSequence().allPairs().none { number == it.first + it.second }
    }.first()

fun <T> Sequence<T>.allPairs(): Sequence<Pair<T, T>> {
    val values = this
    return sequence {
        values.mapIndexed { index, first ->
            values.drop(index).map { second -> first to second }
        }.flatMap {
            it
        }.forEach {
            yield(it)
        }
    }
}
