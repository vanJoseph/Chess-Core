package wildercoding.chess

import com.wildercoding.chess.NoKingFoundException
import java.lang.RuntimeException
import kotlin.reflect.KClass
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf

class GameManager(val board: Board) {
    var playerTurn = Color.WHITE
    val moveLog = arrayListOf<MoveRequest>()
    var isPlayable = true
    var isInCheck = false

    /**
     * Execute one move for the correct player
     * Todo Change the game state
     *
     * @return 1 if successful and -1 if there was a problem with validation

     */
    fun executeMove(moveRequest: MoveRequest): MoveInfo {

        val moveInfo = validateMove(moveRequest)

        // Responsible for changing and logging Moves
        if (moveInfo.success) {
            updateBoard(moveRequest)
            moveLog.add(moveRequest)
            changeTurns()
        }
        return moveInfo
    }

    fun executeMove(inputMethod: InputMethod, outputMethod: OutputMethod) {
        if (moveLog.size < 1) {
            outputMethod.display(playerTurn, isPlayable, isInCheck, null, board)
        }
        val moveRequest = inputMethod.getMove()
        val moveInfo = executeMove(moveRequest)
        outputMethod.display(playerTurn, isPlayable, isInCheck, moveInfo, board)

    }

    fun updateBoard(moveRequest: MoveRequest) {
        val piece = board.getPiece(moveRequest.fromPos)
        board.removePiece(moveRequest.fromPos)
        board.addPiece(piece, moveRequest.toPos)
    }


    fun validateMove(moveRequest: MoveRequest): MoveInfo {
        val piece = board.getPiece(moveRequest.fromPos)
        if (!isPlayable) { // Check to see if the game is not finished
            return MoveInfo(false, "Not Playable")
        }
        if (piece is None) {// Check to see if the piece being moved is a moveable piece
            return MoveInfo(false, "There is no piece to move")
        }
        if (piece.color != playerTurn) { //Check to see that the player is moving their piece
            return MoveInfo(false, "Can not move other color piece")
        }
        if (board.getPiece(moveRequest.toPos).color == playerTurn) { // Check to see if a player is trying to take their own piece
            return MoveInfo(false, "You can not take your own piece")
        } else {
            if (piece.verifyTake(board, moveRequest.fromPos, moveRequest.toPos)) { //Verify that a take is possible
                return MoveInfo(true)
            }
        }

        if (piece.verifyMove(board, moveRequest.fromPos, moveRequest.toPos)) {
            return MoveInfo(true)
        }

        return MoveInfo(false)
    }

    fun changeTurns() {
        if (playerTurn == Color.BLACK)
            playerTurn = Color.WHITE
        else
            playerTurn = Color.BLACK
    }


    fun checkSquareCheck(coord: Coord, fromColor: Color): Array<Coord> {
        val checks = arrayListOf<Coord?>()
        checks.addAll(verifyPawnCheck(coord, fromColor))
        checks.addAll(verifyKnightCheck(coord, fromColor))
        checks.add(verifyKingCheck(coord,fromColor)?:null)
        checks.addAll(verifyRookCheck(coord, fromColor))
        checks.addAll(verifyBishopCheck(coord, fromColor))
        checks.addAll(verifyQueenCheck(coord, fromColor))
        return checks.filterNotNull().toTypedArray()
    }

    fun verifyPawnCheck(coord: Coord, fromColor: Color): Array<Coord> {
        val colorMod = if (fromColor == Color.WHITE) -1 else 1
        val possiblePawnLoc = arrayOf(
                Coord.getValidatedCoord(coord.file - 1, coord.rank + colorMod),
                Coord.getValidatedCoord(coord.file + 1, coord.rank + colorMod))
        val checkfromPawns = arrayListOf<Coord>()
        for (possiblePosition in possiblePawnLoc.filterNotNull()) {
            if (board.getPiece(possiblePosition) is Pawn &&
                    board.getPiece(possiblePosition).color == fromColor) {
                checkfromPawns.add(possiblePosition)
            }
        }
        return checkfromPawns.toTypedArray()
    }

