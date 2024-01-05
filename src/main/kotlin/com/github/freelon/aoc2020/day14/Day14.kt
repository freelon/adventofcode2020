package com.github.freelon.aoc2020.day14

import com.github.freelon.aoc2020.DayTemplate
import java.math.BigInteger

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

    override fun partTwo(input: String): Any {
        TODO("Not yet implemented")
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
        return BigInteger(v.joinToString(""), 2).toLong()
    }

    companion object {
        fun parse(i: Int): Int36 {
            val s = String.format("%36s", Integer.toBinaryString(i)).replace(' ', '0')
            return Int36(s.toCharArray().toList())
        }
    }
}
