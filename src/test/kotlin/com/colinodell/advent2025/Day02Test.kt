package com.colinodell.advent2025

import com.colinodell.advent2025.Inputs.inputAsText
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 2: Gift Shop")
class Day02Test {
    private val exampleInput = inputAsText("day02_example.txt")
    private val puzzleInput = inputAsText("day02_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day02(exampleInput).solvePart1()).isEqualTo(1227775554)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day02(puzzleInput).solvePart1()).isEqualTo(13108371860)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day02(exampleInput).solvePart2()).isEqualTo(4174379265)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day02(puzzleInput).solvePart2()).isEqualTo(22471660255)
    }
}
