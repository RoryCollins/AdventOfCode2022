package day02

enum class Shape(){
    ROCK, PAPER, SCISSORS;
    companion object{
        fun get(move: String) : Shape {
            return when(move) {
                "X","A" -> ROCK
                "Y","B" -> PAPER
                "Z","C" -> SCISSORS
                else -> {error("Oh no")}
            }
        }
    }

    fun getWin(): Shape {
        return when(this){
            ROCK -> PAPER
            PAPER -> SCISSORS
            SCISSORS -> ROCK
        }
    }

    fun getLoss(): Shape {
        return when(this){
            ROCK -> SCISSORS
            PAPER -> ROCK
            SCISSORS -> PAPER
        }
    }
}