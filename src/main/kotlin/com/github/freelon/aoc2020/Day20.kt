package com.github.freelon.aoc2020

class Day20 : DayTemplate() {
    override fun partOne(input: String): Any {
        val initial = input.trim().split("\n\n").map { Tile(it) }
        val all = initial.flatMap { original ->
            listOf(
                original,
                original.flipVertical(),
                original.flipHorizontal(),
                original.flipHorizontal().flipVertical()
            ).flatMap { listOf(it, it.rotate(), it.rotate().rotate(), it.rotate().rotate().rotate()) }
        }
        val seed = initial.first()

        val grid = mutableMapOf((Point(0, 0)) to seed)
        var mutated = true
        while (mutated) {
            mutated = false
            // check all edges of every point in the grid if the neighboring point is empty
            // if so, find a matching image
            // add the matching image to the grid
            for (pair in grid.entries) {
                val point = pair.key
                val image = pair.value
                if (point.up() !in grid) {
                    val candidate =
                        all.filter { it.id != image.id }.firstOrNull { it.bottom().contentEquals(image.top()) }
                    if (candidate != null) {
                        grid[point.up()] = candidate
                        mutated = true
                        break
                    }
                }
                if (point.down() !in grid) {
                    val candidate =
                        all.filter { it.id != image.id }.firstOrNull { it.top().contentEquals(image.bottom()) }
                    if (candidate != null) {
                        grid[point.down()] = candidate
                        mutated = true
                        break
                    }
                }
                if (point.left() !in grid) {
                    val candidate =
                        all.filter { it.id != image.id }.firstOrNull { it.right().contentEquals(image.left()) }
                    if (candidate != null) {
                        grid[point.left()] = candidate
                        mutated = true
                        break
                    }
                }
                if (point.right() !in grid) {
                    val candidate =
                        all.filter { it.id != image.id }.firstOrNull { it.left().contentEquals(image.right()) }
                    if (candidate != null) {
                        grid[point.right()] = candidate
                        mutated = true
                        break
                    }
                }
            }
        }

        val xMin = grid.keys.minBy { it.x }.x
        val xMax = grid.keys.maxBy { it.x }.x
        val yMin = grid.keys.minBy { it.y }.y
        val yMax = grid.keys.maxBy { it.y }.y

        for (y in yMax downTo yMin) {
            for (x in xMin..xMax) {
                val s = if (grid.containsKey(Point(x, y)))
                    "${grid[Point(x, y)]!!.id} "
                else
                    "     "
                print(s)
            }
            println()
        }

        val bottomLeft = grid[Point(xMin, yMin)]!!.id.toLong()
        val bottomRight = grid[Point(xMax, yMin)]!!.id.toLong()
        val topLeft = grid[Point(xMin, yMax)]!!.id.toLong()
        val topRight = grid[Point(xMax, yMax)]!!.id.toLong()

        return bottomLeft * bottomRight * topLeft * topRight
    }

    override fun partTwo(input: String): Any {
        TODO("Not yet implemented")
    }
}

data class Point(val x: Int, val y: Int) {
    fun left() = Point(x - 1, y)
    fun right() = Point(x + 1, y)
    fun up() = Point(x, y + 1)
    fun down() = Point(x, y - 1)
}

data class Tile(
    val id: Int,
    val pixels: List<String>
) {

    init {
        assert(pixels.count() == 10)
        assert(pixels.all { it.count() == 10 })
    }

    constructor(input: String) : this(
        input.trim().lines().first().removePrefix("Tile ").removeSuffix(":").toInt(),
        input.trim().lines().drop(1)
    )

    fun flipVertical() = Tile(id, pixels.reversed())

    fun flipHorizontal() = Tile(id, pixels.map { it.reversed() })

    fun rotate(): Tile {
        val transposed = List(10) { StringBuilder("          ") }
        for (x in 0 until 10)
            for (y in 0 until 10) {
                transposed[x][y] = pixels[y][x]
            }

        return Tile(id, transposed.map { it.reversed().toString() })
    }

    fun top() = pixels.first()
    fun bottom() = pixels.last()
    fun left() = pixels.map { it.first() }.joinToString()
    fun right() = pixels.map { it.last() }.joinToString()
}

fun main() {
    Day20().run()
    // 5907237891361 too low
}
