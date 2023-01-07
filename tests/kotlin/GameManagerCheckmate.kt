
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import wildercoding.chess.*

class GameManagerCheckmate {

    lateinit var gameManager: GameManager
    lateinit var board: Board
    @BeforeEach
    fun setup() {
        board = Board()
        gameManager = GameManager(board)
    }
    @Test
    fun Should_DeclareCheckmate() {
        board.addPiece(King(Color.WHITE), Coord(3, 0))
        board.addPiece(Rook(Color.BLACK), Coord(0,0))
        board.addPiece(Rook(Color.BLACK), Coord(0,1))

        assertThat(gameManager.checkForCheckmate(),`is`(true))
    }
    @Test
    fun Should_NotDeclareCheckmate() {
        board.addPiece(King(Color.WHITE), Coord(3, 0))
        board.addPiece(Rook(Color.BLACK), Coord(0,0))

        assertThat(gameManager.checkForCheckmate(),`is`(false))
    }

    @Test
    fun Should_NotHaveLegalMove_When_KingIsAlone() {
        val kingPos = Coord(0, 0)
        board.addPiece(King(Color.WHITE), kingPos)
        board.addPiece(King(Color.BLACK), Coord(7, 7))
        board.addPiece(Rook(Color.BLACK), Coord(1, 7))
        board.addPiece(Rook(Color.BLACK), Coord(7, 1))

        assertThat(gameManager.hasLegalMove(kingPos), `is`(false))
    }


    @Test
    fun Should_HaveLegalMove_When_KingIsAlone() {
        val kingPos = Coord(0, 0)
        board.addPiece(King(Color.WHITE), kingPos)
        board.addPiece(King(Color.BLACK), Coord(7, 7))
        board.addPiece(Rook(Color.BLACK), Coord(1, 7))
        board.addPiece(Rook(Color.BLACK), Coord(7, 2))

        println(board)
        assertThat(gameManager.hasLegalMove(kingPos), `is`(true))
    }

    @Test
    fun Should_HaveLegalMove_When_KingPinnedWithAnotherPiece() {
        val kingPos = Coord(0, 0)
        board.addPiece(King(Color.WHITE), kingPos)
        board.addPiece(King(Color.BLACK), Coord(7, 7))
        board.addPiece(Rook(Color.BLACK), Coord(1, 7))
        board.addPiece(Rook(Color.BLACK), Coord(7, 1))
        board.addPiece(Pawn(Color.WHITE), Coord(4,4))

        println(board)
        assertThat(gameManager.checkForLegalMoves(Color.WHITE), `is`(true))
    }
    @Test
    fun Should_NotHaveLegalMove_When_KingIsWithAnotherPiece() {
        val kingPos = Coord(0, 0)
        board.addPiece(King(Color.WHITE), kingPos)
        board.addPiece(King(Color.BLACK), Coord(7, 7))
        board.addPiece(Rook(Color.BLACK), Coord(1, 7))
        board.addPiece(Rook(Color.BLACK), Coord(7, 1))
        board.addPiece(Pawn(Color.WHITE), Coord(7, 0))

        println(board)

        assertThat(gameManager.checkForLegalMoves(Color.WHITE), `is`(false))
    }

    @Test
    fun Should_NotStalemate_When_GameIsPlayable() {
        val kingPos = Coord(0, 0)
        board.addPiece(King(Color.WHITE), kingPos)
        board.addPiece(King(Color.BLACK), Coord(7, 7))
        board.addPiece(Rook(Color.BLACK), Coord(1, 7))
        board.addPiece(Pawn(Color.WHITE), Coord(7, 0))

        println(board)

        assertThat(gameManager.checkForStalemate(), `is`(false))
    }
    @Test
    fun Should_Stalemate() {
        val kingPos = Coord(0, 0)
        board.addPiece(King(Color.WHITE), kingPos)
        board.addPiece(King(Color.BLACK), Coord(7, 7))
        board.addPiece(Rook(Color.BLACK), Coord(1, 7))
        board.addPiece(Rook(Color.BLACK), Coord(7, 1))
        board.addPiece(Pawn(Color.WHITE), Coord(7, 0))

        println(board)

        assertThat(gameManager.checkForStalemate(), `is`(true))
    }
}