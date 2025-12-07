package com.colinodell.advent2025

import com.colinodell.advent2025.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 7: Laboratories")
class Day07Test {
    private val exampleInput = inputAsListOfString("day07_example.txt")
    private val puzzleInput = inputAsListOfString("day07_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day07(exampleInput).solvePart1()).isEqualTo(21)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day07(puzzleInput).solvePart1()).isEqualTo(1573)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day07(exampleInput).solvePart2()).isEqualTo(40)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day07(puzzleInput).solvePart2()).isEqualTo(15093663987272)
    }
}
