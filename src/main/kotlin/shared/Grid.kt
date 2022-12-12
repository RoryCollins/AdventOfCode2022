package shared

open class Grid(private val rows: List<List<Int>>) {
    fun heightAt(coordinate: Coordinate): Int {
        return rows[coordinate.y][coordinate.x]
    }

    fun contains(point: Coordinate): Boolean {
        return point.x < rows.first().size
                && point.x >= 0
                && point.y < rows.size
                && point.y >= 0
    }

    protected open fun getNeighbours(coordinate: Coordinate): List<Coordinate>{
        return getAdjacentNeighbours(coordinate)
    }

    private fun getAdjacentNeighbours(coordinate: Coordinate): List<Coordinate>{
        return listOf(
            Coordinate(0,1),
            Coordinate(0,-1),
            Coordinate(1,0),
            Coordinate(-1,0)).map{coordinate.plus(it)}
            .filter{contains(it)}
    }
}