package com.colinodell.advent2025

class Day08(input: List<String>) {
    private val junctionBoxes = input.map { line ->
        val (x, y, z) = line.split(',').map(String::toInt)
        Vector3(x, y, z)
    }

    private val pairsSortedByDistance = junctionBoxes
        .permutationPairs()
        .sortedBy { (a, b) -> a.distanceSquaredTo(b) }

    fun solve(maxConnections: Int = Int.MAX_VALUE): Int {
        val circuits = junctionBoxes.map { mutableSetOf(it) }.toMutableList()

        for (i in 0 until minOf(maxConnections, pairsSortedByDistance.size)) {
            val (a, b) = pairsSortedByDistance[i]
            val circuitA = circuits.first { a in it }
            val circuitB = circuits.first { b in it }

            // Merge circuits if they're different
            if (circuitA !== circuitB) {
                circuitA.addAll(circuitB)
                circuits.remove(circuitB)

                // Part 2: Return when all junction boxes are in one circuit
                if (circuits.size == 1) {
                    return a.x * b.x
                }
            }
        }

        // Part 1: Product of three largest circuit sizes
        return circuits
            .map { it.size }
            .sortedDescending()
            .take(3)
            .reduce(Int::times)
    }
}
