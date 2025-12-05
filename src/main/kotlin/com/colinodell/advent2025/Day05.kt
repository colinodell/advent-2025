package com.colinodell.advent2025

class Day05(input: List<String>) {
    private val freshRanges = input
        .filter { it.contains('-') }
        .map {
            val r = it.split("-")
            r[0].toLong()..r[1].toLong()
        }
        .simplify()

    private val ingredients = input
        .filter { !it.contains('-') && it.isNotBlank() }
        .map { it.toLong() }

    fun solvePart1() = ingredients.count { ingredient ->
        freshRanges.any { range -> ingredient in range }
    }

    fun solvePart2() = freshRanges.sumOf { it.size }
}
