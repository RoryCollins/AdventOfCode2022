package day12

import shared.Coordinate
import shared.Grid
import java.io.File
import java.util.*

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
    println("Part One: ${grid.dijkstraMinimum()}")
    println("Part Two: ")
}

private class HeightMap(private val rows : List<List<Int>>) : Grid(rows){
    fun dijkstraMinimum(): Int {
        val priorityQueue = PriorityQueue(CoordinateScoreComparator())
        val visited = mutableSetOf<Coordinate>()
        val cumulativeEffortMap = mutableMapOf<Coordinate, Int>()

        cumulativeEffortMap[start] = 0
        priorityQueue.add(CoordinateScore(start, 0))

        while(priorityQueue.isNotEmpty()){
            val (coordinate, effort) = priorityQueue.remove()
            visited.add(coordinate)

            val traversableCoordinates = getNeighbours(coordinate)
                .filter { rows[it.y][it.x] <= rows[coordinate.y][coordinate.x] + 1 }
                .filter{!visited.contains(it)}

            traversableCoordinates.map{
                val newEffortLevel = effort + 1
                if(newEffortLevel < cumulativeEffortMap.getOrDefault(it, Int.MAX_VALUE)){
                    cumulativeEffortMap[it] = newEffortLevel
                    priorityQueue.add(CoordinateScore(it, newEffortLevel))
                }
            }
        }
        return cumulativeEffortMap.getOrDefault(end, Int.MAX_VALUE)
    }

}


private class CoordinateScoreComparator : Comparator<CoordinateScore>{
    override fun compare(first: CoordinateScore, second: CoordinateScore): Int {
        return first.score.compareTo(second.score)
    }
}

data class CoordinateScore(val coordinate: Coordinate, val score: Int)