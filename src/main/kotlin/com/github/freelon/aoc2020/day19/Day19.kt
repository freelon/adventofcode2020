package com.github.freelon.aoc2020.day19

import com.github.freelon.aoc2020.DayTemplate

fun main() {
    Day19().run()
}

class Day19 : DayTemplate() {
    override fun partOne(input: String): Int = Solver(input).matchCount

    override fun partTwo(input: String): Int = Solver(input, true).matchCount
    // 145 too low
}

class Solver(input: String, substituteRules: Boolean = false) {
    val matchCount: Int
    private val startRule: Rule
    private val rules: Map<Int, Rule>

    init {
        val (rawRules, messages) = input.split("\n\n")
        val rules = rawRules.lines()
            .map { line ->
                val (id, content) = line.split(":")
                Rule(id.toInt(), content.trim())
            }
            .associateBy(Rule::id)
            .toMutableMap()

        if (substituteRules) {
            val maxDepth = 10
            val content8 = (1..maxDepth)
                .map { " 42".repeat(it).trim() }
                .joinToString(" | ")
                .trim()
            val content11 = (1..maxDepth)
                .map { " 42".repeat(it).trim() + " " + " 31".repeat(it).trim() }
                .joinToString(" | ")
                .trim()
            rules[8] = Rule(8, content8)
            rules[11] = Rule(11, content11)
        }

        this.rules = rules
        startRule = rule(0)

        matchCount = messages.lines().count { matches(it) }
    }

    fun matches(message: String): Boolean {
        val m = matches(startRule, message)
        return m is Match && m.remainder.isEmpty()
    }

    //0: 4 1 5
    //1: 2 3 | 3 2
    //2: 4 4 | 5 5
    //3: 4 5 | 5 4
    //4: "a"
    //5: "b"
    private fun matches(rule: Rule, slice: CharSequence): Result {
        return if (rule.content.startsWith('"')) {
            val c = rule.content[1]
            when {
                slice.isNotEmpty() && slice[0] == c -> Match(slice.drop(1))
                else -> Miss
            }
        } else if (rule.content.contains("|")) {
            val parts = rule.content.split('|').map { it.trim() }

            for (part in parts) {
                val partRules = part.split(" ").map { rule(it.toInt()) }
                val partResult = checkConcatenated(slice, partRules)
                if (partResult is Match) {
                    return partResult
                }
            }

            Miss
        } else {
            val rules = rule.content.split(" ").map { rule(it.toInt()) }
            checkConcatenated(slice, rules)
        }
    }

    private fun checkConcatenated(
        slice: CharSequence,
        rulesToApply: List<Rule>
    ): Result {
        var remainder = slice
        var success = true
        for (r in rulesToApply) {
            when (val m = matches(r, remainder)) {
                is Match -> remainder = m.remainder
                is Miss -> {
                    success = false
                    break
                }
            }
        }

        return if (success)
            Match(remainder)
        else
            Miss
    }

    private fun rule(i: Int) = rules[i]!!
}

data class Rule(val id: Int, val content: String)

sealed class Result

class Match(val remainder: CharSequence) : Result()

object Miss : Result()