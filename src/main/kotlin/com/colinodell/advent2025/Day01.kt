package com.colinodell.advent2025

import kotlin.math.absoluteValue

class Day01(input: List<String>) {
    private val rotations = input.map {
        val v = it.drop(1).toInt()
        if (it.startsWith("L")) -v else v
    }

    private val clicks = rotations.flatMap { r -> List(r.absoluteValue) { if (r > 0) 1 else -1 } }

    fun solvePart1() = rotations
        .runningFold(50) { dial, rotation -> (dial + rotation).mod(100) }
        .count { it == 0 }

    fun solvePart2() = clicks
        .runningFold(50) { dial, click -> (dial + click).mod(100) }
        .count { it == 0 }
}
