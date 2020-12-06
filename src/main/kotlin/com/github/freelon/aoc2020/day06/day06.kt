package com.github.freelon.aoc2020.day06

import java.io.File

fun main() {

    try {
        val input = File("input/day06.txt").readText()

        println("Part 1: ${firstHalf(input)}")
        println("Part 2: ${secondHalf(input)}")
    } catch (e: Exception) {

        e.printStackTrace()
    }
}

fun secondHalf(input: String): Int {
    val groups = getGroups(input)

    return groups.map { group ->
        group.map { person ->
            person.toSet()
        }.fold(('a'..'z').toSet()) { result, personSet ->
            result.intersect(personSet)
        }.count()
    }.fold(0) { value, groupCount -> value + groupCount }
}

fun firstHalf(input: String): Int {
    val groups = getGroups(input)

    return groups.map { group ->
        group.joinToString("").fold(mutableSetOf<Char>()) { set, char ->
            set.add(char)
            set
        }.count()
    }.fold(0) { value, count -> value + count }
}

private fun getGroups(input: String) = input.lines()
    .fold(mutableListOf<List<String>>() to mutableListOf<String>()) { (resultList, currentList), line ->
        if (line.isBlank()) {
            resultList.add(currentList)
            return@fold resultList to mutableListOf<String>()
        }
        currentList.add(line)
        resultList to currentList
    }.first
