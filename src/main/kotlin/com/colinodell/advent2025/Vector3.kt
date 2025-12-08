package com.colinodell.advent2025

data class Vector3(val x: Int, val y: Int, val z: Int) {
    fun distanceSquaredTo(other: Vector3): Long {
        val dx = (x - other.x).toLong()
        val dy = (y - other.y).toLong()
        val dz = (z - other.z).toLong()
        return dx * dx + dy * dy + dz * dz
    }
}
