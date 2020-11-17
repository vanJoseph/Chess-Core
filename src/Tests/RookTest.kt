import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import wildercoding.chess.*


class RookTest : PieceTest {

    @Test
    fun Should_NotPassPiece_When_PieceIsBlockingPath() {
        val board = Board()
        val rook = Rook(Player.WHITE)
        val startlocation = Coord(5, 5)
        board.addPiece(rook, startlocation)
        val passingMoves = arrayOf<Coord>(Coord(0, 5), Coord(7, 5), Coord(5, 0), Coord(5, 7))
        val blockingPieceLocation = arrayOf<Coord>(Coord(1, 3), Coord(6, 5), Coord(5, 3), Coord(5, 6))

        // Populate the board with Blocking pieces
        for (location in blockingPieceLocation) {
            board.addPiece(Pawn(Player.WHITE), location)
        }

        // Attempt the passing moves
        for (move in passingMoves) {
            val gameManager = GameManager(board, DirectInputMethod(startlocation, move), null)
            //val debug=gameManager.executeMove()
            assertFalse(gameManager.executeMove().success, "This piece can not pass another")
        }

    }

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