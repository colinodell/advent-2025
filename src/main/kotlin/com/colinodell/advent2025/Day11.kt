package com.colinodell.advent2025

class Day11(input: List<String>) {
    private val devices = input.associate {
        val (device, outputs) = it.split(": ")
        device to outputs.split(" ")
    }

    private val pathCache = mutableMapOf<Pair<String, String>, Long>()

    private fun countPaths(current: String, target: String): Long = pathCache.getOrPut(current to target) {
        when (current) {
            target -> 1
            !in devices -> 0
            else -> devices[current]!!.sumOf { countPaths(it, target) }
        }
    }

    private fun countPaths(vararg path: String) =
        // The path between multiple points is just the product of the paths between each pair
        path.toList().zipWithNext().fold(1L) { acc, (a, b) -> acc * countPaths(a, b) }

    fun solvePart1() = countPaths("you", "out")

    fun solvePart2() = countPaths("svr", "dac", "fft", "out") + countPaths("svr", "fft", "dac", "out")
}
