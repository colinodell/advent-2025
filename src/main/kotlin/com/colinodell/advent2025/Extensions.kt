package com.colinodell.advent2025

import kotlin.math.max

fun Int.clamp(
    min: Int,
    max: Int,
) = maxOf(min, minOf(max, this))

val LongRange.size get() = endInclusive - start + 1

/**
 * Returns a new list with the ranges condensed into the smallest possible set of ranges.
 *
 * For example, given the ranges [4..6, 1..3, 7..9], this will return [1..9].
 */
fun Iterable<LongRange>.simplify(): List<LongRange> =
    sortedBy { it.first }.fold(mutableListOf()) { ranges, current ->
        when {
            ranges.isNotEmpty() && ranges.last().last >= current.first -> {
                val last = ranges.last()
                ranges[ranges.lastIndex] = last.first..max(last.last, current.last)
                ranges
            }
            else -> ranges.apply { add(current) }
        }
    }
