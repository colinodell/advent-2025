package com.colinodell.advent2025

class Day09(input: List<String>) {
    private val redTiles = input.map {
        it.split(',').map(String::toInt).let { (x, y) -> Vector2(x, y) }
    }

    private val rectangles = redTiles.permutationPairs().map { Region(it.first, it.second) }

    private val edges = (redTiles + redTiles.first()).zipWithNext().map { (a, b) -> Region(a, b) }

    fun solvePart1() = rectangles.maxOf { it.area }

    fun solvePart2() = rectangles.filter { edges.none { edge -> it.overlaps(edge) } }.maxOf { it.area }
}
