package day15

import shared.Coordinate
import java.io.File
import kotlin.math.abs

private val input = File("src/main/kotlin/day15/input.txt")
    .readLines()
    .map {
        """Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""".toRegex()
            .find(it)!!.groups.drop(1)
            .map {
                it!!.value.toInt()
            }
    }
    .map { Coordinate(it[0], it[1]) to Coordinate(it[2], it[3]) }

private fun getExclusionsForLine(sensorRanges: List<SensorRange>, yAxis: Int): List<Coordinate> {
    val sensorsInRange = sensorRanges.filter { it.overlapsY(yAxis) }
    return sensorsInRange.flatMap { it.getXCoordinatesAtYPosition(yAxis) }.toSet().filter{it.y == yAxis }
}

private fun findDistressSignal(sensorRanges : List<SensorRange>, maxXY : Int) : Coordinate{
    var yPos = 0
    var xPos = 0
    while(true){
        val sensorsInRange = sensorRanges.filter { it.overlapsY(yPos) }
        while(xPos < maxXY){
            val sensor = sensorsInRange.firstOrNull { it.contains(Coordinate(xPos, yPos)) }
                ?: return Coordinate(xPos, yPos)
            xPos = sensor.getFurthestXCoordinateOutsideRange(yPos)
        }
        xPos=0
        yPos++
    }
}

private fun findTuningFrequency(sensorRanges : List<SensorRange>, maxXY : Int) : Long{
    val distressSignalSource = findDistressSignal(sensorRanges, maxXY)
    return distressSignalSource.x.toLong() * 4_000_000 + distressSignalSource.y.toLong()
}

fun main() {
    val scanners = input.map { it.first }.toSet()
    val beacons = input.map { it.second }.toSet()
    val sensorRanges = input
        .map { it.first to it.first.manhattanDistanceTo(it.second) }
        .map { SensorRange(it.first, it.second) }

    println("Part One: ${getExclusionsForLine(sensorRanges, 2_000_000).count { !(scanners + beacons).contains(it) }}")
    println("Part Two: ${findTuningFrequency(sensorRanges, 4_000_000)}")
}



