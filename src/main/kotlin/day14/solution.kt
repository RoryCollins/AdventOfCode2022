package day14

import shared.Coordinate
import java.io.File

private val rockCoordinates = File("src/main/kotlin/day14/input.txt")
    .readLines()
    .map { line ->
        line.split(" -> ").map {
            val groups = """(\d+),(\d+)""".toRegex().find(it)!!.groups
            Coordinate(groups[1]!!.value.toInt(), groups[2]!!.value.toInt()*-1)
        }
    }
    .map {
        it.fold(setOf(it[0])) { acc, c -> acc + acc.last().to(c) }
    }
    .flatten()

//private val rockCoordinates = Coordinate(490,-20).to(Coordinate(505, -20))

private val rockBottom = rockCoordinates.minOf { it.y }

fun dropSand(coordinate: Coordinate, state: List<Coordinate>): Pair<Boolean, List<Coordinate>> {
    val candidates =
        listOf(Coordinate(0, -1), Coordinate(-1, -1), Coordinate(1, -1))
            .map { coordinate.plus(it) }
            .filter { !(rockCoordinates + state).contains(it) }
    if (candidates.isEmpty())
        return false to state + coordinate
    if (candidates.first().y < rockBottom)
        return true to state
    return dropSand(candidates.first(), state)
}


fun main() {

    Coordinate(0,0).to(Coordinate(10,0)).map{println(it)}

    var state = emptyList<Coordinate>()
    var exitsToTheAbyss = false
    var count = 0;

    while(!exitsToTheAbyss){
        val foo = dropSand(Coordinate(500, 0), state)
        exitsToTheAbyss = foo.first
        state = foo.second
        count++
    }

    println(count)
    printGrid(state)

    println("Part One: ${count-1}")
    println("Part Two: ")
}

private fun printGrid(sand: List<Coordinate>) {
    val topLeft = Coordinate(rockCoordinates.minOf { it.x }-5, 0)
    val bottomRight = Coordinate(rockCoordinates.maxOf { it.x }, rockBottom)

    for (yPos in topLeft.y.downTo(bottomRight.y)) {
        for (xPos in topLeft.x..bottomRight.x) {
            if (xPos == 500 && yPos == 0) print("X")
            else if (rockCoordinates.contains(Coordinate(xPos, yPos))) print("#")
            else if (sand.contains(Coordinate(xPos, yPos))) print("O")
            else print(".")
        }
        print("\n")
    }
}

