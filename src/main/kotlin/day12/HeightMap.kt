package day12

import shared.Coordinate
import shared.Grid
import java.util.*

internal class HeightMap(private val rows : List<List<Int>>) : Grid(rows){
    fun dijkstraMinimum(start : Coordinate): Int {
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

    fun possibleStartingCoordinates() : List<Coordinate>{
        val coordinates = mutableListOf<Coordinate>()
        for(y in rows.indices){
            for(x in rows[y].indices){
                if(rows[y][x] == 0) coordinates.add(Coordinate(x, y))
            }
        }
        return coordinates
    }
}


private class CoordinateScoreComparator : Comparator<CoordinateScore>{
    override fun compare(first: CoordinateScore, second: CoordinateScore): Int {
        return first.score.compareTo(second.score)
    }
}

private data class CoordinateScore(val coordinate: Coordinate, val score: Int)