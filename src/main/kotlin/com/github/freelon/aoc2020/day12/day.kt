package com.github.freelon.aoc2020.day12

import com.github.freelon.aoc2020.DayTemplate
import com.github.freelon.aoc2020.day12.Direction.*
import com.github.freelon.aoc2020.day12.Side.L
import com.github.freelon.aoc2020.day12.Side.R
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class Day12 : DayTemplate() {

    override fun partOne(input: String): Any {
        val instructions = readInstructions(input)

        return instructions.fold(Ship()) { ship, instruction ->
            ship.navigate(instruction)
            ship
        }.position.distance(Position(0, 0))
    }

    override fun partTwo(input: String): Any {
        val instructions = readInstructions(input)

        return instructions.fold(Ship() to Waypoint()) { (ship, waypoint), instruction ->
            if (instruction.action == NavigationAction.F)
                ship.navigateToWaypoint(waypoint, instruction.value)
            else
                waypoint.navigate(instruction)

            println(instruction)
            println(ship)
            println(waypoint)
            println()

            ship to waypoint
        }.first.position.distance(Position(0, 0)) // 158130 too high
    }

    private fun readInstructions(input: String): List<NavigationInstruction> = input.lines()
        .map { line ->
            NavigationInstruction(
                NavigationAction.valueOf(line.substring(0..0)),
                line.substring(1).toInt()
            )
        }

}

class Waypoint {
    var position: Position = Position(1, 10)

    fun navigate(instruction: NavigationInstruction) {
        position = when (instruction.action) {
            NavigationAction.N -> position.move(N, instruction.value)
            NavigationAction.S -> position.move(S, instruction.value)
            NavigationAction.E -> position.move(E, instruction.value)
            NavigationAction.W -> position.move(W, instruction.value)
            NavigationAction.L -> position.rotate(L, instruction.value)
            NavigationAction.R -> position.rotate(R, instruction.value)
            NavigationAction.F -> error("not applicable to waypoint")
        }
    }

    override fun toString(): String {
        return "Waypoint(position=$position)"
    }
}

class Ship {
    var direction: Direction = E
    var position: Position = Position(0, 0)

    fun navigate(instruction: NavigationInstruction) {
        when (instruction.action) {
            NavigationAction.N -> position = position.move(N, instruction.value)
            NavigationAction.S -> position = position.move(S, instruction.value)
            NavigationAction.E -> position = position.move(E, instruction.value)
            NavigationAction.W -> position = position.move(W, instruction.value)
            NavigationAction.L -> direction = direction.turn(instruction.value, L)
            NavigationAction.R -> direction = direction.turn(instruction.value, R)
            NavigationAction.F -> position = position.move(direction, instruction.value)
        }
    }

    fun navigateToWaypoint(waypoint: Waypoint, distance: Int) {
        position = (1..distance).fold(position) { position, _ -> position.add(waypoint.position) }
    }

    override fun toString(): String {
        return "Ship(direction=$direction, position=$position)"
    }
}

data class NavigationInstruction(val action: NavigationAction, val value: Int)

enum class Direction {
    N, S, W, E;

    fun turn(degrees: Int, side: Side): Direction {
        val order = listOf(S, W, N, E)
        // turning right is positive
        val rightTurns = degrees / 90 * if (side == L) -1 else 1
        val indexOfCurrentDirection = order.indexOf(this)
        return order[(indexOfCurrentDirection + rightTurns + 4) % 4]
    }
}

enum class Side {
    L, R
}

data class Position(val north: Int, val east: Int) {
    fun distance(other: Position) = abs(north - other.north) + abs(east - other.east)

    fun move(direction: Direction, value: Int): Position {
        return when (direction) {
            N -> this.copy(north = north + value)
            S -> this.copy(north = north - value)
            W -> this.copy(east = east - value)
            E -> this.copy(east = east + value)
        }
    }

    fun rotate(side: Side, degrees: Int): Position {
        val degreesLeft = degrees * if (side == R) -1 else 1
        val radiansCounterClockwise = degreesLeft / 90 * Math.PI / 2
        val x = east * cos(radiansCounterClockwise) - north * sin(radiansCounterClockwise)
        val y = east * sin(radiansCounterClockwise) + north * cos(radiansCounterClockwise)
        return Position(y.roundToInt(), x.roundToInt())
    }

    fun add(other: Position): Position = Position(this.north + other.north, this.east + other.east)
}

enum class NavigationAction {
    N, S, E, W, L, R, F
}
