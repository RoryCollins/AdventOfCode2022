package day16

import shared.Coordinate
import java.io.File
import kotlin.time.measureTimedValue

private val valves = File("src/main/kotlin/day16/input_test.txt")
    .readLines()
    .map {
        """Valve (.{2}) has flow rate=(\d+); tunnel(s?) lead(s?) to valve(s?) (.*)""".toRegex()
            .find(it)!!.groups.drop(1).map { it!!.value }
    }.map {
        Valve(it[0], it[1].toInt(), it.last().split(", "), false)
    }

private val valveRegister = valves.associateBy { it.name }
private val shortestPaths =
    floydWarshall(valves.associate { it.name to it.connections.associateWith { 1 }.toMutableMap() }.toMutableMap())

fun doIt(currentValve: Valve, timeRemaining: Int, totalPressure: Int, visited: Set<Valve>) : Int{
    if(!currentValve.open && currentValve.flowRate > 0){
        currentValve.open = true
        return doIt(currentValve, timeRemaining-1, totalPressure+(currentValve.flowRate*(timeRemaining-1)), visited + currentValve)
    }

    val availableValves = shortestPaths[currentValve.name]!!.keys.map{ valveRegister[it] }.filter { !visited.contains(it) }

    if(availableValves.isEmpty() || timeRemaining <= 0){
        return totalPressure
    }
    return availableValves
        .maxOf{valve ->
            val newTimeRemaining = timeRemaining - shortestPaths[currentValve.name]?.get(valve?.name)!!
            doIt(valve!!, newTimeRemaining, totalPressure, visited+currentValve)
        }
}

fun main() {
    shortestPaths.map {
        println(it)
    }
    println("Part One: ${doIt(valveRegister["AA"]!!, 30, 0, emptySet())}")
    println("Part Two: ")
}


class Valve(val name: String, val flowRate: Int, val connections: List<String>, var open: Boolean) {
}

private fun floydWarshall(shortestPaths: MutableMap<String, MutableMap<String, Int>>): MutableMap<String, MutableMap<String, Int>> {
    for (k in shortestPaths.keys) {
        for (i in shortestPaths.keys) {
            for (j in shortestPaths.keys) {
                //use `Int.MAX_VALUE/2` to avoid disaster when doing `ik + kj`
                val ik = shortestPaths[i]?.get(k) ?: (Int.MAX_VALUE / 2)
                val kj = shortestPaths[k]?.get(j) ?: (Int.MAX_VALUE / 2)
                val ij = shortestPaths[i]?.get(j) ?: (Int.MAX_VALUE / 2)
                if (ik + kj < ij) {
                    shortestPaths[i]?.set(j, ik + kj)
                }
            }
        }
    }
    shortestPaths.values.forEach {
        it.keys.removeIf { key -> valveRegister[key]?.flowRate == 0 }
    }
    return shortestPaths
}