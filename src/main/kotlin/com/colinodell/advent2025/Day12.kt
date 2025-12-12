package com.colinodell.advent2025

class Day12(private val input: List<String>) {
    fun solvePart1() = input
        .filter { 'x' in it }
        .count {
            val (left, right) = it.split(": ")
            val (width, height) = left.split("x").map(String::toInt)
            val shapesNeeded = right.split(" ").sumOf(String::toInt)
            (width / 3) * (height / 3) >= shapesNeeded
        }
}
