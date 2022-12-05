package day05

import java.io.File

fun parseRow(row: String, indices: List<Int>): List<Char> {
    return indices.map { row.getOrElse(it * 4 + 1) { ' ' } }
}

fun transpose(manifest: List<List<Char>>): List<String> {
    return manifest[0].indices
        .map { index ->
            manifest.map { it[index] }
        }.map { it.joinToString("").trim() }
}

fun processInstruction(instruction: Instruction, cargo: List<String>, crateMover9000: Boolean): List<String> {
    val processedCargo = cargo.toMutableList()
    val count = instruction.count
    val source = instruction.source
    val target = instruction.target

    processedCargo[source] = cargo[source].drop(count)
    val cratesInTransit = cargo[source].take(count)

    processedCargo[target] = if (crateMover9000) cratesInTransit.plus(cargo[target])
    else cratesInTransit.reversed().plus(cargo[target])

    return processedCargo
}

private fun parseInstruction(instruction: String): Instruction {
    val (count, _source, _target) = """move (\d*) from (\d*) to (\d*)""".toRegex().find(instruction)!!.groups.drop(1)
        .map { it!!.value.toInt() }
    val source = _source - 1
    val target = _target - 1
    return Instruction(count, source, target)
}

data class Instruction(val count: Int, val source: Int, val target: Int)

fun main() {
    val (manifest, instructions) = File("src/main/kotlin/day05/input.txt")
        .readText()
        .split("\n\n", "\r\n\r\n")
        .map { it.lines() }
    val indices = manifest.last().split("""\s+""".toRegex()).drop(1).map { it.toInt() - 1 }
    val cargo = transpose(manifest.dropLast(1).map { parseRow(it, indices) })

    val processedCargo = instructions
        .map { parseInstruction(it) }
        .fold(cargo) { acc, next -> processInstruction(next, acc, false) }

    val processedCargoWithCrateMover9000 = instructions
        .map { parseInstruction(it) }
        .fold(cargo) { acc, next -> processInstruction(next, acc, true) }

    println(
        "Part One: ${
            processedCargo.map { if (it.isEmpty()) "" else it.first() }.joinToString("")
        }"
    )
    println(
        "Part Two: ${
            processedCargoWithCrateMover9000.map { if (it.isEmpty()) "" else it.first() }.joinToString("")
        }"
    )
}
