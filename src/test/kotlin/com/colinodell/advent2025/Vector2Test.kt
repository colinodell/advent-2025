package com.colinodell.advent2025

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.sequences.toList
import kotlin.text.toList
import kotlin.text.trimIndent
import kotlin.to

@DisplayName("Vector2")
class Vector2Test {
    @Test
    fun `Constructor and Properties`() {
        val vector = Vector2(1, 2)
        assertThat(vector.x).isEqualTo(1)
        assertThat(vector.y).isEqualTo(2)
    }

    @Test
    fun `Addition`() {
        val vector = Vector2(1, 2) + Vector2(3, 4)
        assertThat(vector.x).isEqualTo(4)
        assertThat(vector.y).isEqualTo(6)
    }

    @Test
    fun `Subtraction`() {
        val vector = Vector2(4, 6) - Vector2(3, 4)
        assertThat(vector.x).isEqualTo(1)
        assertThat(vector.y).isEqualTo(2)
    }

    @Test
    fun `Multiplication`() {
        val vector = Vector2(1, -2) * 3
        assertThat(vector.x).isEqualTo(3)
        assertThat(vector.y).isEqualTo(-6)
    }

    @Test
    fun `Division`() {
        val vector = Vector2(6, -8) / 3
        assertThat(vector.x).isEqualTo(2)
        assertThat(vector.y).isEqualTo(-2)
    }

    @Test
    fun `Modulus`() {
        val vector = Vector2(4, -8) % 3
        assertThat(vector.x).isEqualTo(1)
        assertThat(vector.y).isEqualTo(-2)
    }

    @Test
    fun `Negative Safe Modulus`() {
        val vector = Vector2(-1, 5).negativeSafeModulo(3)
        assertThat(vector.x).isEqualTo(2)
        assertThat(vector.y).isEqualTo(2)
    }

    @Test
    fun `String Representation`() {
        val vector = Vector2(1, 2)
        assertThat(vector.toString()).isEqualTo("(1, 2)")
    }

    @Test
    fun `Normalization to Unit Vectors`() {
        val vector = Vector2(35, -7)
        assertThat(vector.normalize()).isEqualTo(Vector2(1, -1))
    }

    @Test
    fun `Is Touching`() {
        // Same position
        assertThat(Vector2(0, 0).isTouching(Vector2(0, 0))).isTrue

        // Directly adjacent
        assertThat(Vector2(0, 0).isTouching(Vector2(-1, 0))).isTrue
        assertThat(Vector2(0, 0).isTouching(Vector2(1, 0))).isTrue
        assertThat(Vector2(0, 0).isTouching(Vector2(0, -1))).isTrue
        assertThat(Vector2(0, 0).isTouching(Vector2(0, 1))).isTrue

        // Diagonally adjacent
        assertThat(Vector2(0, 0).isTouching(Vector2(-1, -1))).isTrue
        assertThat(Vector2(0, 0).isTouching(Vector2(1, -1))).isTrue
        assertThat(Vector2(0, 0).isTouching(Vector2(-1, 1))).isTrue
        assertThat(Vector2(0, 0).isTouching(Vector2(1, 1))).isTrue

        // Not touching
        assertThat(Vector2(0, 0).isTouching(Vector2(2, 0))).isFalse
        assertThat(Vector2(0, 0).isTouching(Vector2(0, 2))).isFalse
        assertThat(Vector2(0, 0).isTouching(Vector2(2, 2))).isFalse
    }

    @Test
    fun `Manhattan Distance`() {
        assertThat(Vector2(0, 0).manhattanDistanceTo(Vector2(0, 0))).isEqualTo(0)
        assertThat(Vector2(0, 0).manhattanDistanceTo(Vector2(3, 2))).isEqualTo(5)
        assertThat(Vector2(-1, -1).manhattanDistanceTo(Vector2(1, 1))).isEqualTo(4)
    }

    @Test
    fun `With X`() {
        val vector = Vector2(1, 2)
        assertThat(vector.withX(3)).isEqualTo(Vector2(3, 2))
    }

    @Test
    fun `With Y`() {
        val vector = Vector2(1, 2)
        assertThat(vector.withY(3)).isEqualTo(Vector2(1, 3))
    }

    @Test
    fun `Neighbors`() {
        assertThat(Vector2(0, 0).neighbors()).containsExactlyInAnyOrder(
            Vector2(-1, 0),
            Vector2(1, 0),
            Vector2(0, -1),
            Vector2(0, 1),
        )
    }

