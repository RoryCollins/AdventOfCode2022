package day06

import java.io.File

val signal = File("src/main/kotlin/day06/input.txt").readText()

fun main(){
    println("Part One: ${lastIndexOfNUniqueCharacters(4, signal)}")
    println("Part Two: ${lastIndexOfNUniqueCharacters(14, signal)}")
}

private fun lastIndexOfNUniqueCharacters(n: Int, signal: String) = signal.windowed(n).indexOfFirst { allDifferent(it) } + n

private fun allDifferent(window: String) = window.toSet().count() == window.length