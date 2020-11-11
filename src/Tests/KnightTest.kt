
import org.junit.jupiter.api.Test
import wildercoding.chess.Board
import wildercoding.chess.Coord
import wildercoding.chess.Knight
import wildercoding.chess.Player
import kotlin.test.assertTrue

class KnightTest {


    @Test
    fun Should_MoveLegal_When_PossibleMoves() {
        val board = Board()
        val knight=Knight(Player.WHITE)
        board.add(knight, Coord(0, 0))
        printBoard(board)

        // legal moves
        val move1=Coord(2,1)
        val move2=Coord(1,2)
        assertTrue(knight.verifyMove(move1))

        printBoard(board)
    }
    
    fun printBoard(board: Board) {
        println(board.toString())
    }
}