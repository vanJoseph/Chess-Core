import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import wildercoding.chess.*

class QueenTest : PieceTest {

    @Test
    override fun Should_VerifyCorrectMovePattern_When_GivenAMove() {
        val board = Board()

        val legalMoves=arrayOf<Coord>(Coord(0,1), Coord(1,1), Coord(1,0), Coord(7,7), Coord(0,7), Coord(7,0))

        val startlocation= Coord(0,0)
        val queen= Queen(Player.BLACK)
        board.addPiece(queen,startlocation)

        for (move in legalMoves) {
            assertTrue(queen.verifyMove(move))
        }
    }

    @Test
    override fun Should_Not_VerifyCorrectMovePattern_When_GivenAMove() {
        val board = Board()

        val illegalMoves=arrayListOf<Coord>()
        val startlocation= Coord(0,0)
        // The queen should not be able to do knight moves
        val knight=Knight(Player.BLACK)
        knight.location = startlocation
        illegalMoves.addAll(knight.generateMovesList())


        val queen= Queen(Player.BLACK)
        board.addPiece(queen,startlocation)

        for (move in illegalMoves) {
            assertFalse(queen.verifyMove(move))
        }
    }
}