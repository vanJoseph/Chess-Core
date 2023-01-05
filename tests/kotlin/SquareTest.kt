import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import wildercoding.chess.*


class SquareTest {
    lateinit var piece: Piece
    lateinit var square: Square

    @Before
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