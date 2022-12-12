package shared

data class Coordinate(val x: Int, val y: Int) {
    companion object {
        val Origin = Coordinate(0, 0)

        val Directions = listOf(
            Coordinate(1, 0),
            Coordinate(-1, 0),
            Coordinate(0, 1),
            Coordinate(0, -1)
        )
    }

    fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)
}