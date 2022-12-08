package day08
import java.io.File

private val input = File("src/main/kotlin/day08/input.txt")
    .readLines()
    .map { row ->
        row.toCharArray().map { Character.getNumericValue(it) }
    }

private val directions = listOf(
    Coordinate(1, 0),
    Coordinate(-1, 0),
    Coordinate(0, 1),
    Coordinate(0, -1)
)

fun main() {
    val grid = Grid(input)
    val coordinates = List(input.size) { y ->
        List(input[y].size) { x -> Coordinate(x,y)}
    }.flatten()

    val treeVisibilities = coordinates.map{treeCoordinate ->
        grid.heightAt(treeCoordinate) to directions.map{grid.getAllInDirection(it, treeCoordinate)}
    }

    val visibleCoordinates = treeVisibilities.filter { (height, sight) -> sight.any{line -> line.all{it < height}} }

    val scenicScores = visibleCoordinates.map{(height, sight) -> sight.map { lineOfSight -> lineOfSight.takeWhileInclusive { it < height } }}

    println("Part One: ${visibleCoordinates.count()}")
    println("Part Two: ${scenicScores.maxOf{it.fold(1){acc, ints -> acc * ints.count()} }}")
}

fun <T> List<T>.takeWhileInclusive(pred: (T) -> Boolean): List<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = pred(it)
        result
    }
}