package com.wildercoding.chess.iomethods

import com.wildercoding.chess.units.Board
import com.wildercoding.chess.units.Color
import com.wildercoding.chess.data.MoveInfo

class ConsoleOutputMethod: OutputMethod {
    override fun display(playerTurn: Color, isPlayable: Boolean, isInCheck: Boolean, moveInfo: MoveInfo?, board: Board) {
        println(board)
        println("It is $playerTurn's turn.")
        if (moveInfo != null) {
            if (moveInfo.success == false){
                println("Move failed because: ${moveInfo.reason}")
            }
        }
        if(isInCheck){
            println("CHECK!")
        }
    }
}