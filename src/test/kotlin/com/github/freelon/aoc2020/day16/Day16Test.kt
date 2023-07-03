package com.github.freelon.aoc2020.day16

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day16Test {
    @Test
    fun part1() {
        val input = """class: 1-3 or 5-7
row: 6-11 or 33-44
seat: 13-40 or 45-50

your ticket:
7,1,14

nearby tickets:
7,3,47
40,4,50
55,2,20
38,6,12"""
        assertEquals(71, Day16().partOne(input))
    }

    @Test
    fun part2() {
        val input = """class: 0-1 or 4-19
row: 0-5 or 8-19
seat: 0-13 or 16-19

your ticket:
11,12,13

nearby tickets:
3,9,18
15,1,5
5,14,9"""
        val (fields, myTicket, nearbyTickets) = parse(input)
        val tickets = listOf(myTicket) + nearbyTickets

        assertEquals(listOf(fields[1], fields[0], fields[2]), fieldAssignments(fields, tickets))
    }
}