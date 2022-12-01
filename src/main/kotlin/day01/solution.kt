package day01

import java.io.File

val sortedCalorieTotals =
    File("src/main/kotlin/day01/input.txt")
        .readText().split("\n\n", "\r\n\r\n")
        .map(::countCalories)
        .sortedDescending()

fun countCalories(elf: String): Int {
    return elf.split("\n", "\r\n").sumOf { it.toInt() }
}

fun main() {
    println("Part One: ${sortedCalorieTotals.first()}")
    println("Part Two: ${sortedCalorieTotals.take(3).sum()}")
}