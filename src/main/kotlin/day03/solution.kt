package day03

import java.io.File

val rucksacks = File("src/main/kotlin/day03/input.txt")
    .readLines()

val itemTypeValues = (('a'..'z').toList() + ('A'..'Z').toList()).zip((1..52)).toMap()

fun splitIntoCompartments(rucksack: String): Pair<Set<Char>, Set<Char>> {
    val half = rucksack.length / 2
    return rucksack.take(half).toSet() to rucksack.drop(half).toSet()
}

fun main() {
    val commonItemTypeBetweenRucksackCompartments = rucksacks
        .map { splitIntoCompartments(it) }
        .map { it.first.intersect(it.second).single() }

    val commonItemTypeBetweenElves = rucksacks
        .map { it.toSet() }
        .windowed(3, 3)
        .map { it.reduce { acc, next -> acc.intersect(next) }.single() }

    println("Part One: ${commonItemTypeBetweenRucksackCompartments.sumOf { itemTypeValues[it]!! }}")
    println("Part Two: ${commonItemTypeBetweenElves.sumOf { itemTypeValues[it]!! }}")
}