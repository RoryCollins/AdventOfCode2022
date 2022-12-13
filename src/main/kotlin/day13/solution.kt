package day13

import java.io.File

private val input = File("src/main/kotlin/day13/input.txt")
    .readText().split("\n\n","\r\n\r\n")
    .map{it.lines()}

abstract class Packet{
    companion object{
        fun parse(str: String) : Packet?{
            if (str.isEmpty()) {
                return null
            }
            if(str.first().isDigit()){
                return PacketValue(str.toInt())
            }

            var depth = 0
            var lastComma = 0
            val packets = mutableListOf<Packet?>()

            for ((i, c) in str.withIndex()) {
                if (c == '[') {
                    depth++
                } else if (c == ']') {
                    depth--
                    if (depth == 0) {
                        packets += parse(str.take(i).drop(lastComma + 1))
                    }
                }
                if (c == ',') {
                    if (depth == 1) {
                        packets += parse(str.take(i).drop(lastComma + 1))
                        lastComma = i
                    }
                }
            }
            return PacketComposite(packets.filterNotNull())
        }
    }

    abstract fun compare(other : Packet) : Int
}
data class PacketComposite(val children: List<Packet>) : Packet() {
    override fun compare(other: Packet): Int {
        val b = when (other) {
            is PacketComposite -> {
                repeat(minOf(this.children.size, other.children.size)){i ->
                    val result = this.children[i].compare(other.children[i])
                    if(result != 0){
                        return result
                    }
                }
                return this.children.size.compareTo(other.children.size)
            }
            is PacketValue -> compare(PacketComposite(listOf(other)))
            else -> error("oh no")
        }
        return b
    }
}

data class PacketValue(val value: Int) : Packet() {
    override fun compare(other: Packet): Int {
        val b = when (other) {
            is PacketComposite -> PacketComposite(listOf(this)).compare(other)
            is PacketValue -> this.value.compareTo(other.value)
            else -> error("oh no")
        }
        return b
    }
}


fun main(){
    val foo = input.mapIndexed{ index, it ->
        index to Packet.parse(it[0])!!.compare(Packet.parse(it[1])!!)
    }

    println("Part One: ${foo.filter { it.second == -1 }.sumOf { it.first+1 }}")
    println("Part Two: ")
}