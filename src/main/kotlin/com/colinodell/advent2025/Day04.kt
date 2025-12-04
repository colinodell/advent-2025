package com.colinodell.advent2025

class Day04(input: List<String>) {
    private val rolls = input.toGrid(ignore = '.').keys

    fun solvePart1() = rolls.count { rolls.neighborsIncludingDiagonalsOf(it).size < 4 }
    fun solvePart2(): Int {
        val mutableRolls = rolls.toMutableSet()

        return generateSequence {
            mutableRolls
                .filter { mutableRolls.neighborsIncludingDiagonalsOf(it).size < 4 }
                .takeIf { it.isNotEmpty() }
                ?.onEach(mutableRolls::remove)
        }.sumOf { it.size }
    }
}
