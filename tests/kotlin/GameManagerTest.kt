import com.wildercoding.chess.StandardChessBoard
import com.wildercoding.chess.pieces.Bishop
import com.wildercoding.chess.pieces.King
import com.wildercoding.chess.pieces.Pawn
import com.wildercoding.chess.pieces.Rook
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import wildercoding.chess.*
import java.lang.RuntimeException

class GameManagerTest {
    lateinit var gameManager: GameManager
    @BeforeEach
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

    fun setupForCastling(kingSide: Boolean = true, playerTurn: Color= Color.WHITE): MoveRequest {
        val board = Board()
        gameManager = GameManager(board)
        board.addPiece(Rook(Color.WHITE),Coord(0,0))
        board.addPiece(Rook(Color.WHITE),Coord(7,0))
        board.addPiece(King(Color.WHITE),Coord(4,0))

        board.addPiece(King(Color.BLACK),Coord(4,7))
        board.addPiece(Rook(Color.BLACK),Coord(0,7))
        board.addPiece(Rook(Color.BLACK),Coord(7,7))
        when (playerTurn) {
            Color.WHITE ->{
                gameManager.playerTurn =Color.WHITE
                if(kingSide){
                    return MoveRequest(Coord(4,0), Coord(6,0))
                }else{
                    return MoveRequest(Coord(4,0), Coord(2,0))
                }
            }
            Color.BLACK ->{
                gameManager.playerTurn = Color.BLACK
                if(kingSide){
                    return MoveRequest(Coord(4,7), Coord(6,7))
                }else{
                    return MoveRequest(Coord(4,7), Coord(2,7))
                }
            }
            else -> throw RuntimeException("Unreachable")
        }
    }
    @Test
    fun Should_NotVerifyCastling_When_CastlingIsNotPosible() {
        //Using a standard Chess setup
        val moveRequest = setupForCastling()
        gameManager= GameManager(StandardChessBoard())
        assertThat(gameManager.verifyCastling(moveRequest).success,`is`(false))
    }


    @Test
    fun Should_VerifyCastling_When_FullyPossible_Kingside_White() {
        val moveRequest = setupForCastling()
        assertThat(gameManager.verifyCastleKingside(moveRequest).success, `is`(true))
    }
    @Test
    fun Should_VerifyCastling_When_FullyPossible_Kingside_Black() {
        val moveRequest = setupForCastling(true,Color.BLACK)
        assertThat(gameManager.verifyCastleKingside(moveRequest).success, `is`(true))
    }

    @Test
    fun Should_VerifyCastling_When_FullyPossible_Queenside_White() {
        val moveRequest = setupForCastling(false)
        assertThat(gameManager.verifyCastling(moveRequest).success, `is`(true))
    }
    @Test
    fun Should_VerifyCastling_When_FullyPossible_Queenside_Black() {
        val moveRequest = setupForCastling(false,Color.BLACK)
        assertThat(gameManager.verifyCastling(moveRequest).success, `is`(true))
    }
    @Test
    fun Should_NotVerifyCastling_When_KingInCheck_White() {
        val moveRequest = setupForCastling()

        //The Checking bishop
        gameManager.board.addPiece(Bishop(Color.BLACK),Coord(5,1))

        assertThat(gameManager.verifyCastling(moveRequest).success, `is`(false))
    }

    @Test
    fun Should_NotVerifyCastling_When_KingInCheck_Black() {
        val moveRequest = setupForCastling(true,Color.BLACK)

        //The Checking bishop
        gameManager.board.addPiece(Bishop(Color.WHITE),Coord(5,6))

        assertThat(gameManager.verifyCastling(moveRequest).success, `is`(false))
    }

    @Test
    fun Should_NotVerifyCastling_When_PiecesInTheWay_Kingside_White() {
        val moveRequest = setupForCastling()

        //The Checking bishop
        gameManager.board.addPiece(Bishop(Color.WHITE),Coord(5,0))

        assertThat(gameManager.verifyCastleKingside(moveRequest).success, `is`(false))
    }
    @Test
    fun Should_NotVerifyCastling_When_PiecesInTheWay_Kingside_Black() {
        val moveRequest = setupForCastling(true,Color.BLACK)

        //The Checking bishop
        gameManager.board.addPiece(Bishop(Color.BLACK),Coord(5,7))

        assertThat(gameManager.verifyCastleKingside(moveRequest).success, `is`(false))
    }

