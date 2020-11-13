import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import wildercoding.chess.*

class PawnTest:PieceTest {


    @Test
    override fun Should_VerifyCorrectMovePattern_When_GivenAMove() {

        val board = Board()
        val startingPosition = Coord(0,0)
        val pawn = Pawn(Player.WHITE)
        board.addPiece(pawn, startingPosition)
        // First Move
        val legalFirstMoves =arrayOf<Coord>(Coord(0,1), Coord(0,2))
        pawn.firstMove= true   // Indicate that it is its first move

        // Test the moves
        for (move in legalFirstMoves) {
            assertTrue(pawn.verifyMove(move))
        }

        // Second Move
        val legalSecondMove = Coord(0,1)
        pawn.firstMove= false   // Indicate that it is not the pawns first move

        // Test the moves
        assertTrue(pawn.generateMovesList().size==1, "Pawn has too many possible moves")
        assertTrue(pawn.verifyMove(legalSecondMove), "Pawn isn't making the right non-first move")

    }

    override fun Should_Not_VerifyCorrectMovePattern_When_GivenAMove() {
        TODO("Not yet implemented")
    }
}