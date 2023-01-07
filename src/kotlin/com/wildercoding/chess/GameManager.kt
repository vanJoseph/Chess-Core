package wildercoding.chess

import com.wildercoding.chess.MultiMap
import com.wildercoding.chess.exceptions.NoKingFoundException
import com.wildercoding.chess.iomethods.InputMethod
import com.wildercoding.chess.iomethods.OutputMethod
import com.wildercoding.chess.pieces.King
import com.wildercoding.chess.pieces.Knight
import com.wildercoding.chess.pieces.None
import com.wildercoding.chess.pieces.Pawn

class GameManager(val board: Board) {
    var playerTurn = Color.WHITE
    var opponentColor: Color = Color.BLACK
        get() {
            return if (playerTurn == Color.BLACK) Color.WHITE else Color.BLACK
        }
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
        val piece = board.getPiece(moveRequest.fromPos)
        if (!moveInfo.success){
            return moveInfo
        }
        // Responsible for changing and logging Moves
        when(moveInfo.moveType) {
            MoveType.MOVE -> {
                movePieces(moveRequest)
                moveLog.add(moveRequest)// update the piece that moved first move
                changeTurns()
            }
            MoveType.CASTLE_KINGSIDE,MoveType.CASTLE_QUEENSIDE ->{ //Castling
                val moveInfo = verifyCastling(moveRequest)
                if(moveInfo.success) {
                    castlePieces(moveInfo)
                    moveLog.add(moveRequest)
                    changeTurns()
                }else{
                    return moveInfo
                }
            }

            else -> {}
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

    fun movePieces(moveRequest: MoveRequest) {
        val piece = board.getPiece(moveRequest.fromPos)
        piece.firstMove=false
        board.removePiece(moveRequest.fromPos)
        board.addPiece(piece, moveRequest.toPos)
    }

    fun castlePieces(moveInfo: MoveInfo){
        val player = if(playerTurn == Color.WHITE)0 else 7
        when (moveInfo.moveType) {
             MoveType.CASTLE_KINGSIDE-> {
                 val kingFromPos = Coord(4, player)
                 val kingToPos = Coord(6,player)
                 val kingMoveRequest = MoveRequest(kingFromPos, kingToPos)
                 val rookFromPos = Coord(7, player)
                 val rookToPos = Coord(5, player)
                 val rookMoveRequest = MoveRequest( rookFromPos, rookToPos)
                 movePieces(kingMoveRequest)
                 movePieces(rookMoveRequest)
            }
            MoveType.CASTLE_QUEENSIDE ->{
                val kingFromPos = Coord(4, player)
                val kingToPos = Coord(2,player)
                val kingMoveRequest = MoveRequest(kingFromPos, kingToPos)
                val rookFromPos = Coord(0, player)
                val rookToPos = Coord(3, player)
                val rookMoveRequest = MoveRequest( rookFromPos, rookToPos)
                movePieces(kingMoveRequest)
                movePieces(rookMoveRequest)
            }

            else -> {}
        }
    }

    fun validateMove(moveRequest: MoveRequest): MoveInfo {
        val piece = board.getPiece(moveRequest.fromPos)
        // Validation for Rules
        if (!isPlayable) { // Check to see if the game is not finished
            return MoveInfo(false, "Not Playable")
        }
        if (piece is None) {// Check to see if the piece being moved is a moveable piece
            return MoveInfo(false, "There is no piece to move")
        }
        if (piece.color != playerTurn) { //Check to see that the player is moving their piece
            return MoveInfo(false, "Can not move other color piece")
        }


        // Validation for Takes
        if (board.getPiece(moveRequest.toPos).color == playerTurn) { // Check to see if a player is trying to take their own piece
            return MoveInfo(false, "You can not take your own piece")
        } else {
            if (piece.verifyTake(board, moveRequest.fromPos, moveRequest.toPos)) { //Verify that a take is possible
                return MoveInfo(true)
            }
        }

        // Validation for Castling
        if(piece is King && piece.verifyCastling(moveRequest) ){
            if (moveRequest.toPos.file> moveRequest.fromPos.file) {
                return MoveInfo(true, MoveType.CASTLE_KINGSIDE)
            }else{
                return MoveInfo(true, MoveType.CASTLE_QUEENSIDE)
            }
        }
        // Validation for Movement
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
        checks.add(verifyKingCheck(coord, fromColor) ?: null)
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
        if (!(pieceType == PieceType.QUEEN || pieceType == PieceType.BISHOP)) {
            return null
        }
        for (i in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file + i, coord.rank + i)
            val piece = board.getPiece(tempCoord ?: return null)
            if (piece is None) {
                continue
            }
            if (piece.type == pieceType && piece.color == fromColor) {
                return tempCoord

            } else {
                break
            }
        }
        return null
    }

