import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import wildercoding.chess.Board
import wildercoding.chess.Coord
import wildercoding.chess.Knight
import wildercoding.chess.Player
import kotlin.test.assertTrue

class KnightTest: PieceTest {


    @Test
    override fun Should_VerifyCorrectMovePattern_When_GivenAMove() {
        val board = Board()
        val knight = Knight(Player.WHITE)
        board.addPiece(knight, Coord(0, 0))
        printBoard(board)

        // legal moves
        val legalMove1 = Coord(2, 1)
        val legalMove2 = Coord(1, 2)
        assertTrue(knight.verifyMove(legalMove1))
        assertTrue(knight.verifyMove(legalMove2))
    }


    @Test
    override fun Should_Not_VerifyCorrectMovePattern_When_GivenAMove() {
        val board = Board()
        val knight = Knight(Player.WHITE)
        board.addPiece(knight, Coord(0, 0))
        printBoard(board)

        // legal moves
        val illegalMove1 = Coord(0, 0)
        val illegalMove2 = Coord(7, 7)
        assertFalse(knight.verifyMove(illegalMove1))
        assertFalse(knight.verifyMove(illegalMove2))
    }

    fun printBoard(board: Board) {
        println(board.toString())
    }
}