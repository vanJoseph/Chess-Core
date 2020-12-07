import org.junit.Before
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsCollectionContaining.hasItem
import wildercoding.chess.*

class GameManagerCheckmate {

    lateinit var gameManager: GameManager
    lateinit var board: Board
    @Before
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
}