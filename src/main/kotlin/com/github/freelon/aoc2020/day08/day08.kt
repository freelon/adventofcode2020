package com.github.freelon.aoc2020.day08

import java.io.File

fun main() {

    try {
        val input = File("input/day08.txt").readText()

        println("Part 1: ${firstHalf(input)}")
        println("Part 2: ${secondHalf(input)}")
    } catch (e: Exception) {

        e.printStackTrace()
    }
}

fun parse(input: String): List<Operation> =
    input.lines().map {
        val (left, right) = it.split(" ")
        var value = Integer.parseInt(right.substring(1))
        if (right[0] == '-')
            value *= -1

        Operation(OpCode.valueOf(left.capitalize()), value)
    }.toList()

fun firstHalf(input: String): Int {

    val instructions = parse(input)
    val handheld = Handheld(instructions)
    handheld.run()
    return handheld.accumulator
}

fun secondHalf(input: String): Int {

    val instructions = parse(input).toMutableList()

    return runWithSwitchedOpCode(instructions, OpCode.Nop, OpCode.Jmp)
        ?: runWithSwitchedOpCode(instructions, OpCode.Jmp, OpCode.Nop)
        ?: throw IllegalStateException("Didn't finish :o")
}

private fun runWithSwitchedOpCode(instructions: MutableList<Operation>, from: OpCode, to: OpCode): Int? {
    instructions.mapIndexed { index, operation -> index to operation }
        .filter { (_, op) -> op.opCode == from }
        .forEach { (index, operation) ->
            instructions[index] = operation.copy(opCode = to)
            val handheld = Handheld(instructions)
            handheld.run()

            if (!handheld.abortedLoop())
                return handheld.accumulator

            instructions[index] = operation
        }

    return null
}

class Handheld(private val instructions: List<Operation>) {

    var accumulator: Int = 0
    var instructionCounter: Int = 0
    private val executedInstructions: MutableSet<Int> = mutableSetOf()

    fun run() {

        while (instructionCounter < instructions.size) {

            if (executedInstructions.contains(instructionCounter))
                return

            executedInstructions.add(instructionCounter)
            val op = instructions[instructionCounter]
            when (op.opCode) {
                OpCode.Acc -> {
                    accumulator += op.value
                    instructionCounter++
                }
                OpCode.Jmp -> {
                    instructionCounter += op.value
                }
                OpCode.Nop -> {
                    instructionCounter++
                }
            }
        }
    }

    fun abortedLoop() = executedInstructions.contains(instructionCounter)
}

data class Operation(val opCode: OpCode, val value: Int)

enum class OpCode {
    Acc, Jmp, Nop
}
