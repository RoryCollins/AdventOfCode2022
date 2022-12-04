package day03

import java.io.File

val rucksacks = File("src/main/kotlin/day03/input.txt")
    .readLines()

val itemTypeValues = (('a'..'z').toList() + ('A'..'Z').toList()).zip((1..52)).toMap()

fun splitIntoCompartments(rucksack: String): List<Set<Char>> {
    val half = rucksack.length / 2
    return listOf(rucksack.take(half).toSet(), rucksack.drop(half).toSet())
}

fun getSingleCommonItemType(itemTypes: List<Set<Char>>): Char {
    return itemTypes.reduce { acc, next -> acc.intersect(next) }.single()
}

fun Char.priority() : Int {
    return itemTypeValues[this]!!
}

fun main() {
    val commonItemTypeBetweenRucksackCompartments = rucksacks
        .map { splitIntoCompartments(it) }
        .map { getSingleCommonItemType(it) }

    val commonItemTypeBetweenElves = rucksacks
        .map { it.toSet() }
        .windowed(3, 3)
        .map { getSingleCommonItemType(it) }

    println("Part One: ${commonItemTypeBetweenRucksackCompartments.sumOf { it.priority() }}")
    println("Part Two: ${commonItemTypeBetweenElves.sumOf { it.priority() }}")
}