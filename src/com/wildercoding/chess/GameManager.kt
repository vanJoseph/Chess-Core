package wildercoding.chess

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


    fun checkForCheck(coord: Coord, fromColor: Color): Boolean {
        if (verifyPawnCheck(coord, fromColor)){
            return true
        }
        if (verifyKingCheck(coord, fromColor)){
            return true
        }
        if (verifyKnightCheck(coord, fromColor)){
            return true
        }
        if (verifyRookCheck(coord, fromColor)) {
            return true
        }
        return false
    }

    private fun verifyPawnCheck(coord: Coord, fromColor: Color): Boolean {
        val colorMod = if (fromColor == Color.WHITE) -1 else 1
        val possiblePawnLoc = arrayOf(
                Coord.getValidatedCoord(coord.file - 1, coord.rank + colorMod),
                Coord.getValidatedCoord(coord.file + 1, coord.rank + colorMod))
        for (possiblePosition in possiblePawnLoc.filterNotNull()) {
            if (board.getPiece(possiblePosition) is Pawn &&
                    board.getPiece(possiblePosition).color == fromColor){
                return true
            }
        }
        return false
    }
    private fun verifyKingCheck(coord: Coord, fromColor: Color): Boolean {
        val possibleKingLoc = arrayOf(
                Coord.getValidatedCoord(coord.file+1,coord.rank+1),Coord.getValidatedCoord(coord.file-1,coord.rank+1),
                Coord.getValidatedCoord(coord.file+1,coord.rank),Coord.getValidatedCoord(coord.file-1,coord.rank),
                Coord.getValidatedCoord(coord.file+1,coord.rank-1),Coord.getValidatedCoord(coord.file-1,coord.rank-1),
                Coord.getValidatedCoord(coord.file,coord.rank+1),Coord.getValidatedCoord(coord.file,coord.rank-1))
        for (loc in possibleKingLoc.filterNotNull()) {
            if(board.getPiece(loc) is King &&
                    board.getPiece(loc).color == fromColor){
                return true
            }
        }
        return false
    }

    private fun verifyKnightCheck(coord: Coord, fromColor: Color): Boolean {
        val possibleKnightLoc = arrayOf(
                Coord.getValidatedCoord(coord.file+2,coord.rank+1),Coord.getValidatedCoord(coord.file-2,coord.rank+1),
                Coord.getValidatedCoord(coord.file+2,coord.rank-1),Coord.getValidatedCoord(coord.file-2,coord.rank-1),
                Coord.getValidatedCoord(coord.file+1,coord.rank+2),Coord.getValidatedCoord(coord.file-1,coord.rank-2),
                Coord.getValidatedCoord(coord.file-1,coord.rank+2),Coord.getValidatedCoord(coord.file+1,coord.rank-2))
        for (loc in possibleKnightLoc.filterNotNull()) {
            if(board.getPiece(loc) is Knight &&
                    board.getPiece(loc).color == fromColor){
                return true
            }
        }
        return false
    }
    private fun verifyRookCheck(coord: Coord, fromColor: Color): Boolean {

        if(     verifyCheckNorth(coord,fromColor)||
                verifyCheckSouth(coord,fromColor)||
                verifyCheckWest(coord,fromColor)||
                verifyCheckEast(coord,fromColor)){
            return true
        }
        return false
    }
    private fun verifyCheckNorth(coord: Coord, fromColor: Color): Boolean {
        for (rank in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file,coord.rank+rank)
            val piece = board.getPiece(tempCoord ?: return false )
            if(piece is None){
                continue
            }
            if(piece !is Rook && piece.color ==fromColor ||
                    piece!is Queen && piece.color == fromColor){
                return true

            }else{
                break
            }
        }
        return false
    }

    private fun verifyCheckSouth(coord: Coord, fromColor: Color): Boolean {
        for (rank in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file,coord.rank-rank)
            val piece = board.getPiece(tempCoord ?: return false )
            if(piece is None){
                continue
            }
            if(piece !is Rook && piece.color ==fromColor ||
                    piece!is Queen && piece.color == fromColor){
                return true

            }else{
                break
            }
        }
        return false
    }

    private fun verifyCheckWest(coord: Coord, fromColor: Color): Boolean {
        for (file in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file-file,coord.rank)
            val piece = board.getPiece(tempCoord ?: return false )
            if(piece is None){
                continue
            }
            if(piece !is Rook && piece.color ==fromColor ||
                    piece!is Queen && piece.color == fromColor){
                return true

            }else{
                break
            }
        }
        return false
    }

    private fun verifyCheckEast(coord: Coord,fromColor: Color): Boolean {
        for (file in 1..7) {
            val tempCoord = Coord.getValidatedCoord(coord.file+file,coord.rank)
            val piece = board.getPiece(tempCoord ?: return false )
            if(piece is None){
                continue
            }
            if(piece !is Rook && piece.color ==fromColor ||
                    piece!is Queen && piece.color == fromColor){
                return true

            }else{
                break
            }
        }
        return false
    }

}