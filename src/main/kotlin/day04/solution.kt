package day04

import java.io.File

val ranges = File("src/main/kotlin/day04/input.txt")
    .readLines()
    .map{ pair ->
        pair.split(",")
            .map{ convertToRange(it) }
    }
    .map{it.first() to it.last()}

fun convertToRange(assignment: String) : Set<Int> {
    val assignments = assignment.split("-")
        .map{it.toInt()}
    return (assignments[0]..assignments[1]).toSet()
}
fun hasFullyContainedAssignment(ranges : Pair<Set<Int>, Set<Int>>) : Boolean{
    val (range1, range2) = ranges
    return range1.all{range2.contains(it)} || range2.all{range1.contains(it)}
}
fun hasPartiallyOverlappingAssignment(ranges : Pair<Set<Int>, Set<Int>>) : Boolean{
    return ranges.first.intersect(ranges.second).any()
}

fun main(){
    println("Part One: ${ranges.count{ hasFullyContainedAssignment(it) }}")
    println("Part Two: ${ranges.count{ hasPartiallyOverlappingAssignment(it) }}")
}