package day16

import java.io.File
import java.lang.Integer.max

private val valves = File("src/main/kotlin/day16/input.txt")
    .readLines()
    .map {
        """Valve (.{2}) has flow rate=(\d+); tunnel(s?) lead(s?) to valve(s?) (.*)""".toRegex()
            .find(it)!!.groups.drop(1).map { it!!.value }
    }.map {
        Valve(it[0], it[1].toInt(), it.last().split(", "), false)
    }

private val valveRegister = valves.associateBy { it.name }
private val shortestPaths = floydWarshall(valves.associate { it.name to it.connections.associateWith { 1 }.toMutableMap() }.toMutableMap())
private var score = 0

private fun depthFirstSearch(currScore: Int, currentValve: String, visited: Set<String>, timeRemaining: Int) {
    score = max(score, currScore)
    for ((valve, dist) in shortestPaths[currentValve]!!) {
        if (!visited.contains(valve) && timeRemaining - (dist + 1) > 0) {
            depthFirstSearch(
                currScore + (timeRemaining - (dist + 1)) * valveRegister[valve]!!.flowRate,
                valve,
                visited + valve,
                timeRemaining - (dist + 1)
            )
        }
    }
}

fun main() {
    depthFirstSearch(score, "AA", emptySet(), 30)

    println("Part One: $score")
    println("Part Two: $score")
}


data class Valve(val name: String, val flowRate: Int, val connections: List<String>, var open: Boolean)

private fun floydWarshall(shortestPaths: MutableMap<String, MutableMap<String, Int>>): MutableMap<String, MutableMap<String, Int>> {
    for (k in shortestPaths.keys) {
        for (i in shortestPaths.keys) {
            for (j in shortestPaths.keys) {
                //`Int.MAX_VALUE` blows up when doing `ik + kj`
                val ik = shortestPaths[i]?.get(k) ?: 999
                val kj = shortestPaths[k]?.get(j) ?: 999
                val ij = shortestPaths[i]?.get(j) ?: 999
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