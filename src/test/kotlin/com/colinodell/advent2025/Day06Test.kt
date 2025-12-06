package com.colinodell.advent2025

import com.colinodell.advent2025.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 6: Trash Compactor")
class Day06Test {
    private val exampleInput = inputAsListOfString("day06_example.txt")
    private val puzzleInput = inputAsListOfString("day06_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day06(exampleInput).solvePart1()).isEqualTo(4277556)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day06(puzzleInput).solvePart1()).isEqualTo(5316572080628)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day06(exampleInput).solvePart2()).isEqualTo(3263827)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day06(puzzleInput).solvePart2()).isEqualTo(11299263623062)
    }
}
