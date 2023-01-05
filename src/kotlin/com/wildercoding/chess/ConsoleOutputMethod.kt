package wildercoding.chess

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