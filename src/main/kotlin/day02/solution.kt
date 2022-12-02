package day02

import java.io.File

val games = File("src/main/kotlin/day02/input.txt")
    .readLines()
    .map { it.split(" ") }
    .map { it.first() to it.last() }

fun chooseShape(shape: Shape, requiredResult: String): Shape {
    return when (requiredResult) {
        "X" -> shape.getLoss()
        "Y" -> shape
        else -> shape.getWin()
    }
}

fun main() {
    val partOneInput = games.map { (player1, player2) ->
        Game(Shape.get(player1), Shape.get(player2))
    }

    val partTwoInput = games.map { (player1, requiredResult) ->
        val playerOneShape = Shape.get(player1)
        Game(playerOneShape, chooseShape(playerOneShape, requiredResult))
    }

    println("Part One: ${partOneInput.sumOf { it.scoreGame() }}")
    println("Part Two: ${partTwoInput.sumOf { it.scoreGame() }}")
}
