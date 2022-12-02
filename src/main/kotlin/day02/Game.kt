package day02

import day02.Shape.*

class Game(private val player1 : Shape, private val player2: Shape) {
    fun scoreGame(): Int {
        return scoreShape() + scoreResult()
    }

    private fun scoreShape(): Int {
        return when (player2) {
            ROCK -> 1
            PAPER -> 2
            SCISSORS -> 3
        }
    }

    private fun scoreResult(): Int {
        val loss = 0
        val draw = 3
        val win = 6

        return when(player2){
            player1 -> draw
            player1.getLoss() -> loss
            else -> win
        }
    }
}