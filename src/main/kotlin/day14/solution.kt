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

private tailrec fun findBottom(coordinate: Coordinate, state: Map<Coordinate, Type>) : Pair<Boolean, Coordinate> {
    if(coordinate.y <= rockBottom)
        return true to coordinate
    val candidates =
        listOf(Coordinate(0, -1), Coordinate(-1, -1), Coordinate(1, -1))
            .map { coordinate.plus(it) }
            .filter { !state.containsKey(it) }
    if (candidates.isEmpty()){
        return false to coordinate
    }
    return findBottom(candidates.first(), state)
}


fun main() {
    var state = rockCoordinates.associateWith { Type.ROCK }
    var exitsToTheAbyss = false
    var count = 0;

    while (!exitsToTheAbyss) {
        val foo = findBottom(Coordinate(500, 0), state)
        exitsToTheAbyss = foo.first
        state = state + (foo.second to Type.SAND)
        count++
    }
    printGrid(state)

    println("Part One: ${count - 1}")
    println("Part Two: ")
}

private fun printGrid(sand: Map<Coordinate, Type>) {
    val topLeft = Coordinate(rockCoordinates.minOf { it.x }, 9)
    val bottomRight = Coordinate(rockCoordinates.maxOf { it.x } , rockBottom)

    for (yPos in topLeft.y.downTo(bottomRight.y)) {
        for (xPos in topLeft.x..bottomRight.x) {
            when(sand.getOrDefault(Coordinate(xPos, yPos), Type.SPACE)){
                Type.ROCK -> print("#")
                Type.SAND -> print("O")
                Type.SPACE -> print(" ")
            }
        }
        print("\n")
    }
}

