package day01

import java.io.File

val input = File("src/main/kotlin/day01/input.txt")
    .readText()
    .split("\n\n", "\r\n\r\n")
    .map{elf ->
        elf.split("\n", "\r\n").sumOf { it.toInt() }
    }
    .sortedDescending()
    .take(3)

fun main(){
    println("Part One: ${input.first()}")
    println("Part Two: ${input.sum()}")
}