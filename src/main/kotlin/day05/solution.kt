package day05

import java.io.File

fun parse(manifest: String, indices: List<Int>): List<Char> {
    return indices.map { manifest.getOrElse(it * 4 + 1) { ' ' } }
}

fun transpose(manifest: List<List<Char>>): List<String> {
    val indices = manifest[0].indices
    val map = indices
        .map { index ->
            manifest.map { it[index] }
        }
    return map.map { it.joinToString("").trim() }
}

fun processInstruction(instruction: String, cargo: List<String>): List<String> {
    val mutableCargo = cargo.toMutableList()
    val (count, source, target) = """move (\d*) from (\d*) to (\d*)""".toRegex().find(instruction)!!.groups.drop(1)
        .map { it!!.value.toInt() }
    mutableCargo[source-1] = cargo[source-1].drop(count)
    mutableCargo[target-1] = cargo[source-1].take(count).reversed().plus(cargo[target-1])
    return mutableCargo
}

fun main() {
    val (manifest, instructions) = File("src/main/kotlin/day05/input.txt")
        .readText()
        .split("\n\n", "\r\n\r\n")
        .map { it.lines() }
    val indices = manifest.last().split("""\s+""".toRegex()).drop(1).map{it.toInt() - 1 }
    val cargo = transpose(manifest.dropLast(1).map { parse(it, indices) })

    val processedCargo = instructions.fold(cargo) { acc, next -> processInstruction(next, acc) }

    println("Part One: ${processedCargo.map { if (it.isEmpty()) "" else it.first() }.joinToString("")}")
    println("Part Two: ")
}
