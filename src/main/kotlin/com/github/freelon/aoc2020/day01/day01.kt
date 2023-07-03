package com.github.freelon.aoc2020.day01

import java.io.File

fun main() {
    val numbers = File("input/day01.txt").readLines().map { Integer.parseInt(it) }
    firstHalf(numbers)
    secondHalf(numbers)
}

private fun firstHalf(numbers: List<Int>) {
    val a = numbers.first { a -> numbers.any { b -> a + b == 2020 } }
    val b = numbers.first { b -> a + b == 2020 }
    println("a * b = ${a * b}")
}

private fun secondHalf(numbers: List<Int>) {
    val a = numbers.first { a -> numbers.any { b -> numbers.any { c -> a + b + c == 2020 } } }
    val b = numbers.first { b -> numbers.any { c -> a + b + c == 2020 } }
    val c = numbers.first { c -> a + b + c == 2020 }
    println("a * b * c = ${a * b * c}")
}