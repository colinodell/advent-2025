package com.colinodell.advent2025

import com.colinodell.advent2025.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 9: Movie Theater")
class Day09Test {
    private val exampleInput = inputAsListOfString("day09_example.txt")
    private val puzzleInput = inputAsListOfString("day09_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day09(exampleInput).solvePart1()).isEqualTo(50)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day09(puzzleInput).solvePart1()).isEqualTo(4759930955)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day09(exampleInput).solvePart2()).isEqualTo(24)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day09(puzzleInput).solvePart2()).isEqualTo(1525241870)
    }
}
