import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import wildercoding.chess.*

class BishopTest: PieceTest {
    @Test
    override fun Should_VerifyCorrectMovePattern_When_GivenAMove() {
        val board = Board()

        val legalMoves=arrayOf<Coord>( Coord(1,1), Coord(5,5), Coord(7,7))

        val startlocation= Coord(0,0)
        val bishop= Bishop(Player.BLACK)
        board.addPiece(bishop,startlocation)

        for (move in legalMoves) {
            assertTrue(bishop.verifyMove(move))
        }
    }
@Test
    override fun Should_Not_VerifyCorrectMovePattern_When_GivenAMove() {
        val board = Board()

        val ilegalMoves=arrayOf<Coord>(Coord(0,0), Coord(0,1), Coord(1,0), Coord(2,0))

        val startlocation= Coord(0,0)
        val bishop= Bishop(Player.BLACK)
        board.addPiece(bishop,startlocation)

        for (move in ilegalMoves) {
            assertFalse(bishop.verifyMove(move))
        }
    }
}