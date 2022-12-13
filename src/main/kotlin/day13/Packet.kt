package day13

abstract class Packet : Comparable<Packet>{
    companion object{
        fun parse(str: String) : Packet {
            if(str == "[]")
                return PacketComposite(emptyList())
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
}

data class PacketComposite(val children: List<Packet>) : Packet() {
    override fun compareTo(other: Packet): Int {
        val b = when (other) {
            is PacketComposite -> {
                repeat(minOf(this.children.size, other.children.size)){i ->
                    val result = this.children[i].compareTo(other.children[i])
                    if(result != 0){
                        return result
                    }
                }
                return this.children.size.compareTo(other.children.size)
            }
            is PacketValue -> compareTo(PacketComposite(listOf(other)))
            else -> error("oh no")
        }
        return b
    }
}

data class PacketValue(val value: Int) : Packet() {
    override fun compareTo(other: Packet): Int {
        val b = when (other) {
            is PacketComposite -> PacketComposite(listOf(this)).compareTo(other)
            is PacketValue -> this.value.compareTo(other.value)
            else -> error("oh no")
        }
        return b
    }
}
