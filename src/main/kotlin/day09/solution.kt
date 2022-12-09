package day09

import shared.Coordinate
import java.io.File

val input = File("src/main/kotlin/day09/input.txt")
    .readLines()
    .map{"""([UDRL]) (\d+)""".toRegex().find(it)!!.groups}
    .map{it[1]!!.value to it[2]!!.value}
    .map{ parseInstruction(it) }

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

private data class State(val tailSet : Set<Coordinate>, val currentHead : Coordinate, val currentTail: Coordinate)

private fun processInstruction(state: State, instruction: Pair<Coordinate, Int>) : State {
    val (direction, amount) = instruction
    if(amount == 0 ) return state
    val newHead = state.currentHead.plus(direction)
    val newTail =
        if(newHead.x - state.currentTail.x == 2) Coordinate(newHead.x-1, newHead.y)
        else if(newHead.x - state.currentTail.x == -2) Coordinate(newHead.x+1, newHead.y)
        else if(newHead.y - state.currentTail.y == 2) Coordinate(newHead.x, newHead.y-1)
        else if(newHead.y - state.currentTail.y == -2) Coordinate(newHead.x, newHead.y+1)
        else state.currentTail
    val newTailSet = state.tailSet + newTail
    return processInstruction(State(newTailSet, newHead, newTail), direction to amount-1)
}

fun main(){
    val initialState = State(emptySet(), Coordinate(0,0),  Coordinate(0,0))
    val foo = input.fold(initialState){acc, instruction -> processInstruction(acc, instruction) }
    println("Part One: ${foo.tailSet.count()}")
    println("Part Two: ")
}