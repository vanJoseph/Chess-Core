import wildercoding.chess.Board

interface PieceTest {
   /*
         Legal moves should include:
         - Moves in an Open Position
         - Moves in a corner
         - Moves along the endges
         */
   abstract fun Should_VerifyCorrectMovePattern_When_GivenAMove()

   /*
         Illegal moves should include:
         - Current Position
         - Moves outside of the board
         - Moves that are out side of it legal moves
         */
   abstract fun Should_Not_VerifyCorrectMovePattern_When_GivenAMove()

}