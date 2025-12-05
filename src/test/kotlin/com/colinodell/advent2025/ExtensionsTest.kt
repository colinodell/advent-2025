package com.colinodell.advent2025

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ExtensionsTest {
    @Nested
    inner class IterableLongRangeSimplifyTests {
        @Test
        fun `no overlap`() {
            Assertions.assertThat(listOf(1L..3L, 5L..7L).simplify()).containsExactly(1L..3L, 5L..7L)
        }

        @Test
        fun `some overlap`() {
            Assertions.assertThat(listOf(1L..4L, 3L..7L, 9L..10L).simplify()).containsExactly(1L..7L, 9L..10L)
        }

        @Test
        fun `full overlap`() {
            Assertions.assertThat(listOf(1L..4L, 2L..3L, 3L..7L, 6L..10L).simplify()).containsExactly(1L..10L)
        }
    }
}
