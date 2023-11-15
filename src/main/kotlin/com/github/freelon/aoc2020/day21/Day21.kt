package com.github.freelon.aoc2020.day21

import com.github.freelon.aoc2020.DayTemplate

class Day21 : DayTemplate() {

    override fun partOne(input: String): Any {
        return getImpossibleIngredients(input).second
    }

    fun getImpossibleIngredients(input: String): Pair<MutableSet<String>, Int> {
        val contents = parse(input)
        val ingredients = contents.flatMap { it.first }.toSet()
        val allergenesOfRecipeWithIngredient: Map<String, Set<String>> = ingredients.associateWith { ingredient ->
            contents
                .filter { recipe -> recipe.first.contains(ingredient) }
                .flatMap { recipe -> recipe.second }
                .toSet()
        }

        val impossibleIngredientsForAnAllergene = mutableSetOf<String>()
        for ((ingredient, allergenesOfRecipes) in allergenesOfRecipeWithIngredient) {
            var ingredientCouldContainAllergene = false
            for (allergene in allergenesOfRecipes) {
                // if ingredient had this allergene it must be in all recipes that declare that allergene
                val isContainedInAllRecipes =
                    contents
                        .filter { (_, a) -> a.contains(allergene) }
                        .all { (i, _) -> i.contains(ingredient) }
                if (isContainedInAllRecipes)
                    ingredientCouldContainAllergene = true
            }
            if (!ingredientCouldContainAllergene)
                impossibleIngredientsForAnAllergene.add(ingredient)
        }

        val countAllAppearances = impossibleIngredientsForAnAllergene.sumOf { ingredient ->
            contents.count { recipe ->
                recipe.first.contains(ingredient)
            }
        }

        return Pair(impossibleIngredientsForAnAllergene, countAllAppearances)
    }

    private fun parse(input: String): List<Pair<Set<String>, Set<String>>> =
        input.lines().map { line ->
            val (i, a) = line.split(" (")
            val ingredients = i.split(" ").toSet()
            val allergenes = a.removePrefix("contains ").removeSuffix(")").split(", ").toSet()
            Pair(ingredients, allergenes)
        }

    override fun partTwo(input: String): Any {
        return Unit
    }
}

fun main() {
    Day21().run()
}