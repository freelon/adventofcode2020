package com.github.freelon.aoc2020.day16

import com.github.freelon.aoc2020.DayTemplate

class Day16 : DayTemplate() {
    override fun partOne(input: String): Int {
        val (fields, _, nearbyTickets) = parse(input)
        val invalidValues = nearbyTickets.flatMap { ticket ->
            val badTicketValues =
                ticket.values.filter { v -> fields.none { field -> (v in field.restriction1) or (v in field.restriction2) } }
            badTicketValues
        }.sum()
        return invalidValues
    }

    override fun partTwo(input: String): Any {
        TODO("Not yet implemented")
    }
}

fun parse(input: String): Triple<List<Field>, Ticket, List<Ticket>> {
    val (a, b, c) = input.split("\n\n")
    val fields = a.lines().map { line ->
        val (name, rest) = line.split(":")
        val parts = rest.trim().split(" ")
        val r1 = parts[0].split("-").map { it.toInt() }
        val r2 = parts[2].split("-").map { it.toInt() }
        Field(name, r1[0]..r1[1], r2[0]..r2[1])
    }

    val myTicket = Ticket(b.lines()[1].split(",").map { it.toInt() })

    val nearbyTickets = c.trim().lines().drop(1).map { line ->
        Ticket(line.split(",").map { it.toInt() })
    }

    return Triple(fields, myTicket, nearbyTickets)
}

data class Field(val name: String, val restriction1: IntRange, val restriction2: IntRange)

data class Ticket(val values: List<Int>)

fun main() {
    Day16().run()
}