    @Test
    fun Should_NotVerifyCastling_When_PiecesInTheWay_Queenside_White() {
        val moveRequest = setupForCastling(false)

        //The Checking bishop
        gameManager.board.addPiece(Bishop(Color.WHITE),Coord(1,0))

        println(gameManager.board)
        assertThat(gameManager.verifyCastleQueenside(moveRequest).success, `is`(false))
    }
    @Test
    fun Should_NotVerifyCastling_When_PiecesInTheWay_Queenside_Black() {
        val moveRequest = setupForCastling(false,Color.BLACK)

        //The Checking bishop
        gameManager.board.addPiece(Bishop(Color.BLACK),Coord(1,7))

        assertThat(gameManager.verifyCastleQueenside(moveRequest).success, `is`(false))
    }
    @Test
    fun Should_NotVerifyCastling_When_KingMovesThruCheck_Kingside_White() {
        val moveRequest = setupForCastling()

        //The Checking bishop
        gameManager.board.addPiece(Bishop(Color.BLACK),Coord(4,2))
        println(gameManager.board)
        assertThat(gameManager.verifyCastleKingside(moveRequest).success, `is`(false))
    }

    @Test
    fun Should_NotVerifyCastling_When_KingMovesThruCheck_Kingside_Black() {
        val moveRequest = setupForCastling(true,Color.BLACK)

        //The Checking bishop
        gameManager.board.addPiece(Bishop(Color.WHITE),Coord(4,5))
        println(gameManager.board)
        assertThat(gameManager.verifyCastleKingside(moveRequest).success, `is`(false))
    }

    @Test
    fun Should_NotVerifyCastling_When_KingMovesThruCheck_Queenside_White() {
        val moveRequest = setupForCastling(false)

        //The Checking bishop
        gameManager.board.addPiece(Bishop(Color.BLACK),Coord(4,2))
        println(gameManager.board)
        assertThat(gameManager.verifyCastleQueenside(moveRequest).success, `is`(false))
    }
    @Test
    fun Should_NotVerifyCastling_When_KingMovesThruCheck_Queenside_Black() {
        val moveRequest = setupForCastling(false,Color.BLACK)

        //The Checking bishop
        gameManager.board.addPiece(Bishop(Color.WHITE),Coord(4,5))
        println(gameManager.board)
        assertThat(gameManager.verifyCastleQueenside(moveRequest).success, `is`(false))
    }

    @Test
    fun Should_NotVerifyCastling_When_NotKingFirstMove_Kingside_White() {
        val moveRequest = setupForCastling()
        val kingPos = gameManager.findKing(Color.WHITE)
        gameManager.board.getPiece(kingPos).firstMove = false
        println(gameManager.board)
        assertThat(gameManager.verifyCastleKingside(moveRequest).success, `is`(false))
    }
    @Test
    fun Should_NotVerifyCastling_When_NotKingFirstMove_Kingside_Black() {
        val moveRequest = setupForCastling(true, Color.BLACK)
        val kingPos = gameManager.findKing(Color.BLACK)
        gameManager.board.getPiece(kingPos).firstMove = false
        println(gameManager.board)
        assertThat(gameManager.verifyCastleKingside(moveRequest).success, `is`(false))
    }

    @Test
    fun Should_NotVerifyCastling_When_NotKingFirstMove_Queenside_White() {
        val moveRequest = setupForCastling(false)
        val kingPos = gameManager.findKing(Color.WHITE)
        gameManager.board.getPiece(kingPos).firstMove = false
        println(gameManager.board)
        assertThat(gameManager.verifyCastleQueenside(moveRequest).success, `is`(false))
    }
    @Test
    fun Should_NotVerifyCastling_When_NotKingFirstMove_Queenside_Black() {
        val moveRequest = setupForCastling(false, Color.BLACK)
        val kingPos = gameManager.findKing(Color.BLACK)
        gameManager.board.getPiece(kingPos).firstMove = false
        println(gameManager.board)
        assertThat(gameManager.verifyCastleQueenside(moveRequest).success, `is`(false))
    }


    @Test
    fun Should_NotVerifyCastling_When_NotRookFirstMove_Kingside_White() {
        val moveRequest = setupForCastling()
        val rookPos = Coord(7,moveRequest.fromPos.rank)
        gameManager.board.getPiece(rookPos).firstMove = false
        println(gameManager.board)
        assertThat(gameManager.verifyCastleKingside(moveRequest).success, `is`(false))
    }
    @Test
    fun Should_NotVerifyCastling_When_NotRookFirstMove_Kingside_Black() {
        val moveRequest = setupForCastling(true, Color.BLACK)
        val rookPos = Coord(7,moveRequest.fromPos.rank)
        gameManager.board.getPiece(rookPos).firstMove = false
        println(gameManager.board)
        assertThat(gameManager.verifyCastleKingside(moveRequest).success, `is`(false))
    }

