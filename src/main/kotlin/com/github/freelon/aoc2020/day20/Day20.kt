package com.github.freelon.aoc2020.day20

import com.github.freelon.aoc2020.DayTemplate

class Day20 : DayTemplate() {
    override fun partOne(input: String): Any {
        val grid = solvePuzzle(input)

        val xMin = grid.keys.minBy { it.x }.x
        val xMax = grid.keys.maxBy { it.x }.x
        val yMin = grid.keys.minBy { it.y }.y
        val yMax = grid.keys.maxBy { it.y }.y

        for (y in yMin..yMax) {
            var row = ""
            var mods = ""
            for (x in xMin..xMax) {
                val t = grid[Point(x, y)]!!
                if (grid.containsKey(Point(x, y))) {
                    row += "${t.id} "
                    mods += "(${t.mods})\t "
                }
            }
            println(row)
            println(mods)
        }

        val bottomLeft = grid[Point(xMin, yMin)]!!.id.toLong()
        val bottomRight = grid[Point(xMax, yMin)]!!.id.toLong()
        val topLeft = grid[Point(xMin, yMax)]!!.id.toLong()
        val topRight = grid[Point(xMax, yMax)]!!.id.toLong()

        println()
        createImage(grid, true, Tile::pixels).forEach { println(it) }

        return bottomLeft * bottomRight * topLeft * topRight
    }

    override fun partTwo(input: String): Any {
        val puzzle = solvePuzzle(input)

        val image = createImage(
            puzzle, false, Tile::cutOfBorder
        )

        val rotations = image.createVariants()
        rotations.forEachIndexed { index, strings -> println("$index: ${strings.first()}") }

        return rotations.map(::seaRoughness).maxBy { it.first }.second
    }
}

val MONSTER = """                  # 
#    ##    ##    ###
 #  #  #  #  #  #   """.lines()


fun seaRoughness(image: List<String>): Pair<Long, Int> {
    val pair = markSeaMonsters(image)
    val sea = pair.first
    val monsterCount = pair.second

    val result = Pair(monsterCount, sea.sumOf { seaLine -> seaLine.count { seaPixel -> seaPixel == '#' } })
    println(result)
    return result
}

fun markSeaMonsters(image: List<String>): Pair<List<String>, Long> {
    val sea = image.map { StringBuilder(it) }

    val imageWidth = image.first().length
    val monsterWidth = MONSTER.first().length
    val imageHeight = image.size
    val monsterHeight = MONSTER.size
    var monsterCount = 0L
    for (startY in 0..imageHeight - monsterHeight) {
        for (startX in 0..imageWidth - monsterWidth) {
            var allLinesFit = true
            for (monsterY in MONSTER.indices) {
                val seaLine = sea[startY + monsterY]
                val monsterLine = MONSTER[monsterY]
                val slice = seaLine.substring(startX until startX + monsterWidth)
                var lineFits = true
                for (x in slice.indices) {
                    val monsterPixel = monsterLine[x]
                    val seaPixel = slice[x]
                    if (monsterPixel == '#' && (seaPixel != '#'))
                        lineFits = false
                }
                if (!lineFits) {
                    allLinesFit = false
                    break
                }
            }
            if (allLinesFit) {
                for (monsterY in MONSTER.indices) {
                    val seaLine = sea[startY + monsterY]
                    val monsterLine = MONSTER[monsterY]
                    for (x in 0 until monsterWidth) {
                        val monsterPixel = monsterLine[x]
                        if (monsterPixel == '#')
                            seaLine[startX + x] = 'O'
                    }
                }
                monsterCount += 1
            }
        }
    }

    return Pair(sea.map { it.toString() }, monsterCount)
}

fun createImage(
    puzzle: Map<Point, Tile>, makeGaps: Boolean, tilePixelModified: (Tile) -> List<String>
): List<String> {
    val xMin = puzzle.keys.minBy { it.x }.x
    val xMax = puzzle.keys.maxBy { it.x }.x
    val yMin = puzzle.keys.minBy { it.y }.y
    val yMax = puzzle.keys.maxBy { it.y }.y

    val image = mutableListOf<String>()
    for (y in yMin..yMax) {
        val rows = tilePixelModified(puzzle[Point(xMin, y)]!!)
        val combinedRows = rows.mapIndexed { index, s ->
            var row = s + if (makeGaps) " " else ""
            for (x in xMin + 1..xMax) {
                val v = tilePixelModified(puzzle[Point(x, y)]!!)[index]
                row += v + if (makeGaps) " " else ""
            }
            row
        }
        image.addAll(combinedRows)
        if (makeGaps && y < yMax) image.add("")
    }
    return image.map { it.trim() }
}


