package day12

import shared.Coordinate
import shared.Grid
import java.io.File

private val range = 'a'..'z'

private val input = File("src/main/kotlin/day12/input.txt")
    .readLines()

val start = input.mapIndexed {y, row -> Coordinate(row.indexOf('S'), y) }.single{it.x > -1}
val end = input.mapIndexed {y, row -> Coordinate(row.indexOf('E'), y) }.single{it.x > -1}

private val grid = Grid(input.map { row ->
    row.map {
        when (it) {
            'S' -> range.indexOf('a')
            'E' -> range.indexOf('z')
            else -> range.indexOf(it)
        }
    }
})

private val effortGrid = Grid(input.map{

})

private fun solve(current : Coordinate, previous : Set<Coordinate>) : Int{
    val neighbours = Coordinate.Directions.map{it.plus(current)}.filter { grid.contains(it) }
    val traversableNeighbours = neighbours
        .filter { !previous.contains(it) }
        .filter { grid.heightAt(it) <= grid.heightAt(current)+1 }
    if(traversableNeighbours.isEmpty()) return Int.MAX_VALUE
    if(traversableNeighbours.contains(end)) return previous.size + 1
    return traversableNeighbours.minOf { solve(it, previous + current)}
}


fun main(){
    println("Part One: ${solve(start, emptySet())}")
    println("Part Two: ")
}