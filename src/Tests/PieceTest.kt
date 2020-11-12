import wildercoding.chess.Board

abstract class PieceTest {
   abstract fun Should_VerifyCorrectMovePattern_When_GivenAMove()
   abstract fun Should_Not_VerifyCorrectMovePattern_When_GivenAMove()
    fun printBoard(board: Board) {
        println(board.toString())
    }
}