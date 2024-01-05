package com.github.freelon.aoc2020.day14

import com.github.freelon.aoc2020.DayTemplate

fun main() {
    Day14().run()
}

class Day14 : DayTemplate() {
    override fun partOne(input: String): Long {
        val mem = mutableMapOf<Int, Int36>()
        val lines = input.lines()
        var mask = lines[0].substring(7)
        lines.drop(1).forEach { line ->
            if (line.startsWith("mem")) {
                val s = line.split(" = ")
                val i = s[1].toInt()
                val v = Int36.parse(i)
                val address = s[0].substring(4 until s[0].length - 1).toInt()
                mem[address] = v.applyMask(mask)
            } else if (line.startsWith("mask")) {
                mask = line.substring(7)
            }
        }
        return mem.values.sumOf { it.toLong() }
    }

    override fun partTwo(input: String): Long {
        val mem = mutableMapOf<Long, Long>()
        val lines = input.lines()
        var mask = lines[0].substring(7).toCharArray().toList()
        lines.drop(1).forEach { line ->
            if (line.startsWith("mem")) {
                val s = line.split(" = ")
                val value = s[1].toLong()
                val baseAddress = Int36.parse(s[0].substring(4 until s[0].length - 1).toInt()).v
                for (address in maskAddress(baseAddress, mask))
                    mem[address] = value
            } else if (line.startsWith("mask")) {
                mask = line.substring(7).toCharArray().toList()
            }
        }
        return mem.values.sum()
    }

    private fun maskAddress(baseAddress: List<Char>, mask: List<Char>): List<Long> {
        val masked = baseAddress.zip(mask).map { (a, m) ->
            when (m) {
                '0' -> a
                '1' -> '1'
                'X' -> 'X'
                else -> throw IllegalArgumentException("didn't expect $m here")
            }
        }
        return variants(masked.joinToString("")).map { it.toLong(2) }
    }

    private fun variants(masked: String): Set<String> {
        val i = masked.indexOf('X')
        if (i < 0)
            return setOf(masked)

        val s1 = masked.replaceFirst('X', '0')
        val r1 = variants(s1)
        val s2 = masked.replaceFirst('X', '1')
        val r2 = variants(s2)
        return r1 + r2
    }
}

data class Int36(val v: List<Char>) {
    fun applyMask(mask: String): Int36 {
        val vNew = v.zip(mask.toCharArray().toList()).map { (a, m) ->
            when (m) {
                'X' -> a
                '0' -> '0'
                '1' -> '1'
                else -> {
                    throw IllegalArgumentException("did not expect $m here")
                }
            }
        }
        return Int36(vNew)
    }

    fun toLong(): Long {
        return v.joinToString("").toLong(2)
    }

    companion object {
        fun parse(i: Int): Int36 {
            val s = String.format("%36s", Integer.toBinaryString(i)).replace(' ', '0')
            return Int36(s.toCharArray().toList())
        }
    }
}
