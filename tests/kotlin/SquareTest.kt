import com.wildercoding.chess.pieces.King
import com.wildercoding.chess.pieces.None
import com.wildercoding.chess.pieces.Piece
import com.wildercoding.chess.units.Color
import com.wildercoding.chess.units.Coord
import com.wildercoding.chess.units.Square
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class SquareTest {
    lateinit var piece: Piece
    lateinit var square: Square

    @BeforeEach
    fun setup() {
        piece = None()
        square = Square(Coord(0,0))
    }
    @Test
    fun Should_HavePiece_When_PieceIsAdded(){
        square.addPiece(piece)

        assertTrue(square.piece === piece)
    }

    @Test
    fun Should_HaveNonePiece_When_SquareIsInstantiated() {
        assertTrue(square.piece is None)
    }

    @Test
    fun Should_ReturnFalse_When_RemovingANonePiece() {
        val results = square.removePiece()

        assertFalse(results)
    }

    @Test
    fun Should_ReturnTrue_When_RemovingANonNonePiece() {
        piece= King(Color.WHITE)
        square.addPiece(piece)
        val results = square.removePiece()
        assertTrue(results)
    }

}