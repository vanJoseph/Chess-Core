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
    fun executeMove(): MoveInfo {
        val moveRequest = inputMethod.getMove(GameInfo(playerTurn,board))
        val moveInfo=validate(moveRequest)
        if (moveInfo.success) {
            moveLog.add(moveRequest)
            moveNumber++
            changeTurn()
            return moveInfo
        } else
            return moveInfo
    }


    private fun changeTurn() {
        if (playerTurn == Player.WHITE) playerTurn = Player.BLACK else playerTurn = Player.WHITE
    }

    /**
     * Validate piece moves, game move, and execute the move
     *
     */
    fun validate(moveRequest: MoveRequest): MoveInfo {

        when (moveRequest.moveType) {
            MoveType.MOVE -> {
                val piece= board.getPiece(moveRequest.fromPos)
                // Verification that the piece being moved is the current player's piece
                if (piece?.color!= playerTurn )
                    return MoveInfo(false,false,null,null)
                // Verification that the moveTo square is not ocupied
                if (board.getPiece(moveRequest.toPos)!=null)
                    return MoveInfo(false,true,false,null)
                // Verification that the piece can move to the location
                val pieceType = getPieceTypeFromMoveRequest(moveRequest)
                if (!piece.verifyMove(moveRequest.toPos))
                    return MoveInfo(false,true,true,false )
                else {
                    moveRequest.pieceValidation = true
                }
            }
        }

        // Actually move the piece
        movePiece(moveRequest.fromPos,moveRequest.toPos)
        return MoveInfo(true,true,true,true)
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