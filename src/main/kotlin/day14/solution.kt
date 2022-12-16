package day14

import shared.Coordinate
import java.io.File

private val rockCoordinates = File("src/main/kotlin/day14/input.txt")
    .readLines()
    .map { line ->
        line.split(" -> ").map { Coordinate.of(it) }
    }
    .map {
        it.fold(listOf(it[0])) { acc, c -> acc + acc.last().to(c) }.toSet()
    }
    .flatten()

private enum class Type {
    ROCK,
    SAND,
    SPACE
}

private val rockBottom = rockCoordinates.minOf { it.y }

private tailrec fun dropSand(coordinate: Coordinate, state: Map<Coordinate, Type>) : Pair<Boolean, Coordinate> {
    if(coordinate.y == rockBottom)
        return true to coordinate
    val candidates =
        listOf(Coordinate(0, -1), Coordinate(-1, -1), Coordinate(1, -1))
            .map { coordinate.plus(it) }
            .filter { !state.containsKey(it) }
    if (candidates.isEmpty()){
        return false to coordinate
    }
    return dropSand(candidates.first(), state)
}

private tailrec fun dropSand2(coordinate: Coordinate, state: Map<Coordinate, Type>) : Pair<Boolean, Coordinate> {
    val candidates =
        listOf(Coordinate(0, -1), Coordinate(-1, -1), Coordinate(1, -1))
            .map { coordinate.plus(it) }
            .filter { !state.containsKey(it) }
    if(coordinate.y == 0 && candidates.isEmpty()){
        return true to coordinate
    }
    if(coordinate.y == rockBottom-1){
        return false to coordinate
    }
    if (candidates.isEmpty()){
        return false to coordinate
    }
    return dropSand2(candidates.first(), state)
}


fun main() {
    var state = rockCoordinates.associateWith { Type.ROCK }
    var exitsToTheAbyss = false
    var count = 0

    while (!exitsToTheAbyss) {
        val result = dropSand(Coordinate(500, 0), state)
        exitsToTheAbyss = result.first
        state = state + (result.second to Type.SAND)
        count++
    }
    println("Part One: ${count - 1}")

    state = rockCoordinates.associateWith { Type.ROCK }
    var reachedTheTop = false
    count = 0

    while (!reachedTheTop) {
        val result = dropSand2(Coordinate(500, 0), state)
        reachedTheTop = result.first
        state = state + (result.second to Type.SAND)
        count++
    }
    println("Part Two: $count")
}

private fun printGrid(map: Map<Coordinate, Type>) {
    val topLeft = Coordinate(map.keys.minOf { it.x }, 0)
    val bottomRight = Coordinate(map.keys.maxOf { it.x } , rockBottom-2)

    for (yPos in topLeft.y.downTo(bottomRight.y)) {
        for (xPos in topLeft.x..bottomRight.x) {
            when(map.getOrDefault(Coordinate(xPos, yPos), Type.SPACE)){
                Type.ROCK -> print("#")
                Type.SAND -> print("O")
                Type.SPACE -> print(" ")
            }
        }
        print("\n")
    }
}