    @Test
    fun `Neighbors Including Diagonals`() {
        assertThat(Vector2(0, 0).neighborsIncludingDiagonals()).containsExactlyInAnyOrder(
            Vector2(-1, -1),
            Vector2(-1, 0),
            Vector2(-1, 1),
            Vector2(0, -1),
            Vector2(0, 1),
            Vector2(1, -1),
            Vector2(1, 0),
            Vector2(1, 1),
        )
    }

    @Test
    fun `Direction To`() {
        assertThat(Vector2(0, 0).directionTo(Vector2(0, -1))).isEqualTo(Direction.NORTH)
        assertThat(Vector2(0, 0).directionTo(Vector2(0, 1))).isEqualTo(Direction.SOUTH)
        assertThat(Vector2(0, 0).directionTo(Vector2(-1, 0))).isEqualTo(Direction.WEST)
        assertThat(Vector2(0, 0).directionTo(Vector2(1, 0))).isEqualTo(Direction.EAST)
        assertThat(Vector2(0, 0).directionTo(Vector2(1, 1))).isNull()
    }

    @Test
    fun `Follow`() {
        assertThat(Vector2(0, 0).follow(listOf()).toList()).isEqualTo(
            listOf(
                Vector2(0, 0),
            ),
        )
        assertThat(Vector2(0, 0).follow(listOf(Direction.SOUTH)).toList()).isEqualTo(
            listOf(
                Vector2(0, 0),
                Vector2(0, 1),
            ),
        )
        assertThat(Vector2(-3, 7).follow(listOf(Direction.WEST, Direction.SOUTH)).toList()).isEqualTo(
            listOf(
                Vector2(-3, 7),
                Vector2(-4, 7),
                Vector2(-4, 8),
            ),
        )
    }

    @Test
    fun `As Directions`() {
        assertThat(Vector2(0, 0).asDirections().toList()).isEqualTo(listOf<Direction>())
        assertThat(Vector2(0, 1).asDirections().toList()).isEqualTo(listOf(Direction.SOUTH))
        assertThat(Vector2(-3, 2).asDirections().toList()).isEqualTo(
            listOf(
                Direction.WEST,
                Direction.WEST,
                Direction.WEST,
                Direction.SOUTH,
                Direction.SOUTH,
            ),
        )
    }
}

@Nested
@DisplayName("Direction")
class DirectionTest {
    @Test
    fun `Turn`() {
        assertThat(Direction.NORTH.turn(Direction.Rotation.RIGHT)).isEqualTo(Direction.EAST)
        assertThat(Direction.NORTH.turn(Direction.Rotation.LEFT)).isEqualTo(Direction.WEST)
        assertThat(Direction.EAST.turn(Direction.Rotation.RIGHT)).isEqualTo(Direction.SOUTH)
        assertThat(Direction.EAST.turn(Direction.Rotation.LEFT)).isEqualTo(Direction.NORTH)
        assertThat(Direction.SOUTH.turn(Direction.Rotation.RIGHT)).isEqualTo(Direction.WEST)
        assertThat(Direction.SOUTH.turn(Direction.Rotation.LEFT)).isEqualTo(Direction.EAST)
        assertThat(Direction.WEST.turn(Direction.Rotation.RIGHT)).isEqualTo(Direction.NORTH)
        assertThat(Direction.WEST.turn(Direction.Rotation.LEFT)).isEqualTo(Direction.SOUTH)
    }

    @Test
    fun `Turn Left`() {
        assertThat(Direction.NORTH.turnLeft()).isEqualTo(Direction.WEST)
        assertThat(Direction.WEST.turnLeft()).isEqualTo(Direction.SOUTH)
        assertThat(Direction.SOUTH.turnLeft()).isEqualTo(Direction.EAST)
        assertThat(Direction.EAST.turnLeft()).isEqualTo(Direction.NORTH)
    }

    @Test
    fun `Turn Right`() {
        assertThat(Direction.NORTH.turnRight()).isEqualTo(Direction.EAST)
        assertThat(Direction.EAST.turnRight()).isEqualTo(Direction.SOUTH)
        assertThat(Direction.SOUTH.turnRight()).isEqualTo(Direction.WEST)
        assertThat(Direction.WEST.turnRight()).isEqualTo(Direction.NORTH)
    }

