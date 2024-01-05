package com.github.freelon.aoc2020.day19

import com.github.freelon.aoc2020.DayTemplate
import kotlin.math.min

fun main() {
    Day19().run()
}

class Day19 : DayTemplate() {
    override fun partOne(input: String): Int {
        val solver = Solver(input)
        val messages = input.split("\n\n")[1].lines()
        val set = solver.expand(0)
        return messages.count { it in set }
    }

    override fun partTwo(input: String): Int {
        val solver = Solver(input)
        val messages = input.split("\n\n")[1].lines()
        return messages.count { solver.matchByExpansion(it) }
    }
}

class Solver(input: String) {
    private var messages: String
    val set42: Set<String>
    val set31: Set<String>
    private val expansionCache: MutableMap<Int, Set<String>> = mutableMapOf()

    private val startRule: Rule
    private val rules: Map<Int, Rule>

    init {
        val (rawRules, messages) = input.split("\n\n")
        this.messages = messages
        val rules = rawRules.lines().map { line ->
            val (id, content) = line.split(":")
            Rule(id.toInt(), content.trim())
        }.associateBy(Rule::id).toMutableMap()

        this.rules = rules
        startRule = rule(0)

        if (rules.containsKey(42)) {
            set42 = expand(42)
            set31 = expand(31)
            assert(set42.all { it.length == set42.first().length })
            assert(set31.all { it.length == set42.first().length })
            assert(set42.intersect(set31).isEmpty())
        } else {
            set42 = setOf()
            set31 = setOf()
        }
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
        slice: CharSequence, rulesToApply: List<Rule>
    ): Result {
        var remainder = slice
        for (r in rulesToApply) {
            when (val m = matches(r, remainder)) {
                is Match -> remainder = m.remainder
                is Miss -> {
                    return Miss
                }
            }
        }

        return Match(remainder)
    }

    private fun rule(i: Int) = rules[i]!!

    fun expand(id: Int): Set<String> {
        if (!expansionCache.containsKey(id)) {
            val rule = rule(id)
            val set = if (rule.content.startsWith('"')) {
                setOf(rule.content.removeSurrounding("\""))

            } else if (rule.content.contains("|")) {
                val parts = rule.content.split('|').map { it.trim() }

                parts.map { part ->
                    val partRules = part.split(" ").map { it.toInt() }
                    expand(partRules)
                }.flatten()
                    .toSet()


            } else {
                val rules = rule.content.split(" ").map { it.toInt() }
                expand(rules)
            }
            expansionCache[id] = set
        }

        return expansionCache[id]!!
    }

    private fun expand(ids: List<Int>): Set<String> =
        ids.fold(setOf("")) { acc, rule ->
            val forRule = expand(rule)
            acc.flatMap { a -> forRule.map { b -> a + b } }.toSet()
        }

    fun matchByExpansion(message: String): Boolean {
        val len = set42.first().length
        val parts = message.chunked(len)
        return (min(1, parts.size / 2) until parts.size).any { n42 ->
            val n31 = parts.size - n42
            parts.take(n42).all { it in set42 } and parts.drop(n42).all { it in set31 } and (n42 > n31)
        }
    }
}

data class Rule(val id: Int, val content: String)

sealed class Result {
    open val isFullMatch get() = false
}

class Match(val remainder: CharSequence) : Result() {
    override val isFullMatch get() = remainder.isEmpty()
}

object Miss : Result()
