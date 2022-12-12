package shared

class Grid(private val input: List<List<Int>>) {
    fun heightAt(coordinate: Coordinate): Int {
        return input[coordinate.y][coordinate.x]
    }

    fun contains(point: Coordinate): Boolean {
        return point.x < input.first().count()
                && point.x >= 0
                && point.y < input.count()
                && point.y >= 0
    }

}