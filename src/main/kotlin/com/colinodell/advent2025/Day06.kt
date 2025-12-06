package com.colinodell.advent2025

class Day06(private val input: List<String>) {
    fun solvePart1() = input
        .map { it.trim().split(Regex("\\s+")) }
        .transpose()
        .sumOf {
            when (it.last()) {
                "+" -> it.dropLast(1).fold(0L) { acc, c -> acc + c.toInt() }
                "*" -> it.dropLast(1).fold(1L) { acc, c -> acc * c.toInt() }
                else -> 0L
            }
        }

    fun solvePart2() = input
        .map { it.toList() }
        .transpose()
        .map { it.filter { it != ' ' } }
        .let {
            var operator = '?'
            var numbers = mutableListOf<Int>()
            var total = 0L
            // Add an empty list at the end to handle the final problem
            for (column in it + listOf(emptyList())) {
                if (column.isEmpty()) {
                    // End of problem
                    total += when (operator) {
                        '+' -> numbers.sumOf { it.toLong() }
                        '*' -> numbers.fold(1L) { acc, n -> acc * n }
                        else -> 0L
                    }
                    // Reset for next problem
                    operator = '?'
                    numbers = mutableListOf()
                    continue
                }

                if (column.last() == '+' || column.last() == '*') {
                    operator = column.last()
                    numbers.add(column.dropLast(1).joinToString("").toInt())
                } else {
                    numbers.add(column.joinToString("").toInt())
                }
            }
            total
        }
}
