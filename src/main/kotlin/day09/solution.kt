package day09

import shared.Coordinate
import java.io.File

val input = File("src/main/kotlin/day09/input.txt")
    .readLines()
    .map { """([UDRL]) (\d+)""".toRegex().find(it)!!.groups }
    .map { it[1]!!.value to it[2]!!.value }
    .map { parseInstruction(it) }

private fun parseInstruction(instruction: Pair<String, String>): Pair<Coordinate, Int> {
    val amount = instruction.second.toInt()
    val direction = when (instruction.first) {
        "U" -> Coordinate(0, 1)
        "D" -> Coordinate(0, -1)
        "L" -> Coordinate(-1, 0)
        "R" -> Coordinate(1, 0)
        else -> error("oh no")
    }
    return direction to amount
}

private data class KnotState(val history: Set<Coordinate>, val current: Coordinate)

private fun processInstructionFoo(knotStates: List<KnotState>, instruction: Pair<Coordinate, Int>): List<KnotState> {
    val (direction, amount) = instruction
    if (amount == 0) {
        return knotStates
    }

    val newKnotStates = knotStates.toMutableList()
    val newHead = knotStates[0].current.plus(direction)
    newKnotStates[0] = KnotState(newKnotStates[0].history + newHead, newHead)
    for (idx in 1 until knotStates.size){
        val newPos = getNewPosition(newKnotStates[idx-1].current, newKnotStates[idx].current)
        newKnotStates[idx] = KnotState(newKnotStates[idx].history + newPos, newPos)
    }

    return processInstructionFoo(newKnotStates, direction to amount - 1)
}

fun getNewPosition(leader: Coordinate, current: Coordinate): Coordinate {
    return when (leader.x - current.x to leader.y - current.y) {
        Pair(2, 2) -> current.plus(Coordinate(1, 1))
        Pair(2, -2) -> current.plus(Coordinate(1, -1))
        Pair(-2, 2) -> current.plus(Coordinate(-1, 1))
        Pair(-2, -2) -> current.plus(Coordinate(-1, -1))
        Pair(2, 1), Pair(2, -1), Pair(2, 0) -> Coordinate(current.x + 1, leader.y)
        Pair(-2, 1), Pair(-2, -1), Pair(-2, 0) -> Coordinate(current.x - 1, leader.y)
        Pair(0, 2), Pair(1, 2), Pair(-1, 2) -> Coordinate(leader.x, current.y + 1)
        Pair(0, -2), Pair(1, -2), Pair(-1, -2) -> Coordinate(leader.x, current.y - 1)
        else -> current
    }
}

fun totalUniquePositionsVisitedByTailOfRopeWithSize(size : Int): Int {
    val initialState = List(size) { KnotState(emptySet(), Coordinate(0, 0)) }
    val result = input.fold(initialState) { acc, instruction -> processInstructionFoo(acc, instruction) }
    return result.last().history.count()
}
fun main() {
    println("Part One: ${totalUniquePositionsVisitedByTailOfRopeWithSize(2)}")
    println("Part Two: ${totalUniquePositionsVisitedByTailOfRopeWithSize(10)} ")
}