package day06

import java.io.File

val input = File("src/main/kotlin/day06/input.txt")
    .readText()

fun main(){
    println("Part One: ${lastIndexOfNUniqueCharacters(4)}")
    println("Part Two: ${lastIndexOfNUniqueCharacters(14)}")
}

private fun lastIndexOfNUniqueCharacters(n : Int) = input.windowed(n).indexOfFirst { allDifferent(n, it) } + n

private fun allDifferent(windowSize: Int, it: String) = it.toCharArray().toSet().count() == windowSize