    @Test
    fun `Vector`() {
        assertThat(Direction.NORTH.vector()).isEqualTo(Vector2(0, -1))
        assertThat(Direction.EAST.vector()).isEqualTo(Vector2(1, 0))
        assertThat(Direction.SOUTH.vector()).isEqualTo(Vector2(0, 1))
        assertThat(Direction.WEST.vector()).isEqualTo(Vector2(-1, 0))
    }

    @Test
    fun `Opposite`() {
        assertThat(Direction.NORTH.opposite()).isEqualTo(Direction.SOUTH)
        assertThat(Direction.EAST.opposite()).isEqualTo(Direction.WEST)
        assertThat(Direction.SOUTH.opposite()).isEqualTo(Direction.NORTH)
        assertThat(Direction.WEST.opposite()).isEqualTo(Direction.EAST)
    }

    @Test
    fun `From`() {
        assertThat(Direction.from('^')).isEqualTo(Direction.NORTH)
        assertThat(Direction.from('v')).isEqualTo(Direction.SOUTH)
        assertThat(Direction.from('<')).isEqualTo(Direction.WEST)
        assertThat(Direction.from('>')).isEqualTo(Direction.EAST)
    }
}

@Nested
@DisplayName("Line")
class LineTest {
    @Test
    fun `Example 1`() {
        val line = Line(Vector2(1, 1), Vector2(1, 3))
        assertThat(line.points).containsExactly(
            Vector2(1, 1),
            Vector2(1, 2),
            Vector2(1, 3),
        )
        assertThat(line.isHorizontal).isFalse()
        assertThat(line.isVertical).isTrue()
        assertThat(line.isDiagonal).isFalse()
    }

    @Test
    fun `Example 2`() {
        val line = Line(Vector2(9, 7), Vector2(7, 7))
        assertThat(line.points).containsExactly(
            Vector2(9, 7),
            Vector2(8, 7),
            Vector2(7, 7),
        )
        assertThat(line.isHorizontal).isTrue()
        assertThat(line.isVertical).isFalse()
        assertThat(line.isDiagonal).isFalse()
    }

    @Test
    fun `Example 3`() {
        val line = Line(Vector2(1, 1), Vector2(3, 3))
        assertThat(line.points).containsExactly(
            Vector2(1, 1),
            Vector2(2, 2),
            Vector2(3, 3),
        )
        assertThat(line.isHorizontal).isFalse()
        assertThat(line.isVertical).isFalse()
        assertThat(line.isDiagonal).isTrue()
    }

    @Test
    fun `Example 4`() {
        val line = Line(Vector2(9, 7), Vector2(7, 9))
        assertThat(line.points).containsExactly(
            Vector2(9, 7),
            Vector2(8, 8),
            Vector2(7, 9),
        )

        assertThat(line.isHorizontal).isFalse()
        assertThat(line.isVertical).isFalse()
        assertThat(line.isDiagonal).isTrue()
    }
}

@Nested
@DisplayName("Region")
class RegionTest {
    @Test
    fun `Contains`() {
        val region = Region(Vector2(1, 1), Vector2(3, 3))
        assertThat(region.contains(Vector2(1, 1))).isTrue()
        assertThat(region.contains(Vector2(2, 2))).isTrue()
        assertThat(region.contains(Vector2(3, 3))).isTrue()
        assertThat(region.contains(Vector2(0, 0))).isFalse()
        assertThat(region.contains(Vector2(4, 4))).isFalse()
    }

    @Test
    fun `Contract`() {
        val region = Region(Vector2(1, 1), Vector2(5, 4))
        assertThat(region.contract(1)).isEqualTo(Region(Vector2(2, 2), Vector2(4, 3)))
    }
}

@Nested
@DisplayName("Grid")
class GridTest {
    @Test
    fun `pointsToThe()`() {
        val grid: Grid<Char> =
            mapOf(
                Pair(Vector2(0, 0), 'a'),
                Pair(Vector2(2, 0), 'b'),
                Pair(Vector2(1, 1), 'c'),
                Pair(Vector2(0, 2), 'd'),
                Pair(Vector2(2, 2), 'e'),
            )

        assertThat(grid.pointsToThe(Vector2(1, 1), Vector2(0, 0)).toList()).containsExactly(
            GridEntry(Vector2(1, 1), 'c'),
            GridEntry(Vector2(2, 2), 'e'),
        )
    }

