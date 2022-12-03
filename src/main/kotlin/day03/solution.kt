package day03

import java.io.File

val rucksacks = File("src/main/kotlin/day03/input.txt")
    .readLines()

val itemTypeValues = (('a'..'z').toList() + ('A'..'Z').toList()).zip((1..52)).toMap()

fun main() {
    val commonItemTypesBetweenRucksackCompartments = rucksacks
        .map { it.take(it.length / 2).toSet() to it.substring(it.length / 2).toSet() }
        .map { it.first.intersect(it.second).single() }

    val commonItemTypesBetweenElves = rucksacks
        .map { it.toSet() }
        .windowed(3, 3)
        .map { it[0].intersect(it[1]).intersect(it[2]).single() }

    println("Part One: ${commonItemTypesBetweenRucksackCompartments.sumOf { itemTypeValues[it]!! }}")
    println("Part Two: ${commonItemTypesBetweenElves.sumOf { itemTypeValues[it]!! }}")
}