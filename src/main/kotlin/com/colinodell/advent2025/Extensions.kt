package com.colinodell.advent2025

fun Int.clamp(
    min: Int,
    max: Int,
) = maxOf(min, minOf(max, this))
