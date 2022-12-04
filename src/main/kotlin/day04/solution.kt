package day04

import java.io.File

val ranges = File("src/main/kotlin/day04/input.txt")
    .readLines()
    .map{ pair ->
        pair.split(",")
            .map{ convertToRange(it) }
    }

fun hasFullyContainedAssignment(range1 : Set<Int>, range2: Set<Int>) : Boolean{
    return range1.all{range2.contains(it)} || range2.all{range1.contains(it)}
}

fun convertToRange(assignment: String) : Set<Int> {
    val assignments = assignment.split("-")
        .map{it.toInt()}
    return (assignments[0]..assignments[1]).toSet()
}

fun main(){
    val partOne = ranges.map{
        hasFullyContainedAssignment(it.first(), it.last())
    }

    val partTwo = ranges.map{
        it.first().intersect(it.last()).any()
    }
    println("Part One: ${partOne.count{ it }}")
    println("Part Two: ${partTwo.count{ it }}")
}