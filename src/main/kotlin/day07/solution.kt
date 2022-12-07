package day07

import java.io.File

val input = File("src/main/kotlin/day07/input.txt")
    .readLines()

val directoryRegister = mutableSetOf<Directory>()

fun process(input: List<String>, currentDirectory: Directory) {
    if (input.isEmpty()) {
        return
    }
    val command = input.first().split(" ")
    val remainingInput = input.drop(1)
    if (command[1] == "cd") {
        if (command[2] == "..") {
            return process(remainingInput, currentDirectory.parent!!)
        }
        return process(remainingInput, currentDirectory.navigateTo(command[2]))
    }
    if (command[1] == "ls") {
        val contents = remainingInput.takeWhile { !it.startsWith("$") }
        currentDirectory.addContent(contents)
        directoryRegister.add(currentDirectory)
        return process(remainingInput.dropWhile { !it.startsWith("$") }, currentDirectory)
    }
}

fun main() {
    val root = Directory(".", null)
    root.addContent(listOf("dir /"))

    process(input, root)

    val directorySizes = directoryRegister.map { it.getSize() }
    val spaceRequired = 30000000 - (70000000 - root.getSize())

    println("Part One: ${directorySizes.filter { it <= 100000 }.sum()}")
    println("Part Two: ${directorySizes.filter { it > spaceRequired }.min()}")
}