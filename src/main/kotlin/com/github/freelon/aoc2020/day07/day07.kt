package com.github.freelon.aoc2020.day07

import java.io.File

fun main() {

    try {
        val input = File("input/day07.txt").readText()

        println("Part 1: ${firstHalf(input)}")
        println("Part 2: ${secondHalf(input)}")
    } catch (e: Exception) {

        e.printStackTrace()
    }
}

fun firstHalf(input: String): Int {
    val rules = parseInput(input)

    val possibleAncestorsOfGold = mutableSetOf<String>()
    val ancestorQueue = mutableSetOf<String>()
    ancestorQueue.add("shiny gold")
    while (ancestorQueue.isNotEmpty()) {
        val bag = ancestorQueue.first()
        ancestorQueue.remove(bag)
        possibleAncestorsOfGold.add(bag)
        rules.filter { rule -> rule.inners.any { countedBag -> countedBag.color == bag } }
            .forEach { rule -> ancestorQueue.add(rule.containerColor) }

        println("Queue: $ancestorQueue")
        println("Result: $possibleAncestorsOfGold")
    }

    possibleAncestorsOfGold.remove("shiny gold")

    return possibleAncestorsOfGold.size
}

fun secondHalf(input: String): Int {
    val rules = parseInput(input)

    return neededBags("shiny gold", rules)
}

private fun neededBags(color: String, rules: List<Rule>): Int {
    val rule = rules.find { it.containerColor == color }!!
    return rule.inners.map { countedBag ->
        countedBag.count * (1 + neededBags(countedBag.color, rules))
    }.sum()
}

private fun parseInput(input: String): List<Rule> {
    return input.lines()
        .map { line ->
            val containerColor = line.substring(0..line.indexOf(" bags contain ")).trim()
            var inners = listOf<CountedBag>()

            if (!line.contains("no other bags")) {
                val right = line.split("contain ")[1]
                inners = right.split(", ").map { inner ->
                    val splits = inner.split(" ")
                    CountedBag(splits[1] + " " + splits[2].trim(), Integer.parseInt(splits[0]))
                }
            }

            Rule(containerColor, inners)
        }
}

data class Rule(val containerColor: String, val inners: List<CountedBag>)

data class CountedBag(val color: String, val count: Int)