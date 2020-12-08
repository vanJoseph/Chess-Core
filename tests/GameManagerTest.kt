import com.sun.source.tree.WhileLoopTree
import com.wildercoding.chess.StandardChessBoard
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import wildercoding.chess.*

class GameManagerTest {
    lateinit var gameManager: GameManager
    @Before
    fun setup() {
        gameManager= GameManager(StandardChessBoard())
    }


    @Test
    fun Should_MoveInfoFail_When_IsPlayableIsFalse() {
        gameManager.isPlayable=false
        val moveRequest= MoveRequest(Coord(4,1), Coord(4,3))
        val moveInfo = gameManager.executeMove(moveRequest)
        assertFalse(moveInfo.success)
    }


    @Test
    fun Should_BeBlackTurn_When_AfterWhiteMoved() {
        gameManager.playerTurn=Color.WHITE
        val moveRequest= MoveRequest(Coord(4,1), Coord(4,3))
        val moveInfo = gameManager.executeMove(moveRequest)
        assertTrue(gameManager.playerTurn==Color.BLACK)
    }
    @Test
    fun Should_BeWhiteTurn_When_AfterBlackMoved() {
        gameManager.playerTurn=Color.BLACK
        val moveRequest= MoveRequest(Coord(4,6), Coord(4,4))
        val moveInfo = gameManager.executeMove(moveRequest)
        assertTrue(gameManager.playerTurn==Color.WHITE )
    }


    @Test
    fun Should_MoveInfoFail_When_MovingANonePiece() {
        gameManager.playerTurn=Color.WHITE
        val moveRequest= MoveRequest(Coord(4,5), Coord(4,4))
        val moveInfo = gameManager.executeMove(moveRequest)
        assertFalse(moveInfo.success)
    }
    @Test
    fun Should_MoveInfoFail_When_PlayerTryToMoveOppositeColor() {
        gameManager.playerTurn=Color.WHITE
        var moveRequest= MoveRequest(Coord(4,6), Coord(4,4))
        var moveInfo = gameManager.executeMove(moveRequest)
        assertFalse(moveInfo.success)

        gameManager.playerTurn=Color.BLACK
        moveRequest= MoveRequest(Coord(4,1), Coord(4,2))
        moveInfo = gameManager.executeMove(moveRequest)
        assertFalse(moveInfo.success)
    }

    @Test
    fun Should_AddMoveRequestToLog_When_MoveInfoSucceed() {
        gameManager.playerTurn=Color.WHITE
        val moveRequest= MoveRequest(Coord(4,1), Coord(4,3))

        // Before Move
        assertTrue(gameManager.moveLog.size == 0)
        val moveInfo = gameManager.executeMove(moveRequest)
        // After Move
        assertTrue(gameManager.moveLog.size == 1)
    }

    @Test
    fun Should_NotMoveToASquare_When_SamePlayerPieceOccupies() {
        gameManager.playerTurn=Color.WHITE
        val occupiedSquare =  Coord(4,3)
        val moveRequest= MoveRequest(Coord(4,1),occupiedSquare)
        gameManager.board.addPiece(Pawn(Color.WHITE),occupiedSquare)
        val moveInfo= gameManager.executeMove(moveRequest)
        assertFalse(moveInfo.success)
    }

    @Test
    fun Should_UpdateFirstMove_When_PieceMoves() {

        val moveTo = Coord(3, 3)
        val moveRequest = MoveRequest(Coord(3, 1), moveTo)

        gameManager.executeMove(moveRequest)
        val movedPiece= gameManager.board.getPiece(moveTo)
        assertThat(movedPiece.firstMove, `is`(false))
    }

    @Test
    fun Should_FirstMoveTrue_When_PieceHasNotMoved() {

        for (y in 0..7) {
            for (x in 0..7) {
                val coord = Coord(x, y)
                val firstMove = gameManager.board.getPiece(coord).firstMove
                assertThat(firstMove, `is`(true))
            }
        }
    }

}