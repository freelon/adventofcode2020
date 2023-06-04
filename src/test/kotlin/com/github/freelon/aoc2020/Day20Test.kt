package com.github.freelon.aoc2020

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day20Test {
    @Test
    fun part1() {
        assertEquals(20899048083289L, Day20().partOne(TEST_INPUT))
    }
}

const val TEST_INPUT = "" +
        "Tile 2311:\n" +
        "..##.#..#.\n" +
        "##..#.....\n" +
        "#...##..#.\n" +
        "####.#...#\n" +
        "##.##.###.\n" +
        "##...#.###\n" +
        ".#.#.#..##\n" +
        "..#....#..\n" +
        "###...#.#.\n" +
        "..###..###\n" +
        "\n" +
        "Tile 1951:\n" +
        "#.##...##.\n" +
        "#.####...#\n" +
        ".....#..##\n" +
        "#...######\n" +
        ".##.#....#\n" +
        ".###.#####\n" +
        "###.##.##.\n" +
        ".###....#.\n" +
        "..#.#..#.#\n" +
        "#...##.#..\n" +
        "\n" +
        "Tile 1171:\n" +
        "####...##.\n" +
        "#..##.#..#\n" +
        "##.#..#.#.\n" +
        ".###.####.\n" +
        "..###.####\n" +
        ".##....##.\n" +
        ".#...####.\n" +
        "#.##.####.\n" +
        "####..#...\n" +
        ".....##...\n" +
        "\n" +
        "Tile 1427:\n" +
        "###.##.#..\n" +
        ".#..#.##..\n" +
        ".#.##.#..#\n" +
        "#.#.#.##.#\n" +
        "....#...##\n" +
        "...##..##.\n" +
        "...#.#####\n" +
        ".#.####.#.\n" +
        "..#..###.#\n" +
        "..##.#..#.\n" +
        "\n" +
        "Tile 1489:\n" +
        "##.#.#....\n" +
        "..##...#..\n" +
        ".##..##...\n" +
        "..#...#...\n" +
        "#####...#.\n" +
        "#..#.#.#.#\n" +
        "...#.#.#..\n" +
        "##.#...##.\n" +
        "..##.##.##\n" +
        "###.##.#..\n" +
        "\n" +
        "Tile 2473:\n" +
        "#....####.\n" +
        "#..#.##...\n" +
        "#.##..#...\n" +
        "######.#.#\n" +
        ".#...#.#.#\n" +
        ".#########\n" +
        ".###.#..#.\n" +
        "########.#\n" +
        "##...##.#.\n" +
        "..###.#.#.\n" +
        "\n" +
        "Tile 2971:\n" +
        "..#.#....#\n" +
        "#...###...\n" +
        "#.#.###...\n" +
        "##.##..#..\n" +
        ".#####..##\n" +
        ".#..####.#\n" +
        "#..#.#..#.\n" +
        "..####.###\n" +
        "..#.#.###.\n" +
        "...#.#.#.#\n" +
        "\n" +
        "Tile 2729:\n" +
        "...#.#.#.#\n" +
        "####.#....\n" +
        "..#.#.....\n" +
        "....#..#.#\n" +
        ".##..##.#.\n" +
        ".#.####...\n" +
        "####.#.#..\n" +
        "##.####...\n" +
        "##..#.##..\n" +
        "#.##...##.\n" +
        "\n" +
        "Tile 3079:\n" +
        "#.#.#####.\n" +
        ".#..######\n" +
        "..#.......\n" +
        "######....\n" +
        "####.#..#.\n" +
        ".#...#.##.\n" +
        "#.#####.##\n" +
        "..#.###...\n" +
        "..#.......\n" +
        "..#.###...\n"