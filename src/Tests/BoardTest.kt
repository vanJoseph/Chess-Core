import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import wildercoding.chess.*

class BoardTest {

    @Test
    fun When_PieceAdded_PieceIsInSquare(){
        val board=Board()
        val pieces= arrayListOf<Piece>()
        pieces.add(Pawn(Player.WHITE))
        pieces.add(Rook(Player.BLACK))
        pieces.add(Knight(Player.WHITE))
        pieces.add(Bishop(Player.BLACK))
        pieces.add(Queen(Player.WHITE))
        pieces.add(King(Player.BLACK))

        val coords = arrayOf<Coord>(Coord(0,0), Coord(0,7), Coord(7,0), Coord(7,7), Coord(5,5), Coord(0,5))


        for (i in 0 until 6) {
            board.addPiece(pieces[i],coords[i])
        }

        for (i in 0 until 6) {
            assertTrue(board.getPiece(coords[i])==pieces[i],"\n board.getPiece= ${board.getPiece(coords[i])}\t expectedPiece=${pieces[i]}\n")
        }
    }

    @Test
    fun TestRemovePiece() {
        val board=Board()
        val piece = Pawn(Player.WHITE)
        val startPos =Coord(0,0)
        board.addPiece(piece, startPos)

        assertTrue(board.getPiece(startPos)?.type==PieceType.PAWN)

        board.removePiece(startPos)

        assertTrue(board.getPiece(startPos) == null)
    }

}