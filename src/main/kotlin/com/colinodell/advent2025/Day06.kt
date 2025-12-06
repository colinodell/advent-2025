package com.colinodell.advent2025

class Day06(private val input: List<String>) {
    private data class Problem(val operator: Char = '?', val numbers: List<Int> = emptyList()) {
        fun compute() = when (operator) {
            '+' -> numbers.sumOf { it.toLong() }
            '*' -> numbers.fold(1L) { acc, n -> acc * n }
            else -> 0L
        }
    }

    fun solvePart1() = input
        .map { it.trim().split(Regex("\\s+")) }
        .transpose()
        .map { column ->
            Problem(
                operator = column.last().first(),
                numbers = column.dropLast(1).map { it.toInt() },
            )
        }
        .sumOf { it.compute() }

    fun solvePart2() = buildList {
        input
            .map { it.toList() }
            .transpose()
            // Empty columns define the boundaries between problems
            .map { it.filter { it != ' ' } }
            // Add an empty list at the end to ensure the last problem gets added
            .let { it + listOf(emptyList()) }
            // Use fold() to track the state of problem parsing, calling add() on the outer list when we have a complete problem
            .fold(Problem()) { acc, column ->
                if (column.isEmpty()) {
                    add(acc)
                    Problem()
                } else if (column.lastOrNull() == '+' || column.lastOrNull() == '*') {
                    Problem(column.last(), acc.numbers + column.dropLast(1).joinToString("").toInt())
                } else {
                    Problem(acc.operator, acc.numbers + column.joinToString("").toInt())
                }
            }
    }
        .sumOf { it.compute() }
}