    @Test
    fun `neighborsOf`() {
        val grid: Grid<Char> =
            mapOf(
                Pair(Vector2(0, 0), 'a'),
                Pair(Vector2(2, 0), 'b'),
                Pair(Vector2(1, 1), 'c'),
                Pair(Vector2(0, 2), 'd'),
                Pair(Vector2(2, 2), 'e'),
            )

        assertThat(grid.neighborsOf(Vector2(2, 1))).containsExactlyInAnyOrderEntriesOf(
            mapOf(
                Pair(Vector2(1, 1), 'c'),
                Vector2(2, 0) to 'b',
                Vector2(2, 2) to 'e',
            ),
        )
    }

    @Test
    fun `neighborsIncludingDiagonalsOf`() {
        val grid: Grid<Char> =
            mapOf(
                Pair(Vector2(0, 0), 'a'),
                Pair(Vector2(2, 0), 'b'),
                Pair(Vector2(1, 1), 'c'),
                Pair(Vector2(0, 2), 'd'),
                Pair(Vector2(2, 2), 'e'),
            )

        assertThat(grid.neighborsIncludingDiagonalsOf(Vector2(1, 1))).containsExactlyInAnyOrderEntriesOf(
            mapOf(
                Pair(Vector2(0, 0), 'a'),
                Pair(Vector2(2, 0), 'b'),
                Pair(Vector2(0, 2), 'd'),
                Pair(Vector2(2, 2), 'e'),
            ),
        )
    }

    @Test
    fun `toStringVisualization()`() {
        val grid: Grid<Char> =
            mapOf(
                Pair(Vector2(0, 0), 'a'),
                Pair(Vector2(2, 0), 'b'),
                Pair(Vector2(1, 1), 'c'),
                Pair(Vector2(0, 2), 'd'),
                Pair(Vector2(2, 2), 'e'),
            )

        assertThat(grid.toStringVisualization()).isEqualTo("a b\n c \nd e")
    }

    @Test
    fun `valuesBetween()`() {
        val grid: Grid<Char> =
            mapOf(
                Pair(Vector2(0, 0), 'a'),
                Pair(Vector2(2, 0), 'b'),
                Pair(Vector2(1, 1), 'c'),
                Pair(Vector2(0, 2), 'd'),
                Pair(Vector2(2, 2), 'e'),
            )

        assertThat(grid.valuesBetween(Vector2(0, 0), Vector2(2, 2))).isEqualTo("ace".toList())
        assertThat(grid.valuesBetween(Vector2(0, 0), Vector2(2, 0))).isEqualTo("ab".toList())
    }
}

@Nested
@DisplayName("Vector2 Collection")
class CollectionTest {
    private val collection: Set<Vector2> =
        setOf(
            Vector2(0, 0),
            Vector2(2, 0),
            Vector2(1, 1),
            Vector2(0, 2),
            Vector2(2, 2),
        )

    @Test
    fun `width()`() {
        assertThat(collection.width()).isEqualTo(3)
    }

    @Test
    fun `height()`() {
        assertThat(collection.height()).isEqualTo(3)
    }

    @Test
    fun `neighborsOf`() {
        val points = listOf(
            Vector2(0, 0),
            Vector2(2, 0),
            Vector2(1, 1),
            Vector2(0, 2),
            Vector2(2, 2),
        )

        assertThat(points.neighborsOf(Vector2(2, 1))).containsAll(
            listOf(
                Vector2(1, 1),
                Vector2(2, 0),
                Vector2(2, 2),
            ),
        )
    }

    @Test
    fun `neighborsIncludingDiagonalsOf`() {
        val points = listOf(
            Vector2(0, 0),
            Vector2(2, 0),
            Vector2(1, 1),
            Vector2(0, 2),
            Vector2(2, 2),
        )

        assertThat(points.neighborsIncludingDiagonalsOf(Vector2(1, 1))).containsAll(
            listOf(
                Vector2(0, 0),
                Vector2(2, 0),
                Vector2(0, 2),
                Vector2(2, 2),
            ),
        )
    }

    @Test
    fun `toStringVisualization()`() {
        assertThat(collection.toStringVisualization()).isEqualTo(
            """
            #.#
            .#.
            #.#
            """.trimIndent(),
        )
    }
}
