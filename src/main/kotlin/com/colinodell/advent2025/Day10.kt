package com.colinodell.advent2025

import com.microsoft.z3.Context
import com.microsoft.z3.IntExpr
import com.microsoft.z3.IntNum
import com.microsoft.z3.Status
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

    // Heavily based on https://github.com/Jadarma/advent-of-code-kotlin-solutions/blob/92e1c6945b689ab88f271ab235447e626d791f0d/solutions/aockt/y2025/Y2025D10.kt
    private fun findMinimumPressesToSetJoltages(machine: Machine): Int = Context().use { ctx ->
        val optimizer = ctx.mkOptimize()

        // Create variables to count button presses
        val buttonVars = machine.buttons.indices
            .map { ctx.mkIntConst("button_$it") }
            .onEach { button -> optimizer.Add(ctx.mkGe(button, ctx.mkInt(0))) }
            .toTypedArray()

        // Add constraints: for each joltage counter, sum of affecting buttons equals target
        machine.joltages.forEachIndexed { counterIdx, targetValue ->
            val relevantButtons = machine.buttons.indices
                .filter { counterIdx in machine.buttons[it] }
                .map { buttonVars[it] }
                .toTypedArray()

            optimizer.Add(ctx.mkEq(ctx.mkAdd(*relevantButtons), ctx.mkInt(targetValue)))
        }

        // Minimize total button presses
        val totalPresses = ctx.mkAdd(*buttonVars) as IntExpr
        optimizer.MkMinimize(totalPresses)

        // Solve and return result
        check(optimizer.Check() == Status.SATISFIABLE) { "No solution exists for joltage configuration" }
        return (optimizer.model.evaluate(totalPresses, false) as IntNum).int
    }

    fun solvePart1() = machines.sumOf { findMinimumPressesToSetIndicatorLights(it) }

    fun solvePart2() = machines.sumOf { findMinimumPressesToSetJoltages(it) }
}
