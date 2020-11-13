import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import wildercoding.chess.*

class KingTest : PieceTest {

    @Test
    override fun Should_VerifyCorrectMovePattern_When_GivenAMove() {
        val board = Board()

        /*
         Legal moves should include:
         - Moves in an Open Position
         - Moves in a corner
         - Moves along the endges
         */
        val legalMoves =arrayOf<Coord>(Coord(0,1), Coord(1,0), Coord(1,1))
        val startingPosition = Coord(0,0)

        val king = King(Player.WHITE)
        board.addPiece(king, startingPosition)

        for (move in legalMoves) {
            assertTrue(king.verifyMove(move))
        }
    }

    @Test
    override fun Should_Not_VerifyCorrectMovePattern_When_GivenAMove() {
        val board = Board()

        /*
         Illegal moves should include:
         - Current Position
         - Moves outside of the board that are with in its possible moves if the board was extended
         - Moves that are out side of it legal moves
         */
        val illegalMoves =arrayOf<Coord>(Coord(0,0), Coord(-1,-1), Coord(2,2), Coord(0,2), Coord(2,0))
        val startingPosition = Coord(0,0)

        val king = King(Player.WHITE)
        board.addPiece(king, startingPosition)

        for (move in illegalMoves) {
            assertFalse(king.verifyMove(move))
        }
    }
}