    @Test
    fun Should_NotVerifyCastling_When_NotRookFirstMove_Queenside_White() {
        val moveRequest = setupForCastling(false)
        val rookPos =  Coord(0,moveRequest.fromPos.rank)
        gameManager.board.getPiece(rookPos).firstMove = false
        println(gameManager.board)
        assertThat(gameManager.verifyCastleQueenside(moveRequest).success, `is`(false))
    }
    @Test
    fun Should_NotVerifyCastling_When_NotRookFirstMove_Queenside_Black() {
        val moveRequest = setupForCastling(false, Color.BLACK)
        val rookPos = Coord(0,moveRequest.fromPos.rank)
        gameManager.board.getPiece(rookPos).firstMove = false
        println(gameManager.board)
        assertThat(gameManager.verifyCastleQueenside(moveRequest).success, `is`(false))
    }

    @Test
    fun Should_ValidateCastling() {
        val moveRequest = setupForCastling()

        assertThat(gameManager.validateMove(moveRequest), `is`(MoveInfo(true,MoveType.CASTLE_KINGSIDE)))
    }
    @Test
    fun Should_NotValidateCastling() {
        val moveRequest = setupForCastling()
        gameManager = GameManager(StandardChessBoard())
        assertThat(gameManager.validateMove(moveRequest).success, `is`(false))
    }

    @Test
    fun Should_ExecuteCastling_When_Kingside_White() {
        val moveRequest = setupForCastling()
        val moveInfo = gameManager.executeMove(moveRequest)

        assertThat(moveInfo.moveType, `is`(MoveType.CASTLE_KINGSIDE))
    }
    @Test
    fun Should_ExecuteCastling_When_Kingside_Black() {
        val moveRequest = setupForCastling(true,Color.BLACK)
        val moveInfo = gameManager.executeMove(moveRequest)

        assertThat(moveInfo.moveType, `is`(MoveType.CASTLE_KINGSIDE))
    }
    @Test
    fun Should_ExecuteCastling_When_Queenside_White() {
        val moveRequest = setupForCastling(false)
        val moveInfo = gameManager.executeMove(moveRequest)

        assertThat(moveInfo.moveType, `is`(MoveType.CASTLE_QUEENSIDE))
    }
    @Test
    fun Should_ExecuteCastling_When_Queenside_Black() {
        val moveRequest = setupForCastling(false,Color.BLACK)
        val moveInfo = gameManager.executeMove(moveRequest)

        assertThat(moveInfo.moveType, `is`(MoveType.CASTLE_QUEENSIDE))
    }

    @Test
    fun Should_MovePieces_When_Castling_Kingside_White() {
        gameManager = GameManager(Board())
        gameManager.board.addPiece(King(Color.WHITE), Coord(4, 0))
        gameManager.board.addPiece(Rook(Color.WHITE), Coord(7, 0))


        gameManager.castlePieces(MoveInfo(true,MoveType.CASTLE_KINGSIDE))
        val kingEndPos = Coord(6, 0)
        val rookEndPos = Coord(5,0)
        println(gameManager.board)
        assertThat(gameManager.board.getPiece(kingEndPos).type, `is`(PieceType.KING))
        assertThat(gameManager.board.getPiece(rookEndPos).type,  `is`(PieceType.ROOK))
    }
    @Test
    fun Should_MovePieces_When_Castling_Kingside_Black() {
        gameManager = GameManager(Board())
        gameManager.playerTurn = Color.BLACK
        gameManager.board.addPiece(King(Color.BLACK), Coord(4, 7))
        gameManager.board.addPiece(Rook(Color.BLACK), Coord(7, 7))


        gameManager.castlePieces(MoveInfo(true,MoveType.CASTLE_KINGSIDE))
        val kingEndPos = Coord(6, 7)
        val rookEndPos = Coord(5,7)
        println(gameManager.board)
        assertThat(gameManager.board.getPiece(kingEndPos).type, `is`(PieceType.KING))
        assertThat(gameManager.board.getPiece(rookEndPos).type,  `is`(PieceType.ROOK))
    }

    @Test
    fun Should_MovePieces_When_Castling_Queenside_White() {
        gameManager = GameManager(Board())
        gameManager.board.addPiece(King(Color.WHITE), Coord(4, 0))
        gameManager.board.addPiece(Rook(Color.WHITE), Coord(0, 0))


        gameManager.castlePieces(MoveInfo(true,MoveType.CASTLE_QUEENSIDE))
        val kingEndPos = Coord(2, 0)
        val rookEndPos = Coord(3,0)
        println(gameManager.board)
        assertThat(gameManager.board.getPiece(kingEndPos).type, `is`(PieceType.KING))
        assertThat(gameManager.board.getPiece(rookEndPos).type,  `is`(PieceType.ROOK))
    }

