package day08

data class Coordinate(val x: Int, val y: Int) {
    fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)
}