package day12

import shared.Coordinate
import java.io.File

private val range = 'a'..'z'

private val input = File("src/main/kotlin/day12/input.txt")
    .readLines()

val start = input.mapIndexed {y, row -> Coordinate(row.indexOf('S'), y) }.single{it.x > -1}
val end = input.mapIndexed {y, row -> Coordinate(row.indexOf('E'), y) }.single{it.x > -1}

private val grid = HeightMap(input.map { row ->
    row.map {
        when (it) {
            'S' -> range.indexOf('a')
            'E' -> range.indexOf('z')
            else -> range.indexOf(it)
        }
    }
})

fun main(){
    println("Part One: ${grid.dijkstraMinimum(start)}")
    println("Part Two: ${grid.possibleStartingCoordinates().minOf { grid.dijkstraMinimum(it) }}")
}