    fun verifyCheckSe(coord: Coord, fromColor: Color, pieceType: PieceType): Coord? {
        if (!(pieceType == PieceType.QUEEN || pieceType == PieceType.BISHOP)) {
            return null
        }
        for (i in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file + i, coord.rank - i)
            val piece = board.getPiece(tempCoord ?: return null)
            if (piece is None) {
                continue
            }
            if (piece.type == pieceType && piece.color == fromColor) {
                return tempCoord

            } else {
                break
            }
        }
        return null
    }

    fun verifyCheckNw(coord: Coord, fromColor: Color, pieceType: PieceType): Coord? {
        if (!(pieceType == PieceType.QUEEN || pieceType == PieceType.BISHOP)) {
            return null
        }
        for (i in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file - i, coord.rank + i)
            val piece = board.getPiece(tempCoord ?: return null)
            if (piece is None) {
                continue
            }
            if (piece.type == pieceType && piece.color == fromColor) {
                return tempCoord

            } else {
                break
            }
        }
        return null
    }

    fun verifyCheckSw(coord: Coord, fromColor: Color, pieceType: PieceType): Coord? {
        if (!(pieceType == PieceType.QUEEN || pieceType == PieceType.BISHOP)) {
            return null
        }
        for (i in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file - i, coord.rank - i)
            val piece = board.getPiece(tempCoord ?: return null)
            if (piece is None) {
                continue
            }
            if (piece.type == pieceType && piece.color == fromColor) {
                return tempCoord

            } else {
                break
            }
        }
        return null
    }

    private fun verifyCheckNorth(coord: Coord, fromColor: Color, pieceType: PieceType): Coord? {
        if (!(pieceType == PieceType.QUEEN || pieceType == PieceType.ROOK)) {
            return null
        }
        for (rank in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file, coord.rank + rank)
            val piece = board.getPiece(tempCoord ?: return null)
            if (piece is None) {
                continue
            }
            if (piece.type == pieceType && piece.color == fromColor) {
                return tempCoord

            } else {
                break
            }
        }
        return null
    }

    private fun verifyCheckSouth(coord: Coord, fromColor: Color, pieceType: PieceType): Coord? {
        if (!(pieceType == PieceType.QUEEN || pieceType == PieceType.ROOK)) {
            return null
        }
        for (rank in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file, coord.rank - rank)
            val piece = board.getPiece(tempCoord ?: return null)
            if (piece is None) {
                continue
            }
            if (piece.type == pieceType && piece.color == fromColor) {
                return tempCoord
            } else {
                break
            }
        }
        return null
    }

    private fun verifyCheckWest(coord: Coord, fromColor: Color, pieceType: PieceType): Coord? {
        if (!(pieceType == PieceType.QUEEN || pieceType == PieceType.ROOK)) {
            return null
        }
        for (file in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file - file, coord.rank)
            val piece = board.getPiece(tempCoord ?: return null)
            if (piece is None) {
                continue
            }
            if (piece.type == pieceType && piece.color == fromColor) {
                return tempCoord

            } else {
                break
            }
        }
        return null
    }

    private fun verifyCheckEast(coord: Coord, fromColor: Color, pieceType: PieceType): Coord? {
        if (!(pieceType == PieceType.QUEEN || pieceType == PieceType.ROOK)) {
            return null
        }
        for (file in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file + file, coord.rank)
            val piece = board.getPiece(tempCoord ?: return null)
            if (piece is None) {
                continue
            }
            if (piece.type == pieceType && piece.color == fromColor) {
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
                if (board.getPiece(coord) is King && board.getPiece(coord).color == color)
                    return coord
            }
        }
        throw NoKingFoundException()
    }

    /**
     * Finds all the pieces that can capture the piece that is checking the current player king
     */
    fun getCheckReliefPiece(): Map<Coord, Coord> {
        val reliefMap = mutableMapOf<Coord, Coord>()
        val kingPos = findKing(playerTurn)
        val opponentColor = if (playerTurn == Color.BLACK) Color.WHITE else Color.BLACK
        val checkingPieces = checkSquareCheck(kingPos, opponentColor)
        for (checkingPiece in checkingPieces) {
            for (reliefPiece in checkSquareCheck(checkingPieces.get(0), playerTurn)) {
                reliefMap.put(checkingPiece, reliefPiece)
            }
        }
        return reliefMap
    }

    /**
     * Simulates a move if it results in a check it will return false
     */
    fun simulateCheckRelief(moveRequest: MoveRequest): Boolean {
        if (!validateMove(moveRequest).success)
            return false

        // Make the Simulated Move
        val piece = board.getPiece(moveRequest.fromPos)
        val removedPiece = board.getPiece(moveRequest.toPos)
        board.removePiece(moveRequest.fromPos)
        board.addPiece(piece, moveRequest.toPos)
        // Check for Check allieviation
        val kingPos = findKing(playerTurn)
        val opponentColor = if (playerTurn == Color.BLACK) Color.WHITE else Color.BLACK
        val relief = checkSquareCheck(kingPos, opponentColor).isEmpty()
        // Reverse the Simulated Move
        board.addPiece(piece, moveRequest.fromPos)
        board.removePiece(moveRequest.toPos)
        board.addPiece(removedPiece, moveRequest.toPos)

        return relief
    }

    fun checkForKingCheck(color: Color): Boolean {
        val kingPos = findKing(color)
        val opponentColor = if (playerTurn == Color.BLACK) Color.WHITE else Color.BLACK
        if (!checkSquareCheck(kingPos, opponentColor).isEmpty()) {
            return true
        }

        return false
    }

    /**
     * return all the postions of non check squares
     */
    fun canMoveOutofCheck(): Array<Coord> {
        val kingPos = findKing(playerTurn)
        val opponentColor = if (playerTurn == Color.BLACK) Color.WHITE else Color.BLACK
        val king = board.getPiece(kingPos)
        val validatedSquares = arrayListOf<Coord>()

        val potentialMoves = king.generateMovesList(kingPos)
        for (square in potentialMoves) {
            val moveInfo = validateMove(MoveRequest(kingPos, square))
            if (moveInfo.success) {
                val checksOnSquare = checkSquareCheck(square, opponentColor)
                if (checksOnSquare.isEmpty()) {
                    val moveRequest = MoveRequest(kingPos, square)
                    if (simulateCheckRelief(moveRequest)) {
                        validatedSquares.add(square)
                    }
                }
            }
        }
//        king.generateMovesList(kingPos).forEach{
//            val moveInfo = validateMove(MoveRequest(kingPos,it))
//            if(moveInfo.success && checkSquareCheck(it,opponentColor).isEmpty()){
//                validatedSquares.add(it)
//            }
//        }
        return validatedSquares.toTypedArray()
    }

    fun canBlockCheck(): MultiMap<Coord, Coord> {
        val kingPos = findKing(playerTurn)
        val opponentColor = if (playerTurn == Color.BLACK) Color.WHITE else Color.BLACK
        val attackingPiecePos = checkSquareCheck(kingPos, opponentColor)
        val attackingPiece = board.getPiece(attackingPiecePos[0])
        val attackRange = attackingPiece.generateMovesList(attackingPiecePos[0])

        val blockingPieces = MultiMap<Coord, Coord>()
        for (square in attackRange) {
            val checkPieces = checkSquareCheck(square, playerTurn)
            for (piece in checkPieces) {
                val simMoveRequest = MoveRequest(piece, square)
                if (simulateCheckRelief(simMoveRequest)) {
                    blockingPieces.put(piece, square)
                }
            }
        }

        return blockingPieces
    }


    fun checkForCheckmate(): Boolean {
        //Check that the king is in check
        val kingPos = findKing(playerTurn)
        if (!checkForKingCheck(playerTurn)) {
            return false
        }
        //Check that the king can not move out of check
        if (!canMoveOutofCheck().isEmpty()) {
            return false
        }
        // verify that a peice can not block the checking pieces
        if (!canBlockCheck().isEmpty)
            return false
        return true
    }

    fun hasLegalMove(coord: Coord): Boolean {
        val piece = board.getPiece(coord)
        val moves = piece.generateMovesList(coord)
        for (move in moves) {

            val moveRequest = MoveRequest(coord, move)
            if (simulateCheckRelief(moveRequest)) {
                return true
            }
        }
        return false
    }

    fun checkForLegalMoves(color: Color): Boolean {
        val piecePositions = arrayListOf<Coord>()

        // Gather all the pieces for the color
        for (y in 0..7) {
            for (x in 0..7) {
                val coord = Coord(x, y)
                val piece = board.getPiece(coord)

                if (piece.color == color) {
                    piecePositions.add(coord)
                }

            }
        }

        // Check to see if each one has a legal move
        // return after the first legal move found

        piecePositions.forEach {
            if (hasLegalMove(it)) {
                return true
            }
        }
        return false
    }

    fun checkForStalemate(): Boolean {
        if (!checkForKingCheck(playerTurn) &&
                !checkForLegalMoves(playerTurn)) {
            return true
        }
        return false
    }

    fun verifyCastling(moveRequest: MoveRequest): MoveInfo {

        if (checkForKingCheck(playerTurn)) {
            return MoveInfo(false, "Player is in Check")
        }

        var moveInfo: MoveInfo
        if (moveRequest.toPos.file > moveRequest.fromPos.file) {
            moveInfo = verifyCastleKingside(moveRequest)
        } else {
            moveInfo = verifyCastleQueenside(moveRequest)
        }
        if (!moveInfo.success) {
            return moveInfo
        }
        return moveInfo
    }

    fun verifyCastleKingside(moveRequest: MoveRequest): MoveInfo {

        //Check to see that it is the kings's first move
        val kingPos = findKing(playerTurn)
        if (!board.getPiece(kingPos).firstMove) {
            return MoveInfo(false, "the King has move already")
        }
        // Check to see that the Rook hasn't moved
        val rookPos =Coord(7,moveRequest.fromPos.rank)
        if (!board.getPiece(rookPos).firstMove) {
            return MoveInfo(false, "the Rook has move already")
        }

        // Check to see that to see that there are no pieces in between
        // And that no square in between is under attack
        val checkfiles = arrayOf(5, 6)
        for (file in checkfiles) {
            val tempCoord = Coord(file, moveRequest.fromPos.rank)
            if (board.getPiece(tempCoord) !is None) {
                return MoveInfo(false, "Piece is Blocking")
            }
            if (!checkSquareCheck(tempCoord, opponentColor).isEmpty()) {
                return MoveInfo(false, "Can not move thru check")
            }
        }
        return MoveInfo(true,MoveType.CASTLE_KINGSIDE)
    }

    fun verifyCastleQueenside(moveRequest: MoveRequest): MoveInfo {

        //Check to see that the
        //Check to see that it is the kings's first move
        val kingPos = findKing(playerTurn)
        if (!board.getPiece(kingPos).firstMove) {
            return MoveInfo(false, "the King has move already")
        }


        // Check to see that the Rook hasn't moved
        val rookPos =Coord(0,moveRequest.fromPos.rank)
        if (!board.getPiece(rookPos).firstMove) {
            return MoveInfo(false, "the Rook has move already")
        }
        // Check to see that to see that there are no pieces in between
        // And that no square in between is under attack
        val checkfiles = arrayOf(3, 2, 1)
        for (file in checkfiles) {
            val tempCoord = Coord(file, moveRequest.fromPos.rank)
            if (board.getPiece(tempCoord) !is None) {
                return MoveInfo(false, "Piece is Blocking")
            }
            if (!checkSquareCheck(tempCoord, opponentColor).isEmpty()) {
                return MoveInfo(false, "Can not move thru check")
            }
        }
        return MoveInfo(true, MoveType.CASTLE_QUEENSIDE)
    }

}