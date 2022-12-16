package day15

import shared.Coordinate
import kotlin.math.abs

class SensorRange(private val sensor: Coordinate, private val range: Int) {
    private val coordinateRange = listOf(
        Coordinate(-range, 0),
        Coordinate(range, 0),
        Coordinate(0, -range),
        Coordinate(0, range)
    ).map {
        sensor.plus(it)
    }

    fun overlapsY(y: Int): Boolean {
        return ((sensor.y - range)..(sensor.y + range)).contains(y)
    }

    fun contains(other: Coordinate): Boolean {
        val xRange = range - abs(sensor.y - other.y)
        return other.x >= sensor.x - xRange && other.x <= sensor.x + xRange
    }

    fun getFurthestXCoordinateOutsideRange(y: Int): Int {
        val xRange = range - abs(sensor.y - y)
        return sensor.x + xRange + 1
    }

    fun getXCoordinatesAtYPosition(y: Int): Set<Coordinate> {
        val xRange = range - abs(sensor.y - y)
        return Coordinate(sensor.x - xRange, y).to(Coordinate(sensor.x + xRange, y))
    }
}