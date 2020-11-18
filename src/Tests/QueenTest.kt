import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import wildercoding.chess.*

class QueenTest : PieceTest {
    @Test
    fun Should_NotPassPiece_When_PieceIsBlockingPath() {
        val board = Board()
        val queen = Queen(Player.WHITE, board)
        val startlocation = Coord(3, 3)
        board.addPiece(queen, startlocation)
        val passingMoves = arrayOf<Coord>(
                Coord(0, 6),
                Coord(3, 7),
                Coord(7, 7),
                Coord(0, 3),
                Coord(7, 3),
                Coord(0, 0),
                Coord(3, 0),
                Coord(6, 0)
        )
        val blockingPieceLocation = arrayOf<Coord>(
                Coord(2, 4),
                Coord(3, 6),
                Coord(5, 5),
                Coord(2, 3),
                Coord(5, 3),
                Coord(1, 1),
                Coord(3, 1),
                Coord(5, 1)
        )

        // Populate the board with Blocking pieces
        for (location in blockingPieceLocation) {
            board.addPiece(Pawn(Player.WHITE), location)
        }
        // Test the passing move in all directions
        for (move in passingMoves) {
            assertFalse(queen.verifyMove(move), "Queen@: $startlocation should not pass ${board.getPiece(move)?.type}@: move ")
        }

    }

    @Test
    override fun Should_VerifyCorrectMovePattern_When_GivenAMove() {
        val board = Board()

        val legalMoves = arrayOf<Coord>(Coord(0, 1), Coord(1, 1), Coord(1, 0), Coord(7, 7), Coord(0, 7), Coord(7, 0))

        val startlocation = Coord(0, 0)
        val queen = Queen(Player.BLACK)
        board.addPiece(queen, startlocation)

        for (move in legalMoves) {
            assertTrue(queen.verifyMove(move))
        }
    }

    @Test
    override fun Should_Not_VerifyCorrectMovePattern_When_GivenAMove() {
        val board = Board()

        val illegalMoves = arrayListOf<Coord>()
        val startlocation = Coord(0, 0)
        // The queen should not be able to do knight moves
        val knight = Knight(Player.BLACK)
        knight.location = startlocation
        illegalMoves.addAll(knight.generateMovesList())


        val queen = Queen(Player.BLACK)
        board.addPiece(queen, startlocation)

        for (move in illegalMoves) {
            assertFalse(queen.verifyMove(move))
        }
    }
}