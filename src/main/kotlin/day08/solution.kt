package day08
import shared.Coordinate
import shared.Grid
import java.io.File

private val input = File("src/main/kotlin/day08/input.txt")
    .readLines()
    .map { row ->
        row.toCharArray().map { Character.getNumericValue(it) }
    }

fun main() {
    val grid = Grid(input)
    val coordinates = List(input.size) { y ->
        List(input[y].size) { x -> Coordinate(x,y) }
    }.flatten()

    val treeVisibilities = coordinates.map{treeCoordinate ->
        grid.heightAt(treeCoordinate) to Coordinate.Directions.map{grid.getAllInDirection(it, treeCoordinate)}
    }

    val visibleCoordinates = treeVisibilities.filter { (height, sight) -> sight.any{line -> line.all{it < height}} }

    val scenicScores = visibleCoordinates.map{(height, sight) -> sight.map { lineOfSight -> lineOfSight.takeWhileInclusive { it < height } }}

    println("Part One: ${visibleCoordinates.count()}")
    println("Part Two: ${scenicScores.maxOf{it.fold(1){acc, ints -> acc * ints.count()} }}")
}

private fun <T> List<T>.takeWhileInclusive(pred: (T) -> Boolean): List<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = pred(it)
        result
    }
}

private fun Grid.getAllInDirection(direction: Coordinate, coordinate: Coordinate): List<Int> {
    val newCoordinate = coordinate.plus(direction)
    if (this.contains(newCoordinate)) {
        return listOf(input[newCoordinate.y][newCoordinate.x]) + getAllInDirection(direction, newCoordinate)
    }
    return emptyList()
}