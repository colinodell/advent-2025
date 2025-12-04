package com.colinodell.advent2025

import kotlin.math.abs
import kotlin.math.max

data class Vector2(val x: Int, val y: Int) {
    operator fun plus(other: Vector2) = Vector2(x + other.x, y + other.y)

    operator fun plus(other: Vector2L) = Vector2L(x + other.x, y + other.y)

    operator fun minus(other: Vector2) = Vector2(x - other.x, y - other.y)

    operator fun minus(other: Vector2L) = Vector2L(x - other.x, y - other.y)

    operator fun times(scale: Int) = Vector2(x * scale, y * scale)

    operator fun times(scale: Long) = Vector2L(x * scale, y * scale)

    operator fun div(scale: Int) = Vector2(x / scale, y / scale)

    operator fun div(scale: Long) = Vector2L(x / scale, y / scale)

    operator fun rem(n: Int) = Vector2(x % n, y % n)

    operator fun rem(n: Long) = Vector2L(x % n, y % n)

    fun negativeSafeModulo(n: Int) = Vector2((x + n) % n, (y + n) % n)

    fun normalize() = Vector2(x.clamp(-1, 1), y.clamp(-1, 1))

    fun isTouching(other: Vector2) = abs(x - other.x) <= 1 && abs(y - other.y) <= 1

    fun manhattanDistanceTo(other: Vector2) = abs(x - other.x) + abs(y - other.y)

    fun size() = abs(x) + abs(y)

    override fun toString() = "($x, $y)"

    fun neighbors() =
        setOf(
            Vector2(x - 1, y),
            Vector2(x + 1, y),
            Vector2(x, y - 1),
            Vector2(x, y + 1),
        )

    fun neighborsIncludingDiagonals() =
        neighbors() +
            setOf(
                Vector2(x - 1, y - 1),
                Vector2(x - 1, y + 1),
                Vector2(x + 1, y - 1),
                Vector2(x + 1, y + 1),
            )

    fun withX(x: Int) = Vector2(x, y)

    fun withY(y: Int) = Vector2(x, y)

    fun directionTo(other: Vector2) =
        when ((other - this).normalize()) {
            Vector2(0, -1) -> Direction.NORTH
            Vector2(0, 1) -> Direction.SOUTH
            Vector2(-1, 0) -> Direction.WEST
            Vector2(1, 0) -> Direction.EAST
            else -> null
        }

    fun follow(directions: Iterable<Direction>) =
        directions.asSequence().runningFold(this) { pos, dir -> pos + dir.vector() }

    fun asDirections() = sequence {
        var current = Vector2(0, 0)
        while (current != this@Vector2) {
            val direction = when {
                current.x < this@Vector2.x -> Direction.EAST
                current.x > this@Vector2.x -> Direction.WEST
                current.y < this@Vector2.y -> Direction.SOUTH
                else -> Direction.NORTH
            }
            yield(direction)
            current += direction.vector()
        }
    }
}

data class Vector2L(val x: Long, val y: Long) {
    operator fun plus(other: Vector2L) = Vector2L(x + other.x, y + other.y)

    operator fun minus(other: Vector2L) = Vector2L(x - other.x, y - other.y)

    operator fun times(scale: Int) = Vector2L(x * scale, y * scale)

    operator fun times(scale: Long) = Vector2L(x * scale, y * scale)

    operator fun div(scale: Int) = Vector2L(x / scale, y / scale)

    operator fun div(scale: Long) = Vector2L(x / scale, y / scale)

    operator fun rem(n: Int) = Vector2L(x % n, y % n)

    operator fun rem(n: Long) = Vector2L(x % n, y % n)

    fun size() = abs(x) + abs(y)

    override fun toString() = "($x, $y)"
}

enum class Direction {
    NORTH,
    SOUTH,
    WEST,
    EAST,
    ;

    enum class Rotation {
        LEFT,
        RIGHT,
    }

    fun vector() =
        when (this) {
            NORTH -> Vector2(0, -1)
            SOUTH -> Vector2(0, 1)
            WEST -> Vector2(-1, 0)
            EAST -> Vector2(1, 0)
        }

    fun turn(rotation: Rotation) =
        when (rotation) {
            Rotation.LEFT -> turnLeft()
            Rotation.RIGHT -> turnRight()
        }

    fun turnLeft() =
        when (this) {
            NORTH -> WEST
            SOUTH -> EAST
            WEST -> SOUTH
            EAST -> NORTH
        }

    fun turnRight() =
        when (this) {
            NORTH -> EAST
            SOUTH -> WEST
            WEST -> NORTH
            EAST -> SOUTH
        }

    fun opposite() =
        when (this) {
            NORTH -> SOUTH
            SOUTH -> NORTH
            WEST -> EAST
            EAST -> WEST
        }

    override fun toString() =
        when (this) {
            NORTH -> "^"
            SOUTH -> "v"
            WEST -> "<"
            EAST -> ">"
        }

