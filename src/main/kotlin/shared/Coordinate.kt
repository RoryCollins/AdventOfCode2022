package shared

data class Coordinate(val x: Int, val y: Int) {
    companion object {
        val Origin = Coordinate(0,0)
    }
    fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)
}