package day09

import shared.Coordinate
import java.io.File

val input = File("src/main/kotlin/day09/input_test.txt")
    .readLines()
    .map{"""([UDRL]) (\d+)""".toRegex().find(it)!!.groups}
    .map{it[1]!!.value to it[2]!!.value}

private fun parseInstruction(instruction: Pair<String, String>) : Pair<Coordinate, Int>{
    val amount = instruction.second.toInt()
    val direction = when(instruction.first){
        "U" -> Coordinate(0,1)
        "D" -> Coordinate(0,-1)
        "L" -> Coordinate(-1,0)
        "R" -> Coordinate(1,0)
        else -> error("oh no")
    }
    return direction to amount
}

private fun processInstruction(instruction: Pair<Coordinate, Int>) {
    val foo = Coordinate(0,0)
    repeat(instruction.second) {
        foo.plus(instruction.first)
    }
}

fun main(){
    input.map{println(it)}
    println("Part One: ")
    println("Part Two: ")
}