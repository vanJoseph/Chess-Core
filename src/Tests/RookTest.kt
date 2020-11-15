import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import wildercoding.chess.Board
import wildercoding.chess.Coord
import wildercoding.chess.Player
import wildercoding.chess.Rook


class RookTest : PieceTest {

    @Test
    override fun Should_VerifyCorrectMovePattern_When_GivenAMove() {
        val board = Board()

        val legalMoves=arrayOf<Coord>(Coord(0,7), Coord(7,0))

        val startlocation=Coord(0,0)
        val rook= Rook(Player.BLACK)
        board.addPiece(rook,startlocation)

        for (move in legalMoves) {
            assertTrue(rook.verifyMove(move))
        }

    }
    @Test
    override fun Should_Not_VerifyCorrectMovePattern_When_GivenAMove() {
        val board = Board()

        val ilegalMoves=arrayOf<Coord>(Coord(0,0), Coord(1,1), Coord(5,5), Coord(7,7))

        val startlocation=Coord(0,0)
        val rook= Rook(Player.BLACK)
        board.addPiece(rook,startlocation)

        for (move in ilegalMoves) {
            assertFalse(rook.verifyMove(move),"Rook should not be able to move to: $move")
        }
    }
}