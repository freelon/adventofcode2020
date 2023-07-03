package com.github.freelon.aoc2020.day04

import java.io.File

fun main() {

    try {
        val input = File("input/day04.txt").readText()
        val passports = input.split("\n\n").map {
            Regex("([a-z]+):((#?)[a-z0-9]+)")
                .findAll(it)
                .map { match -> match.groupValues[1] to match.groupValues[2] }
                .toMap()
        }

        firstHalf(passports)
        secondHalf(passports)
    } catch (e: Exception) {

        e.printStackTrace()
    }
}

private fun firstHalf(passports: List<Map<String, String>>) {
    val neededCodes = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    val count = passports
        .filter { passport -> neededCodes.all { passport.containsKey(it) } }
        .count()

    println("$count valid passports")
}

private fun secondHalf(passports: List<Map<String, String>>) {

    val colorRegex = Regex("#[0-9a-f]{6}")
    val pidRegex = Regex("[0-9]{9}")

    val neededCodes = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    val validators: Map<String, (String) -> Boolean> = mapOf(
        "byr" to { s -> s.length == 4 && s.toInt() in 1920..2002 },
        "iyr" to { s -> s.length == 4 && s.toInt() in 2010..2020 },
        "eyr" to { s -> s.length == 4 && s.toInt() in 2020..2030 },
        "hgt" to { s ->
            s.endsWith("cm") && s.removeSuffix("cm").toInt() in 150..193 || s.endsWith("in") && s.removeSuffix("in")
                .toInt() in 59..76
        },
        "hcl" to { s -> colorRegex.matches(s) },
        "ecl" to { s ->
            s in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
        },
        "pid" to { s -> pidRegex.matches(s) },
        "cid" to { true }
    )

    val count = passports
        .filter { passport -> neededCodes.all { passport.containsKey(it) } }
        .filter { passport ->
            passport.all { entry ->
                (validators[entry.key] ?: error("'${entry.key}' is not a valid passport entry")).invoke(entry.value)
            }
        }
        .count()

    println("$count valid passports") // 138 too high
}
