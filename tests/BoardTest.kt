
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import wildercoding.chess.*

class BoardTest {
    lateinit var board:Board

    @Before
    fun setup(){
        board = Board()
    }

    fun cycleThruBoardCoords(assertLambda:(coord:Coord)->Unit){
        for (y in 0..7) {
            for (x in 0 .. 7){
                assertLambda(Coord(x,y))
            }
        }
    }

    @Test
    fun Should_RecieveTheCorrectSquare_Given_ACoord() {
        val assertLambda ={coord: Coord-> assertTrue(board.getSquare(coord).boardPostion == coord)}
        cycleThruBoardCoords(assertLambda)
    }

    @Test
    fun Should_RecieveAllNonePieces_When_BoardIsInstantiated(){
        val assertLambda= { coord: Coord-> assertTrue(board.getPiece(coord) is None )}
        cycleThruBoardCoords(assertLambda)
    }


    @Test
    fun Should_ReturnFalse_When_NonePieceIsAddedToASquareWithNonePiece(){
        val assertLambda={coord: Coord -> assertFalse( board.addPiece(None(),coord))}
        cycleThruBoardCoords(assertLambda)
    }

    @Test
    fun Should_ReturnTrue_When_ANonNonePieceIsAdded() {
        val assertLambda={coord: Coord -> assertTrue(board.addPiece(King(Color.WHITE),coord))}
        cycleThruBoardCoords(assertLambda)
    }

    @Test
    fun Should_HavePiece_When_PieceIsAdded(){
        val piece=King(Color.WHITE)
        val addPieceToSquare ={coord: Coord -> board.addPiece(piece,coord); Unit }
        cycleThruBoardCoords(addPieceToSquare)

        val assertLambda ={coord: Coord -> assertTrue(board.getPiece(coord) === piece )}
        cycleThruBoardCoords(assertLambda)
    }

    @Test
    fun Should_ReturnFalse_When_NoneIsRemoved(){
        val assertLambda ={ coord: Coord -> assertFalse(board.removePiece(coord)) }
        cycleThruBoardCoords(assertLambda)
    }

    @Test
    fun Should_ReturnTrue_When_NonNonePieceIsRemoved() {
        val piece=King(Color.WHITE)

        // Add piece to all of the squares
        val addPieceToSquare ={coord: Coord -> board.addPiece(piece,coord); Unit }
        cycleThruBoardCoords(addPieceToSquare)

        // Assert Test
        val assertLambda ={ coord: Coord -> assertTrue(board.removePiece(coord)) }
        cycleThruBoardCoords(assertLambda)
    }


    @Test
    fun Should_HaveNone_When_PieceIsRemoved() {
        val piece=King(Color.WHITE)

        // Add piece to all of the squares
        val addPieceToSquare ={coord: Coord -> board.addPiece(piece,coord); Unit }
        cycleThruBoardCoords(addPieceToSquare)

        // Remove piece from all of the squares
        val removePieceFromSquare ={coord: Coord -> board.removePiece(coord); Unit }
        cycleThruBoardCoords(removePieceFromSquare)

        // Check all of the squares for None
        val assertLambda ={coord: Coord -> assertTrue(board.getPiece(coord) is None )}
        cycleThruBoardCoords(assertLambda)
    }

    @Test
    fun Should_AllSquareAreNone_When_BoardIsCleared() {
        val piece=King(Color.WHITE)

        // Add piece to all of the squares
        val addPieceToSquare ={coord: Coord -> board.addPiece(piece,coord); Unit }
        cycleThruBoardCoords(addPieceToSquare)

        // Clear board
        board.clear()
        // Check all of the squares for None
        val assertLambda ={coord: Coord -> assertTrue(board.getPiece(coord) is None)}
        cycleThruBoardCoords(assertLambda)

    }


}