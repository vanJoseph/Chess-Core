import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import wildercoding.chess.*

class GameManagerCheckTest {
    lateinit var board: Board
    lateinit var gameManager: GameManager
    @Before
    fun setup(){
        board = Board()
        gameManager = GameManager(board)
    }

    fun cycleThruBoardCoords(assertLambda:(coord: Coord)->Unit){
        for (y in 0..7) {
            for (x in 0 .. 7){
                assertLambda(Coord(x,y))
            }
        }
    }
    @Test
    fun Should_NotCheck_When_BoardIsEmpty() {
        val assertLambda = {coord: Coord -> assertThat(gameManager.checkForCheck(coord,Color.BLACK),`is`(false))}
        cycleThruBoardCoords(assertLambda)
    }

    @Test
    fun Should_Check_When_InCheckByPawn() {
        board.addPiece(Pawn(Color.BLACK),Coord(3,2))
        val testSquare = Coord(2,1)
        assertThat(gameManager.verifyPawnCheck(testSquare,Color.BLACK),`is`(true))
    }

    @Test
    fun Should_Check_When_InCheckByKing() {
        val testSquare = Coord(3,3)
        val kingSquares = arrayOf(
                Coord(4,4),Coord(2,4),
                Coord(4,3),Coord(2,3),
                Coord(4,2),Coord(2,2),
                Coord(3,4),Coord(3,2))

        for (pos in kingSquares) {
            board.addPiece(King(Color.WHITE),pos)
            assertThat(gameManager.verifyKingCheck(testSquare,Color.WHITE), `is`(true))
            board.removePiece(pos)
        }
    }

    @Test
    fun Should_Check_When_InCheckByKnight() {
        val testSquare = Coord(3,3)
        val knightSquares = arrayOf(
                Coord(2,5),Coord(4,5),
                Coord(1,4),Coord(5,4),
                Coord(1,2),Coord(5,2),
                Coord(2,1),Coord(4,1))

        for (pos in knightSquares) {
            board.addPiece(Knight(Color.WHITE),pos)
            assertThat(gameManager.verifyKnightCheck(testSquare,Color.WHITE), `is`(true))
            board.removePiece(pos)
        }
    }

    @Test
    fun Should_Check_When_InCheckByRook() {
        val testSquare = Coord(3,3)
        val rookSquares = arrayOf(
                Coord(3,7), Coord(3,0),
                Coord(0,3), Coord(7,3))

        for (pos in rookSquares) {
            board.addPiece(Rook(Color.WHITE),pos)
            assertThat(gameManager.verifyRookCheck(testSquare,Color.WHITE), `is`(true))
            board.removePiece(pos)
        }
    }

    @Test
    fun Should_Check_When_InCheckByBishop() {
        val testSquare = Coord(3,3)
        val bishopSquares = arrayOf(
                Coord(7,7), Coord(6,0),
                Coord(0,6), Coord(0,0))

        for (pos in bishopSquares) {
            board.addPiece(Bishop(Color.WHITE),pos)
            assertThat(gameManager.verifyBishopCheck(testSquare,Color.WHITE), `is`(true))
            board.removePiece(pos)
        }
    }

    @Test
    fun Should_Check_When_InCheckByQueen() {
        val testSquare = Coord(3,3)
        val bishopSquares = arrayOf(
                Coord(7,7), Coord(6,0),
                Coord(0,6), Coord(0,0),
                Coord(3,7), Coord(3,0),
                Coord(0,3), Coord(7,3))

        for (pos in bishopSquares) {
            board.addPiece(Queen(Color.WHITE),pos)
            assertThat(gameManager.verifyQueenCheck(testSquare,Color.WHITE), `is`(true))
            board.removePiece(pos)
        }
    }

}