package com.github.freelon.aoc2020

val subExpressionPattern = Regex(".*(\\([\\d*+]+\\)).*").toPattern()
val samePrecedencePattern = Regex("((\\d+)([+*])(\\d+)).*").toPattern()
val addPattern = Regex("(\\d+[*])*((\\d+)\\+(\\d+)).*").toPattern()

class Day18 : DayTemplate() {
    override fun partOne(input: String): Any {
        return calcX(input, this::calcSamePrecedence)
    }

    override fun partTwo(input: String): Any {
        return calcX(input, this::calcAddBeforeMul)
    }

    private fun calcX(input: String, calculator: (String) -> Long): Long {
        return input.trim().lines().map { it.replace(" ", "") }.sumOf { calc(it, calculator) }
    }

    private fun calc(expression: String, calculator: (String) -> Long): Long {
        var s = expression
        while (subExpressionPattern.matcher(s).matches()) {
            val m = subExpressionPattern.matcher(s)
            m.matches()
            val sub = m.group(1)
            val new = calc(sub.substring(1 until sub.length - 1), calculator)
            s = s.replace(sub, new.toString())
        }
        return calculator(s)
    }

    private fun calcSamePrecedence(sub: String): Long {
        var s = sub
        while (samePrecedencePattern.matcher(s).matches()) {
            val result = samePrecedencePattern.matcher(s)
            result.matches()
            val expression = result.group(1)
            val first = result.group(2).toLong()
            val op = result.group(3)
            val second = result.group(4).toLong()
            val subResult = when (op) {
                "+" -> first + second
                "*" -> first * second
                else -> throw IllegalArgumentException("bad operator $op")
            }
            s = s.replaceFirst(expression, subResult.toString())
        }

        return s.toLong()
    }

    private fun calcAddBeforeMul(sub: String): Long {
        var s = sub
        while (addPattern.matcher(s).matches()) {
            val result = addPattern.matcher(s)
            result.matches()
            val expression = result.group(2)
            val first = result.group(3).toLong()
            val second = result.group(4).toLong()
            val subResult = first + second
            s = s.replaceFirst(expression, subResult.toString())
        }

        return s.split("*")
            .fold(1L)
            { acc, next -> acc * next.toLong() }
    }
}

fun main() {
    Day18().run()
}