fun solvePuzzle(input: String): MutableMap<Point, Tile> {
    val initial = parse(input)
    val all = createTileVariants(initial)
    val seed = initial.first()

    return puzzle(seed, all)
}

fun puzzle(
    seed: Tile, all: List<Tile>
): MutableMap<Point, Tile> {
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
                val candidate = all.filter { it.id != image.id }.firstOrNull { it.bottom().contentEquals(image.top()) }
                if (candidate != null) {
                    grid[point.up()] = candidate
                    mutated = true
                    break
                }
            }
            if (point.down() !in grid) {
                val candidate = all.filter { it.id != image.id }.firstOrNull { it.top().contentEquals(image.bottom()) }
                if (candidate != null) {
                    grid[point.down()] = candidate
                    mutated = true
                    break
                }
            }
            if (point.left() !in grid) {
                val candidate = all.filter { it.id != image.id }.firstOrNull { it.right().contentEquals(image.left()) }
                if (candidate != null) {
                    grid[point.left()] = candidate
                    mutated = true
                    break
                }
            }
            if (point.right() !in grid) {
                val candidate = all.filter { it.id != image.id }.firstOrNull { it.left().contentEquals(image.right()) }
                if (candidate != null) {
                    grid[point.right()] = candidate
                    mutated = true
                    break
                }
            }
        }
    }
    return grid
}

fun createTileVariants(initial: List<Tile>) = initial.flatMap { createTileVariants(it) }

fun createTileVariants(original: Tile) = listOf(
    original, original.flipVertical(), original.flipHorizontal(), original.flipHorizontal().flipVertical()
).flatMap {
    listOf(
        it, it.rotate(), it.rotate().rotate(), it.rotate().rotate().rotate()
    )
}

fun parse(input: String) = input.trim().split("\n\n").map { Tile(it) }

data class Point(val x: Int, val y: Int) {
    fun left() = Point(x - 1, y)
    fun right() = Point(x + 1, y)
    fun up() = Point(x, y - 1)
    fun down() = Point(x, y + 1)
}

data class Tile(
    val id: Int,
    /**
     * Each list entry is one row. First (pixels[0]) is the highest row visually.
     */
    val pixels: List<String>, val mods: String = ""
) {

    init {
        assert(pixels.count() == 10)
        assert(pixels.all { it.count() == 10 })
    }

    constructor(input: String) : this(
        input.trim().lines().first().removePrefix("Tile ").removeSuffix(":").toInt(), input.trim().lines().drop(1)
    )

    fun flipVertical() = Tile(id, pixels.flipVertical(), mods + "v")

    fun flipHorizontal() = Tile(id, pixels.flipHorizontal(), mods + "h")

    fun rotate(): Tile {
        val transposed = List(10) { StringBuilder("          ") }
        for (x in 0 until 10) for (y in 0 until 10) {
            transposed[x][y] = pixels[y][x]
        }

        return Tile(id, pixels.rotate(), mods + "r")
    }

    fun top() = pixels.first()
    fun bottom() = pixels.last()
    fun left() = pixels.map { it.first() }.joinToString()
    fun right() = pixels.map { it.last() }.joinToString()

    fun cutOfBorder() = pixels.subList(1, pixels.size - 1).map { it.substring(1 until it.length - 1) }
}

fun List<String>.flipVertical() = this.reversed()
fun List<String>.flipHorizontal() = this.map { it.reversed() }

fun List<String>.rotate(): List<String> {
    val transposed = List(this.size) { StringBuilder(" ".repeat(this.size)) }
    for (x in indices) for (y in indices) {
        transposed[x][y] = this[y][x]
    }
    return transposed.map { it.reversed().toString() }
}

fun List<String>.createVariants() =
    listOf(this, this.flipVertical(), this.flipHorizontal(), this.flipHorizontal().flipVertical()).flatMap {
        listOf(
            it,
            it.rotate(),
            it.rotate().rotate(),
            it.rotate().rotate().rotate()
        )
    }

fun main() {
    Day20().run()
    // 5907237891361 too low
}
