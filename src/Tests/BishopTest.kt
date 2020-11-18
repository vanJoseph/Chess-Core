import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import wildercoding.chess.*

class BishopTest: PieceTest {


    @Test
    fun Should_NotPassPiece_When_PieceIsBlockingPath() {
        val board = Board()
        val bishop = Bishop(Player.WHITE,board)
        val startlocation = Coord(4, 3)
        board.addPiece(bishop, startlocation)
        val passingMoves = arrayOf<Coord>(Coord(0, 7), Coord(7, 6), Coord(1, 0), Coord(7, 0))
        val blockingPieceLocation = arrayOf<Coord>(Coord(2, 1), Coord(2, 5), Coord(6, 5), Coord(7, 0))

        // Populate the board with Blocking pieces
        for (location in blockingPieceLocation) {
            board.addPiece(Pawn(Player.WHITE), location)
        }
        // Test the passing move in all directions
        for (move in passingMoves) {
            assertFalse(bishop.verifyMove(move), "This piece can not pass another")
        }

    }
    @Test
    override fun Should_VerifyCorrectMovePattern_When_GivenAMove() {
        val board = Board()

        val legalMoves=arrayOf<Coord>( Coord(1,1), Coord(5,5), Coord(7,7))

        val startlocation= Coord(0,0)
        val bishop= Bishop(Player.BLACK)
        board.addPiece(bishop,startlocation)

        for (move in legalMoves) {
            assertTrue(bishop.verifyMove(move),"legalMove:$move is unreachable from: $startlocation")
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