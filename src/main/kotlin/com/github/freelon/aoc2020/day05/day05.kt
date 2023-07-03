package com.github.freelon.aoc2020.day05

import java.io.File

fun main() {

    try {
        val input = File("input/day05.txt").readText()
        val seats = input.lines().map(::getSeat).toSet()

        firstHalf(seats)
        secondHalf(seats)
    } catch (e: Exception) {

        e.printStackTrace()
    }
}

private fun firstHalf(seats: Set<Seat>) {

    val result = seats.maxByOrNull(Seat::id)

    println("Result #1: ${result?.id()}")
}

private fun secondHalf(seats: Set<Seat>) {

    (0..127).flatMap { row ->
        (0..7).map { col -> Seat(row, col) }.filter { seat ->
            !seats.contains(seat) && seats.contains(
                Seat(
                    seat.row + 1,
                    seat.column
                )
            ) && seats.contains(Seat(seat.row - 1, seat.column))
        }
    }
        .single()
        .apply { println("Result #2: $this - ${this.id()}") }
}

private fun getSeat(ticket: String): Seat {

    val unifiedTicket = ticket.replace('F', 'L').replace('B', 'U').replace('R', 'U')
    val rowResult = narrowSpace(unifiedTicket.subSequence(0, 7), 0..127)

    if (rowResult.count() > 1)
        throw IllegalStateException("Range is $rowResult")

    val columnResult = narrowSpace(unifiedTicket.substring(7), 0..7)

    if (columnResult.count() > 1)
        throw IllegalStateException("Range is $columnResult")

    return Seat(rowResult.first, columnResult.first)
}

private fun narrowSpace(rowSelector: CharSequence, initialRange: IntRange) =
    rowSelector.fold(initialRange) { range, char ->
        when (char) {
            'L' -> (range.first..range.first + (range.last - range.first) / 2)
            'U' -> (range.first + (range.last - range.first + 1) / 2..range.last)
            else -> throw IllegalArgumentException("input is $char but must be L or U")
        }
    }

data class Seat(val row: Int, val column: Int) {
    fun id() = row * 8 + column
}
