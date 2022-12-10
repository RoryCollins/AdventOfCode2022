package day10

import java.io.File

val input = File("src/main/kotlin/day10/input.txt")
    .readLines()
    .map{it.split(" ")}

fun main(){
    val signals = mutableListOf(1)
    input.forEach {
        signals.add(signals.last())
        if(it.size == 2){
            signals.add(signals.last() + it[1].toInt())
        }
    }

    val image = signals.chunked(40).map{row -> row.mapIndexed{cycle, signal ->
        if((signal-1..signal+1).contains(cycle)) "#" else "."
    }}

    println("Part One: ${listOf(20, 60, 100, 140, 180, 220).sumOf { signals[it - 1] * it }}")
    println("Part Two: ")
    image.map { println(it.joinToString("")) }
}

