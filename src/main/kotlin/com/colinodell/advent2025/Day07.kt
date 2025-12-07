package com.colinodell.advent2025

class Day07(input: List<String>) {
    data class State(val timesSplit: Int = 0, val beams: Set<Vector2>)

    private val grid = input.toGrid(ignore = '.')
    private val splitters = grid.filterValues { it == '^' }.keys.toSet()
    private val source = grid.keys.first { grid[it] == 'S' }

    private val down = Vector2(0, 1)
    private val left = Vector2(-1, 0)
    private val right = Vector2(1, 0)

    fun solvePart1() = (0 until grid.height())
        .fold(State(beams = setOf(source))) { state, _ ->
            val nextBeams = state.beams.map { it + down }
            val (onSplitters, notOnSplitters) = nextBeams.partition { it in splitters }
            val splitBeams = onSplitters.flatMap { listOf(it + left, it + right) }

            State(
                timesSplit = state.timesSplit + onSplitters.size,
                beams = (notOnSplitters + splitBeams).toSet(),
            )
        }.timesSplit

    fun solvePart2(): Long {
        val cache = mutableMapOf<Vector2, Long>()

        fun dfs(pos: Vector2): Long = cache.getOrPut(pos) {
            when {
                pos.y == grid.height() - 1 -> 1L
                pos + down in splitters -> dfs(pos + down + left) + dfs(pos + down + right)
                else -> dfs(pos + down)
            }
        }

        return dfs(source)
    }
}
