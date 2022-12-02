package day02

import java.io.File

val games = File("src/main/kotlin/day02/input.txt")
    .readLines()
    .map { it.split(" ") }

fun chooseShape(shape: Shape, requiredResult: String): Shape {
    return when (requiredResult) {
        "X" -> shape.getLoss()
        "Y" -> shape
        else -> shape.getWin()
    }
}

fun main() {
    val partOneInput = games.map { Game(Shape.get(it.first()),Shape.get(it.last())) }
    val partTwoInput = games.map { (player1, requiredResult) ->
        val playerOneShape = Shape.get(player1)
        Game(playerOneShape, chooseShape(playerOneShape, requiredResult)) }

    println("Part One: ${partOneInput.sumOf { it.scoreGame() }}")
    println("Part Two: ${partTwoInput.sumOf { it.scoreGame() }}")
}