    fun verifyKingCheck(coord: Coord, fromColor: Color): Coord? {
        val possibleKingLoc = arrayOf(
                Coord.getValidatedCoord(coord.file + 1, coord.rank + 1), Coord.getValidatedCoord(coord.file - 1, coord.rank + 1),
                Coord.getValidatedCoord(coord.file + 1, coord.rank), Coord.getValidatedCoord(coord.file - 1, coord.rank),
                Coord.getValidatedCoord(coord.file + 1, coord.rank - 1), Coord.getValidatedCoord(coord.file - 1, coord.rank - 1),
                Coord.getValidatedCoord(coord.file, coord.rank + 1), Coord.getValidatedCoord(coord.file, coord.rank - 1))
        for (loc in possibleKingLoc.filterNotNull()) {
            if (board.getPiece(loc) is King &&
                    board.getPiece(loc).color == fromColor) {
                return loc
            }
        }
        return null
    }

    fun verifyKnightCheck(coord: Coord, fromColor: Color): Array<Coord> {
        val possibleKnightLoc = arrayOf(
                Coord.getValidatedCoord(coord.file + 2, coord.rank + 1), Coord.getValidatedCoord(coord.file - 2, coord.rank + 1),
                Coord.getValidatedCoord(coord.file + 2, coord.rank - 1), Coord.getValidatedCoord(coord.file - 2, coord.rank - 1),
                Coord.getValidatedCoord(coord.file + 1, coord.rank + 2), Coord.getValidatedCoord(coord.file - 1, coord.rank - 2),
                Coord.getValidatedCoord(coord.file - 1, coord.rank + 2), Coord.getValidatedCoord(coord.file + 1, coord.rank - 2))
        val checkPieces = arrayListOf<Coord>()
        for (loc in possibleKnightLoc.filterNotNull()) {
            if (board.getPiece(loc) is Knight &&
                    board.getPiece(loc).color == fromColor) {
                checkPieces.add(loc)
            }
        }
        return checkPieces.toTypedArray()
    }

    fun verifyRookCheck(coord: Coord, fromColor: Color): Array<Coord> {

        val checkPieces = arrayListOf<Coord?>()
        checkPieces.add(verifyCheckNorth(coord, fromColor, PieceType.ROOK) ?: null)
        checkPieces.add(verifyCheckSouth(coord, fromColor, PieceType.ROOK) ?: null)
        checkPieces.add(verifyCheckWest(coord, fromColor, PieceType.ROOK) ?: null)
        checkPieces.add(verifyCheckEast(coord, fromColor, PieceType.ROOK) ?: null)

        return checkPieces.filterNotNull().toTypedArray()
    }

    fun verifyBishopCheck(coord: Coord, fromColor: Color): Array<Coord> {
        val checkPieces = arrayListOf<Coord?>()
        checkPieces.add(verifyCheckNe(coord, fromColor, PieceType.BISHOP) ?: null)
        checkPieces.add(verifyCheckSe(coord, fromColor, PieceType.BISHOP) ?: null)
        checkPieces.add(verifyCheckNw(coord, fromColor, PieceType.BISHOP) ?: null)
        checkPieces.add(verifyCheckSw(coord, fromColor, PieceType.BISHOP) ?: null)
        return checkPieces.filterNotNull().toTypedArray()
    }

    fun verifyQueenCheck(coord: Coord, fromColor: Color): Array<Coord> {


        val checkPieces = arrayListOf<Coord?>()
        checkPieces.add(verifyCheckNorth(coord, fromColor, PieceType.QUEEN) ?: null)
        checkPieces.add(verifyCheckSouth(coord, fromColor, PieceType.QUEEN) ?: null)
        checkPieces.add(verifyCheckWest(coord, fromColor, PieceType.QUEEN) ?: null)
        checkPieces.add(verifyCheckEast(coord, fromColor, PieceType.QUEEN) ?: null)
        checkPieces.add(verifyCheckNe(coord, fromColor, PieceType.QUEEN) ?: null)
        checkPieces.add(verifyCheckSe(coord, fromColor, PieceType.QUEEN) ?: null)
        checkPieces.add(verifyCheckNw(coord, fromColor, PieceType.QUEEN) ?: null)
        checkPieces.add(verifyCheckSw(coord, fromColor, PieceType.QUEEN) ?: null)
        return checkPieces.filterNotNull().toTypedArray()
    }


