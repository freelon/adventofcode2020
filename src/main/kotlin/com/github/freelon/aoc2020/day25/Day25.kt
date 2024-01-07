package com.github.freelon.aoc2020.day25

import com.github.freelon.aoc2020.DayTemplate

fun main() {
    Day25().run()
}

class Day25 : DayTemplate() {
    override fun partOne(input: String): Int {
        val publicKeys = input.trim().lines().map { it.toInt() }
        val loopSizes = publicKeys.take(1).map { pk ->
            var last = transformSubject(7, 1)
            (2 until 20201227).first {
                val transformed = transformSubject(7, 1, start = last)
                last = transformed
                transformed == pk
            }
        }
        val encryptionKey = transformSubject(publicKeys[1], loopSizes[0])
        return encryptionKey
    }

    private fun transformSubject(subject: Int, loopSize: Int, start: Int = 1): Int {
        var result: Long = start.toLong()
        repeat(loopSize) {
            result *= subject
            result %= 20201227
        }
        return result.toInt()
    }

    override fun partTwo(input: String): Any {
        return ""
    }
}