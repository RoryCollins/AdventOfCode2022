package day04

import java.io.File

val ranges = File("src/main/kotlin/day04/input.txt")
    .readLines()
    .map {
        """(\d*)-(\d*),(\d*)-(\d*)""".toRegex().find(it)!!.groups.drop(1).map { group ->
            group!!.value.toInt()
        }
    }

fun hasFullyContainedAssignment(values: List<Int>): Boolean {
    val (start1, end1, start2, end2) = values
    return (start1 <= start2 && end1 >= end2) || (start2 <= start1 && end2 >= end1)
}

fun hasPartiallyOverlappingAssignment(values: List<Int>): Boolean {
    val (start1, end1, start2, end2) = values
    return !((end2 < start1) || (start2 > end1))
}

fun main() {
    println("Part One: ${ranges.count { hasFullyContainedAssignment(it) }}")
    println("Part Two: ${ranges.count { hasPartiallyOverlappingAssignment(it) }}")
}