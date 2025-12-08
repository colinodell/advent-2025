package com.colinodell.advent2025

import com.colinodell.advent2025.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 8: Playground")
class Day08Test {
    private val exampleInput = inputAsListOfString("day08_example.txt")
    private val puzzleInput = inputAsListOfString("day08_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day08(exampleInput).solve(10)).isEqualTo(40)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day08(puzzleInput).solve(1000)).isEqualTo(102816)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day08(exampleInput).solve()).isEqualTo(25272)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day08(puzzleInput).solve()).isEqualTo(100011612)
    }
}
