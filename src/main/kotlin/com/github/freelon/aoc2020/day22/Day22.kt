package com.github.freelon.aoc2020.day22

import com.github.freelon.aoc2020.DayTemplate

fun main() {
    Day22().run()
}

class Day22 : DayTemplate() {
    override fun partOne(input: String): Int {
        val players = input.trim().split("\n\n").map { block ->
            block.lines().drop(1).map { it.toInt() }.toMutableList()
        }
        while (players.all { it.isNotEmpty() }) {
            val a = players[0].removeAt(0)
            val b = players[1].removeAt(0)
            if (a > b) {
                players[0].add(a)
                players[0].add(b)
            } else {
                players[1].add(b)
                players[1].add(a)
            }
        }
        return players.find { it.isNotEmpty() }!!.reversed().mapIndexed { index, i -> i * (index + 1) }.sum()
    }

    override fun partTwo(input: String): Any {
        val players = input.trim().split("\n\n").map { block ->
            block.lines().drop(1).map { it.toInt() }.toMutableList()
        }
        val (winner, cards) = play(players)
        return cards[winner].reversed().mapIndexed { index, i -> i * (index + 1) }.sum()
    }

    private var games = 0
    private fun play(players: List<MutableList<Int>>): Pair<Int, List<List<Int>>> {
        val game = ++games
        println("=== Game $game ===")
        val playedGames = mutableSetOf<String>()
        var round = 0
        while (players.none { it.isEmpty() }) {
            println()
            println("-- Round ${++round} (Game $game) --")
            players.forEachIndexed { index, it -> println("Player ${index + 1}'s deck: ${it.joinToString()}") }
            val gameState = players.joinToString(separator = " | ") { it.joinToString() }
            if (playedGames.contains(gameState))
                return Pair(0, players.map { it.toList() })
            else
                playedGames.add(gameState)

            players.forEachIndexed { index, it -> println("Player ${index + 1} plays: ${it[0]}") }
            val a = players[0].removeAt(0)
            val b = players[1].removeAt(0)
            val playedCards = listOf(a, b)
            val winner = if (players[0].size >= a && players[1].size >= b) {
                println("Playing a sub-game to determine the winner...")
                println()
                val (w, _) = play(
                    listOf(
                        players[0].subList(0, a).toMutableList(),
                        players[1].subList(0, b).toMutableList()
                    )
                )
                println()
                println("...anyway, back to game $game.")
                w
            } else {
                if (a > b)
                    0
                else
                    1
            }
            println("Player ${winner + 1} wins round $round of game $game!")
            players[winner].add(playedCards[winner])
            players[winner].add(playedCards[(1 + winner) % 2])
        }
        val winner = players.indexOfFirst { it.isNotEmpty() }
        println("The winner of game $game is player ${winner + 1}!")
        return Pair(winner, players.map { it.toList() })
    }
}