    @Test
    fun Should_MovePieces_When_Castling_Queenside_Black() {
        gameManager = GameManager(Board())
        gameManager.playerTurn = Color.BLACK
        gameManager.board.addPiece(King(Color.BLACK), Coord(4, 7))
        gameManager.board.addPiece(Rook(Color.BLACK), Coord(0, 7))


        gameManager.castlePieces(MoveInfo(true,MoveType.CASTLE_QUEENSIDE))
        val kingEndPos = Coord(2, 7)
        val rookEndPos = Coord(3,7)
        println(gameManager.board)
        assertThat(gameManager.board.getPiece(kingEndPos).type, `is`(PieceType.KING))
        assertThat(gameManager.board.getPiece(rookEndPos).type,  `is`(PieceType.ROOK))
    }

    @Test
    fun Should_Castle_When_Kingside_White() {
        val moveRequest = setupForCastling()
        val moveInfo = gameManager.executeMove(moveRequest)
        val kingEndPos = Coord(6, 0)
        val rookEndPos = Coord(5,0)
        println(gameManager.board)

        assertThat(moveInfo.success,`is`(true))
        assertThat(gameManager.board.getPiece(kingEndPos).type, `is`(PieceType.KING))
        assertThat(gameManager.board.getPiece(rookEndPos).type,  `is`(PieceType.ROOK))
    }

    @Test
    fun Should_Castle_When_Kingside_Black() {
        val moveRequest = setupForCastling(true,Color.BLACK)
        val moveInfo = gameManager.executeMove(moveRequest)
        val kingEndPos = Coord(6, 7)
        val rookEndPos = Coord(5,7)
        println(gameManager.board)

        assertThat(moveInfo.success,`is`(true))
        assertThat(gameManager.board.getPiece(kingEndPos).type, `is`(PieceType.KING))
        assertThat(gameManager.board.getPiece(rookEndPos).type,  `is`(PieceType.ROOK))
    }

    @Test
    fun Should_Castle_When_Queenside_White() {
        val moveRequest = setupForCastling(false)
        val moveInfo = gameManager.executeMove(moveRequest)
        val kingEndPos = Coord(2,0)
        val rookEndPos = Coord(3,0)
        println(gameManager.board)

        assertThat(moveInfo.success,`is`(true))
        assertThat(gameManager.board.getPiece(kingEndPos).type, `is`(PieceType.KING))
        assertThat(gameManager.board.getPiece(rookEndPos).type,  `is`(PieceType.ROOK))
    }

    @Test
    fun Should_Castle_When_Queenside_Black() {
        val moveRequest = setupForCastling(false, Color.BLACK)
        val moveInfo = gameManager.executeMove(moveRequest)
        val kingEndPos = Coord(2,7)
        val rookEndPos = Coord(3,7)
        println(gameManager.board)

        assertThat(moveInfo.success,`is`(true))
        assertThat(gameManager.board.getPiece(kingEndPos).type, `is`(PieceType.KING))
        assertThat(gameManager.board.getPiece(rookEndPos).type,  `is`(PieceType.ROOK))
    }



    @Test
    fun Should_NotCastle_When_Kingside_White() {
        val moveRequest = setupForCastling()
        gameManager.board.addPiece(Bishop(Color.BLACK), Coord(4,1))
        val moveInfo = gameManager.executeMove(moveRequest)
        println(gameManager.board)

        assertThat(moveInfo.success,`is`(false))
    }
    @Test
    fun Should_NotCastle_When_Kingside_Black() {
        val moveRequest = setupForCastling(true,Color.BLACK)
        gameManager.board.addPiece(Bishop(Color.WHITE), Coord(4,6))
        val moveInfo = gameManager.executeMove(moveRequest)
        println(gameManager.board)

        assertThat(moveInfo.success,`is`(false))
    }

    @Test
    fun Should_NotCastle_When_Queenside_White() {
        val moveRequest = setupForCastling(false)
        gameManager.board.addPiece(Bishop(Color.BLACK), Coord(4,1))
        val moveInfo = gameManager.executeMove(moveRequest)
        println(gameManager.board)

        assertThat(moveInfo.success,`is`(false))
    }
    @Test
    fun Should_NotCastle_When_Queenside_Black() {
        val moveRequest = setupForCastling(false,Color.BLACK)
        gameManager.board.addPiece(Bishop(Color.WHITE), Coord(4,6))
        val moveInfo = gameManager.executeMove(moveRequest)
        println(gameManager.board)

        assertThat(moveInfo.success,`is`(false))
    }
}