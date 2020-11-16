package wildercoding.chess

class GameManager(val board:Board, val inputMethod: InputMethod, val outputMethod: OutputMethod) {
    var moveNumber = 0
    var playerTurn = Player.WHITE
    val moveLog = arrayListOf<MoveRequest>()
    var gameState=GameState.NEW

    fun start() {
        while(gameState==GameState.NEW) {
            displayGame()
            executeMove()
        }
    }

    fun displayGame() {
        outputMethod.display(GameInfo(playerTurn,board))
    }
    /**
     * Execute one move for the correct player
     * Todo Change the game state
     *
     * @return 1 if successful and -1 if there was a problem with validation

     */
    fun executeMove(): Boolean {
        val moveRequest = inputMethod.getMove(GameInfo(playerTurn,board))
        if (validate(moveRequest)) {
            moveLog.add(moveRequest)
            moveNumber++
            changeTurn()
            return true
        } else
            return false
    }


    private fun changeTurn() {
        if (playerTurn == Player.WHITE) playerTurn = Player.BLACK else playerTurn = Player.WHITE
    }

    /**
     * Validate piece moves, game move, and execute the move
     *
     */
    fun validate(moveRequest: MoveRequest): Boolean {

        when (moveRequest.moveType) {
            MoveType.MOVE -> {
                val piece= board.getPiece(moveRequest.fromPos)
                // Verification that the piece being moved is the current player's piece
                if (piece?.color!= playerTurn )
                    return false
                // Verification that the moveTo square is not ocupied

                // Verification that the piece can move to the location
                val pieceType = getPieceTypeFromMoveRequest(moveRequest)
                val requestPiece = Piece.spawnPiece(pieceType ?: return false)
                requestPiece.location = moveRequest.fromPos
                if (!requestPiece.verifyMove(moveRequest.toPos))
                    return false
                else {
                    moveRequest.pieceValidation = true
                }
            }
        }

        // Actually move the piece
        movePiece(moveRequest.fromPos,moveRequest.toPos)
        return true
    }

    /**
     * Moves the piece from @param fromPos to @param toPos nullify the fromPos
     */
    private fun movePiece(fromPos:Coord, toPos:Coord){
        // get the piece
        val piece= board.getPiece(fromPos)!!
        // null out the fromPos
        board.removePiece(fromPos)
        // move piece to toPos
        board.addPiece(piece,toPos)
    }
    fun getPieceTypeFromMoveRequest(moveRequest: MoveRequest): PieceType? {
        return board.getPiece(moveRequest.fromPos)?.type
    }
}