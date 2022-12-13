package day13

import java.io.File

private val packets = File("src/main/kotlin/day13/input.txt")
    .readText().split("\n\n", "\r\n\r\n")
    .flatMap { it.lines() }
    .map { Packet.parse(it) }

fun main() {
    val orderedPackets = packets
        .chunked(2)
        .mapIndexed { index, ps -> index + 1 to (ps.first() < ps.last()) }
        .filter { it.second }
    println("Part One: ${orderedPackets.sumOf { it.first }}")

    val dividerOne = Packet.parse("[[2]]")
    val dividerTwo = Packet.parse("[[6]]")
    val inputWithDividers = packets + listOf(dividerOne, dividerTwo)
    val sortedPackets = inputWithDividers.sorted()
    println("Part Two: ${(sortedPackets.indexOf(dividerOne) + 1) * (sortedPackets.indexOf(dividerTwo) + 1)}")
}