    fun verifyCheckNe(coord: Coord, fromColor: Color, pieceType: PieceType): Coord? {
        if (!(pieceType==PieceType.QUEEN|| pieceType== PieceType.BISHOP)){
            return null
        }
        for (i in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file + i, coord.rank + i)
            val piece = board.getPiece(tempCoord ?: return null)
            if (piece is None) {
                continue
            }
            if (piece.type ==pieceType && piece.color == fromColor) {
                return tempCoord

            } else {
                break
            }
        }
        return null
    }

    fun verifyCheckSe(coord: Coord, fromColor: Color, pieceType: PieceType): Coord? {
        if (!(pieceType==PieceType.QUEEN|| pieceType== PieceType.BISHOP)){
            return null
        }
        for (i in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file + i, coord.rank - i)
            val piece = board.getPiece(tempCoord ?: return null)
            if (piece is None) {
                continue
            }
            if (piece.type ==pieceType && piece.color == fromColor) {
                return tempCoord

            } else {
                break
            }
        }
        return null
    }

    fun verifyCheckNw(coord: Coord, fromColor: Color, pieceType: PieceType): Coord? {
        if (!(pieceType==PieceType.QUEEN|| pieceType== PieceType.BISHOP)){
            return null
        }
        for (i in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file - i, coord.rank + i)
            val piece = board.getPiece(tempCoord ?: return null)
            if (piece is None) {
                continue
            }
            if (piece.type ==pieceType && piece.color == fromColor ) {
                return tempCoord

            } else {
                break
            }
        }
        return null
    }

    fun verifyCheckSw(coord: Coord, fromColor: Color, pieceType: PieceType): Coord? {
        if (!(pieceType==PieceType.QUEEN|| pieceType== PieceType.BISHOP)){
            return null
        }
        for (i in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file - i, coord.rank - i)
            val piece = board.getPiece(tempCoord ?: return null)
            if (piece is None) {
                continue
            }
            if (piece.type ==pieceType && piece.color == fromColor) {
                return tempCoord

            } else {
                break
            }
        }
        return null
    }

    private fun verifyCheckNorth(coord: Coord, fromColor: Color, pieceType: PieceType): Coord? {
        if (!(pieceType==PieceType.QUEEN|| pieceType== PieceType.ROOK)){
            return null
        }
        for (rank in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file, coord.rank + rank)
            val piece = board.getPiece(tempCoord ?: return null)
            if (piece is None) {
                continue
            }
            if (piece.type ==pieceType && piece.color == fromColor) {
                return tempCoord

            } else {
                break
            }
        }
        return null
    }

    private fun verifyCheckSouth(coord: Coord, fromColor: Color, pieceType: PieceType): Coord? {
        if (!(pieceType==PieceType.QUEEN|| pieceType== PieceType.ROOK)){
            return null
        }
        for (rank in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file, coord.rank - rank)
            val piece = board.getPiece(tempCoord ?: return null)
            if (piece is None) {
                continue
            }
            if (piece.type ==pieceType && piece.color == fromColor) {
                return tempCoord
            } else {
                break
            }
        }
        return null
    }

    private fun verifyCheckWest(coord: Coord, fromColor: Color, pieceType: PieceType): Coord? {
        if (!(pieceType==PieceType.QUEEN|| pieceType== PieceType.ROOK)){
            return null
        }
        for (file in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file - file, coord.rank)
            val piece = board.getPiece(tempCoord ?: return null)
            if (piece is None) {
                continue
            }
            if (piece.type ==pieceType && piece.color == fromColor) {
                return tempCoord

            } else {
                break
            }
        }
        return null
    }

    private fun verifyCheckEast(coord: Coord, fromColor: Color, pieceType: PieceType): Coord? {
        if (!(pieceType==PieceType.QUEEN|| pieceType== PieceType.ROOK)){
            return null
        }
        for (file in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file + file, coord.rank)
            val piece = board.getPiece(tempCoord ?: return null)
            if (piece is None) {
                continue
            }
            if (piece.type ==pieceType  && piece.color == fromColor) {
                return tempCoord

            } else {
                break
            }
        }
        return null
    }

    fun findKing(color: Color): Coord {
        for (y in 0..7) {
            for (x in 0..7) {
                val coord = Coord(x, y)
                if(board.getPiece(coord) is King && board.getPiece(coord). color ==color)
                    return coord
            }
        }
        throw NoKingFoundException()
    }
}