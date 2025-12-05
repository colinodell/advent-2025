package com.colinodell.advent2025

import com.colinodell.advent2025.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 5: Cafeteria")
class Day05Test {
    private val exampleInput = inputAsListOfString("day05_example.txt")
    private val puzzleInput = inputAsListOfString("day05_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day05(exampleInput).solvePart1()).isEqualTo(3)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day05(puzzleInput).solvePart1()).isEqualTo(758)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day05(exampleInput).solvePart2()).isEqualTo(14)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day05(puzzleInput).solvePart2()).isEqualTo(343143696885053)
    }
}
