package day08

import shared.Coordinate

class Grid(private val input: List<List<Int>>) {
    fun heightAt(coordinate: Coordinate): Int {
        return input[coordinate.y][coordinate.x]
    }

    fun getAllInDirection(direction: Coordinate, coordinate: Coordinate): List<Int> {
        val newCoordinate = coordinate.plus(direction)
        if (this.contains(newCoordinate)) {
            return listOf(input[newCoordinate.y][newCoordinate.x]) + getAllInDirection(direction, newCoordinate)
        }
        return emptyList()
    }

    private fun contains(point: Coordinate): Boolean {
        return point.x < input.first().count()
                && point.x >= 0
                && point.y < input.count()
                && point.y >= 0
    }

}