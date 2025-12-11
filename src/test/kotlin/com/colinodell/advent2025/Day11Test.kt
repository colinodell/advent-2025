package com.colinodell.advent2025

import com.colinodell.advent2025.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 11: Reactor")
class Day11Test {
    private val exampleInput1 = inputAsListOfString("day11_example1.txt")
    private val exampleInput2 = inputAsListOfString("day11_example2.txt")
    private val puzzleInput = inputAsListOfString("day11_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day11(exampleInput1).solvePart1()).isEqualTo(5)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day11(puzzleInput).solvePart1()).isEqualTo(658)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day11(exampleInput2).solvePart2()).isEqualTo(2)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day11(puzzleInput).solvePart2()).isEqualTo(371113003846800)
    }
}
