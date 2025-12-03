package com.colinodell.advent2025

class Day03(val input: List<String>) {
    private fun maximumJoltage(bank: String, take: Int): Long {
        var joltage = 0L

        // We choose `take` batteries by repeatedly selecting the largest battery from the leftmost possible position
        // (such that enough batteries remain to complete the selection).
        var startIndex = 0
        repeat(take) { i ->
            // Ignore the rightmost `take - i - 1` batteries, because we need to leave
            // enough batteries to pick from in the remaining iterations.
            val endIndex = bank.length - (take - i - 1)
            val largestBattery = bank.substring(startIndex, endIndex).max()
            joltage = joltage * 10 + largestBattery.digitToInt()

            // Move past the selected battery for the next iteration
            // since each battery can only be used once, and we know
            // there aren't better options to the left.
            startIndex = bank.indexOf(largestBattery, startIndex) + 1
        }

        return joltage
    }

    fun solvePart1() = input.sumOf { maximumJoltage(it, 2) }
    fun solvePart2() = input.sumOf { maximumJoltage(it, 12) }
}
