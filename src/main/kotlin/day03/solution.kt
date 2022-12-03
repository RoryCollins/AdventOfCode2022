package day03

import java.io.File

val rucksacks = File("src/main/kotlin/day03/input.txt")
    .readLines()

val itemTypeValues = (('a'..'z').toList() + ('A'..'Z').toList()).zip((1..52)).toMap()

fun splitIntoCompartments (rucksack : String) : Pair<Set<Char>, Set<Char>>{
    val half = rucksack.length / 2
    return rucksack.take(half).toSet() to rucksack.substring(half).toSet()
}

fun main() {
    val commonItemTypesBetweenRucksackCompartments = rucksacks
        .map { splitIntoCompartments(it) }
        .map { it.first.intersect(it.second).single() }

    val commonItemTypesBetweenElves = rucksacks
        .map { it.toSet() }
        .windowed(3, 3)
        .map { it[0].intersect(it[1]).intersect(it[2]).single() }

    println("Part One: ${commonItemTypesBetweenRucksackCompartments.sumOf { itemTypeValues[it]!! }}")
    println("Part Two: ${commonItemTypesBetweenElves.sumOf { itemTypeValues[it]!! }}")
}