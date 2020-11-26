package wildercoding.chess

class ConsoleOutputMethod: OutputMethod {
    override fun display(playerTurn: Color, isPlayable: Boolean, isInCheck: Boolean, moveInfo: MoveInfo?, board: Board) {
        println(board)
    }
}