    companion object {
        fun from(c: Char): Direction =
            when (c) {
                '^', 'U', 'N' -> NORTH
                'v', 'D', 'S' -> SOUTH
                '<', 'L', 'W' -> WEST
                '>', 'R', 'E' -> EAST
                else -> throw IllegalArgumentException("Unknown direction: $c")
            }
    }
}

// A line that is at some multiple of 45 degrees (horizontal, vertical, or diagonal)
data class Line(val start: Vector2, val end: Vector2) {
    val points: List<Vector2> by lazy {
        val xDiff = end.x - start.x
        val yDiff = end.y - start.y
        val stepCount = max(abs(xDiff), abs(yDiff))

        val xStep = xDiff / stepCount
        val yStep = yDiff / stepCount

        (0..stepCount).map { Vector2(start.x + it * xStep, start.y + it * yStep) }
    }

    val isHorizontal: Boolean by lazy { start.y == end.y }
    val isVertical: Boolean by lazy { start.x == end.x }
    val isDiagonal: Boolean by lazy { !(isHorizontal || isVertical) }
}

data class Region(val topLeft: Vector2, val bottomRight: Vector2) {
    operator fun contains(point: Vector2): Boolean = point.x in topLeft.x..bottomRight.x && point.y in topLeft.y..bottomRight.y

    fun contract(amount: Int) = Region(topLeft + Vector2(amount, amount), bottomRight - Vector2(amount, amount))
}

typealias Grid<T> = Map<Vector2, T>
typealias MutableGrid<T> = MutableMap<Vector2, T>

data class GridEntry<V>(override val key: Vector2, override val value: V?) : Map.Entry<Vector2, V?>

fun <T> mutableGridOf(vararg pairs: Pair<Vector2, T>) = mutableMapOf(*pairs)

fun <T> Grid<T>.pointsToThe(
    direction: Vector2,
    source: Vector2,
) = sequence {
    var current = source + direction
    while (current in keys) {
        yield(GridEntry(current, getValue(current)))
        current += direction
    }
}

fun <T> Grid<T>.neighborsOf(point: Vector2): Map<Vector2, T> {
    return point.neighbors().filter { containsKey(it) }.associateWith { get(it)!! }
}

fun <T> Grid<T>.neighborsIncludingDiagonalsOf(point: Vector2): Map<Vector2, T> {
    return point.neighborsIncludingDiagonals().filter { containsKey(it) }.associateWith { get(it)!! }
}

fun <T> Grid<T>.topLeft() = Vector2(keys.minOf { it.x }, keys.minOf { it.y })

fun <T> Grid<T>.bottomRight() = Vector2(keys.maxOf { it.x }, keys.maxOf { it.y })

fun <T> Grid<T>.region() = Region(topLeft(), bottomRight())

fun <T> Grid<T>.width() = keys.maxOf { it.x } - keys.minOf { it.x } + 1

fun <T> Grid<T>.height() = keys.maxOf { it.y } - keys.minOf { it.y } + 1

fun <T> Grid<T>.rows() = IntRange(keys.minOf { it.y }, keys.maxOf { it.y })

fun <T> Grid<T>.cols() = IntRange(keys.minOf { it.x }, keys.maxOf { it.x })

fun <T> Grid<T>.valuesBetween(start: Vector2, end: Vector2) =
    Line(start, end).points.mapNotNull { get(it) }

fun Collection<Vector2>.width() = maxOf { it.x } - minOf { it.x } + 1

fun Collection<Vector2>.height() = maxOf { it.y } - minOf { it.y } + 1

fun Collection<Vector2>.toStringVisualization(): String {
    val minX = minOf { it.x }
    val minY = minOf { it.y }
    val maxX = maxOf { it.x }
    val maxY = maxOf { it.y }

    val grid = Array(maxY - minY + 1) { Array(maxX - minX + 1) { '.' } }

    for (point in this) {
        grid[point.y - minY][point.x - minX] = '#'
    }

    return grid.joinToString("\n") { it.joinToString("") }
}

fun <T> Grid<T>.toStringVisualization(): String {
    val minX = minOf { it.key.x }
    val minY = minOf { it.key.y }
    val maxX = maxOf { it.key.x }
    val maxY = maxOf { it.key.y }

    val grid = Array(maxY - minY + 1) { Array(maxX - minX + 1) { ' ' } }

    for (point in this) {
        grid[point.key.y - minY][point.key.x - minX] = point.value.toString()[0]
    }

    return grid.joinToString("\n") { it.joinToString("") }
}

fun <T> List<String>.toGrid(
    ignore: Char? = null,
    transform: (Char) -> T,
) = mutableMapOf<Vector2, T>().apply {
    forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if (c != ignore) put(Vector2(x, y), transform(c))
        }
    }
}

fun List<String>.toGrid(ignore: Char? = null) = toGrid(ignore) { it }

fun <T> Iterable<Vector2>.toGrid(valueGenerator: (Vector2) -> T) =
    mutableMapOf<Vector2, T>().apply {
        forEachIndexed { _, v ->
            put(v, valueGenerator(v))
        }
    }
