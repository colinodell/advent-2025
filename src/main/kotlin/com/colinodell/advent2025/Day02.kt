package com.colinodell.advent2025

class Day02(input: String) {
    private val productIds = input.split(',').flatMap {
        val (start, end) = it.split('-').map(String::toLong)
        LongRange(start, end)
    }

    private val regexPart1 = Regex("""^(\d+?)\1$""")
    private val regexPart2 = Regex("""^(\d+?)\1+$""")

    fun solvePart1() = productIds.filter { regexPart1.matches(it.toString()) }.sum()
    fun solvePart2() = productIds.filter { regexPart2.matches(it.toString()) }.sum()
}
