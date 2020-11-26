package wildercoding.chess

interface OutputMethod {
    fun display(playerTurn:Color, isPlayable:Boolean, isInCheck: Boolean , moveInfo: MoveInfo?, board :Board)
}