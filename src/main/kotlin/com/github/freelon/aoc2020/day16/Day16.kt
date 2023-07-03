package com.github.freelon.aoc2020.day16

import com.github.freelon.aoc2020.DayTemplate


fun main() {
    Day16().run()
}

data class Field(val name: String, val restriction1: LongRange, val restriction2: LongRange)

data class Ticket(val values: List<Long>)

class Day16 : DayTemplate() {

    override fun partOne(input: String): Long {
        val (fields, _, nearbyTickets) = parse(input)
        val invalidValues = nearbyTickets.flatMap { ticket ->
            val badTicketValues =
                ticket.values.filter { v -> fields.none { field -> (v in field.restriction1) or (v in field.restriction2) } }
            badTicketValues
        }.sum()
        return invalidValues
    }

    override fun partTwo(input: String): Long {
        val (fields, myTicket, nearbyTickets) = parse(input)
        val tickets = listOf(myTicket) + nearbyTickets
        val validTickets =
            tickets.filter { ticket ->
                ticket.values.all { v ->
                    fields.any { field -> (v in field.restriction1) or (v in field.restriction2) }
                }
            }

        val assignments = fieldAssignments(fields, validTickets)
        return myTicket.values.zip(assignments)
            .filter { it.second.name.startsWith("departure") }
            .map { it.first }
            .fold(1) { a, b -> a * b }
    }
}

fun parse(input: String): Triple<List<Field>, Ticket, List<Ticket>> {
    val (a, b, c) = input.split("\n\n")
    val fields = a.lines().map { line ->
        val (name, rest) = line.split(":")
        val parts = rest.trim().split(" ")
        val r1 = parts[0].split("-").map { it.toLong() }
        val r2 = parts[2].split("-").map { it.toLong() }
        Field(name, r1[0]..r1[1], r2[0]..r2[1])
    }

    val myTicket = Ticket(b.lines()[1].split(",").map { it.toLong() })

    val nearbyTickets = c.trim().lines().drop(1).map { line ->
        Ticket(line.split(",").map { it.toLong() })
    }

    return Triple(fields, myTicket, nearbyTickets)
}

fun fieldAssignments(fields: List<Field>, tickets: List<Ticket>): List<Field> {
    val possibleAssignments = fields.map { Pair(it, fields.indices.toMutableList()) }
    for ((field, assignments) in possibleAssignments) {
        assignments.removeIf { a ->
            tickets.any { t ->
                val valueAtPosition = t.values[a]
                val restriction1Satisfied = valueAtPosition in field.restriction1
                val restriction2satisfied = valueAtPosition in field.restriction2
                !(restriction1Satisfied or restriction2satisfied)
            }
        }
    }
    while (possibleAssignments.any { it.second.size > 1 }) {
        possibleAssignments
            .filter { it.second.size == 1 }
            .forEach { assignment ->
                val (field, assignments) = assignment
                val finalAssignment = assignments.first()
                possibleAssignments.filter { it.first != field }
                    .forEach { it.second.removeIf { vToCheck -> vToCheck == finalAssignment } }
            }
    }
    return possibleAssignments.sortedBy { it.second.first() }.map { it.first }
}
