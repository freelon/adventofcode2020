package com.github.freelon.aoc2020.day19

import com.github.freelon.aoc2020.DayTemplate

fun main() {
    Day19().run()
}

class Day19 : DayTemplate() {
    override fun partOne(input: String): Int = Solver(input).matchCount

    override fun partTwo(input: String): Int = Solver(input).matchFunkyCount
    // 145 too low
    // 353 is too high
}

class Solver(input: String) {
    private var messages: String
    val matchCount: Int
        get() = messages.lines().count { matches(it) }

    val matchFunkyCount: Int
        get() = messages.lines().withIndex().count {
            println("Starting ${it.index + 1}/${messages.lines().size}")
            matchesFunky(it.value)
        }
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
    }

    fun matches(message: String): Boolean {
        val m = matches(startRule, message)
        return m is Match && m.remainder.isEmpty()
    }

    fun matchesFunky(message: String): Boolean {
        return message.splits().map { (prefix, suffix) ->
            if (prefix == "babbbbaabbbbbabbbbbbaabaa")
                println("x")
            val p = Pair(
                count42(prefix), count31(suffix)
            )
            p
        }.any { (c42, c31) -> c42 > 0 && c31 > 0 && c31 <= c42 }
    }

    private val cache42: MutableMap<CharSequence, Int> = mutableMapOf()
    private fun count42(s: CharSequence): Int {
        if (!cache42.containsKey(s)) {
            cache42[s] = computeCount42(s)
        }

        return cache42.getValue(s)
    }

    private fun computeCount42(s: CharSequence) = s.splits()
        .map { (prefix, suffix) ->
            val m = matches(rule(42), prefix)
            if (m is Match) {
                val remainder = m.remainder.toString() + suffix
                if (remainder.isEmpty())
                    return@map 1
                val mSuffix = matches(rule(42), remainder)
                if (mSuffix.isFullMatch) {
                    val suffixCount = count42(remainder)
                    suffixCount + 1
                } else {
                    0
                }
            } else {
                0
            }
        }.filter { it > 0 }
        .minOrNull() ?: 0

    private fun count31(s: CharSequence): Int {
        val m = matches(rule(31), s)
        return if (m is Match) {
            if (m.remainder.isEmpty()) {
                1
            } else {
                val suffixMatch = matches(rule(31), m.remainder)
                if (suffixMatch.isFullMatch) {
                    val suffixCount = count31(m.remainder)
                    suffixCount + 1
                } else {
                    0
                }
            }
        } else {
            0
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

        return if (success) Match(remainder)
        else Miss
    }

    private fun rule(i: Int) = rules[i]!!

    fun expand(id: Int): Set<String> {
        val rule = rule(id)
        return if (rule.content.startsWith('"')) {
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

    }

    private fun expand(ids: List<Int>): Set<String> =
        ids.fold(setOf("")) { acc, rule ->
            val forRule = expand(rule)
            acc.flatMap { a -> forRule.map { b -> a + b } }.toSet()
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

fun CharSequence.splits(): List<Pair<CharSequence, CharSequence>> = this.indices.map { it + 1 }
    .map { splitAt -> Pair(this.subSequence(0, splitAt), this.subSequence(splitAt, this.length)) }