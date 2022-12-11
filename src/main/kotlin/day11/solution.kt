package day11

import java.io.File

val input = File("src/main/kotlin/day11/input_test.txt")
    .readText()
    .split("\n\n", "\r\n\r\n")
    .map { it.lines() }

data class Monkey(
    val items: MutableList<Long>,
    val operation: (x: Long) -> Long,
    val test: Long,
    val recipientOnPass: Int,
    val recipientOnFail: Int,
    var inspections: Long = 0L
)

fun main() {
    println("Part One: ${processMonkeyBusiness(20)}")
    println("Part Two: ${processMonkeyBusiness(10_000)}")
}

private fun processMonkeyBusiness(times: Int): Long {
    val monkeys = input.map { createMonkey(it) }
    val lcm = monkeys.map { it.test }.reduce { acc, l -> acc * l }
    repeat(times) {
        for (monkey in monkeys) {
            for (item in monkey.items) {
                monkey.inspections++
                val newWorryLevel = monkey.operation(item) % lcm
                monkeys[if (newWorryLevel % monkey.test == 0L) monkey.recipientOnPass else monkey.recipientOnFail].items.add(
                    newWorryLevel
                )
            }
            monkey.items.clear()
        }
    }

    val mostActiveMonkeys = monkeys.map { it.inspections }.sortedDescending().take(2)
    return mostActiveMonkeys[0] * mostActiveMonkeys[1]
}

private fun createMonkey(definition: List<String>): Monkey {
    val startingItems = """Starting items: (.*)""".toRegex()
        .find(definition[1])!!.groups
        .last()!!.value
        .split(", ")
        .map { it.toLong() }

    val args = definition[2].split(" ").takeLast(2)
    val operation: (x: Long) -> Long = { x ->
        val y = if (args[1] == "old") x
        else args[1].toLong()

        if (args[0] == "*") x * y
        else x + y
    }

    val test = """Test: divisible by (\d*)""".toRegex()
        .find(definition[3])!!
        .groups
        .last()!!
        .value.toLong()

    val recipientOnPass = Character.getNumericValue(definition[4].last())
    val recipientOnFail = Character.getNumericValue(definition[5].last())

    return Monkey(startingItems.toMutableList(), operation, test, recipientOnPass, recipientOnFail)
}