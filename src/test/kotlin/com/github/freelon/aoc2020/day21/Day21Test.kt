package com.github.freelon.aoc2020.day20

import com.github.freelon.aoc2020.day21.Day21
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day21Test {
    val input = """mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
trh fvjkl sbzzf mxmxvkd (contains dairy)
sqjhc fvjkl (contains soy)
sqjhc mxmxvkd sbzzf (contains fish)"""


    @Test
    fun example1() {
        assertEquals(setOf("kfcds", "nhms", "sbzzf", "trh"), Day21().getImpossibleIngredients(input).first)
        assertEquals(5, Day21().getImpossibleIngredients(input).second)
    }

    @Test
    fun example2() {
        assertEquals("mxmxvkd,sqjhc,fvjkl", Day21().partTwo(input))
    }
}