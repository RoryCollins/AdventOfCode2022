package day06

import java.io.File

val signal = File("src/main/kotlin/day06/input.txt").readText()

fun main(){
    println("Part One: ${indexAfterNUniqueCharacters(signal, 4)}")
    println("Part Two: ${indexAfterNUniqueCharacters(signal, 14)}")
}

private fun indexAfterNUniqueCharacters(signal: String, n: Int) = signal
    .windowed(n)
    .indexOfFirst { allDifferent(it) } + n

private fun allDifferent(window: String) = window.toSet().count() == window.length