package shared

import kotlin.math.abs

data class Coordinate(val x: Int, val y: Int) {
    companion object {
        val Origin = Coordinate(0, 0)

        val Directions = listOf(
            Coordinate(1, 0),
            Coordinate(-1, 0),
            Coordinate(0, 1),
            Coordinate(0, -1)
        )

        fun of(str: String) : Coordinate {
            val groups = """(\d+),(\d+)""".toRegex().find(str)!!.groups
            return Coordinate(groups[1]!!.value.toInt(), groups[2]!!.value.toInt()*-1)
        }
    }

    fun to(other: Coordinate) : Set<Coordinate>{
        return x.to(other.x).flatMap{newX -> y.to(other.y).map{newY -> Coordinate(newX, newY)}}.toSet()
    }

    fun manhattanDistanceTo(other : Coordinate) : Int{
        return abs(x - other.x) + abs(y-other.y)
    }

    private fun Int.to(that: Int) : List<Int>{
        val range = if (this < that) this..that
        else this.downTo(that)
        return range.toList()
    }

    fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)
}