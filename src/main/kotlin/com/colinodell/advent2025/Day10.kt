package com.colinodell.advent2025

import kotlin.collections.fold
import kotlin.collections.map

class Day10(input: List<String>) {
    data class Machine(val indicatorLights: Int, val buttons: List<Set<Int>>, val joltages: List<Int>) {
        val buttonsAsBitmask by lazy {
            buttons.map { it.fold(0) { acc, i -> acc or (1 shl i) } }
        }

        companion object {
            fun fromLine(line: String): Machine {
                val (rawIndicatorLights, rawButtons, rawJoltages) =
                    Regex("""\[([.#]+)\] ([^{]+) \{([^}]+)\}""")
                        .matchEntire(line)!!
                        .destructured

                val indicatorLights = rawIndicatorLights
                    .withIndex()
                    .sumOf { (i, char) -> if (char == '#') 1 shl i else 0 }

                val buttons = rawButtons
                    .split(' ')
                    .map { it.trim('(', ')').split(',').map(String::toInt).toSet() }

                val joltages = rawJoltages.split(',').map(String::toInt)

                return Machine(indicatorLights, buttons, joltages)
            }
        }
    }

    private val machines = input.map { Machine.fromLine(it) }

    private fun findMinimumPressesToSetIndicatorLights(machine: Machine): Int {
        val seen = mutableSetOf(0)
        val queue = ArrayDeque<Pair<Int, Int>>().apply { add(0 to 0) }

        while (queue.isNotEmpty()) {
            val (currentLights, presses) = queue.removeFirst()

            if (currentLights == machine.indicatorLights) return presses

            machine.buttonsAsBitmask.forEach { button ->
                val nextLights = currentLights xor button
                if (seen.add(nextLights)) {
                    queue.add(nextLights to presses + 1)
                }
            }
        }

        throw IllegalStateException("No solution found")
    }

    fun solvePart1() = machines.sumOf { findMinimumPressesToSetIndicatorLights(it) }
}
