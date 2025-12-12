package com.colinodell.advent2025

import com.colinodell.advent2025.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 12: Christmas Tree Farm")
class Day12Test {
    private val puzzleInput = inputAsListOfString("day12_input.txt")

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day12(puzzleInput).solvePart1()).isEqualTo(481)
    }
}
