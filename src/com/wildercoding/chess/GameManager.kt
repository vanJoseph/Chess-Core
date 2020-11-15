package wildercoding.chess

class GameManager(val inputMethod: InputMethod) {
    var moveNumber = 0
        private set
    var playerTurn = Player.WHITE
    val board = Board()
    val movesMade = arrayListOf<MoveRequest>()

    /**
     * Execute one move for the correct player
     *
     * @return 1 if successful and -1 if there was a problem with validation

     */
    fun executeMove(): Int {
        val moveRequest = inputMethod.getMove(GameInfo(playerTurn))
        if (validate(moveRequest)) {
            movesMade.add(moveRequest)
            moveNumber++
            changeTurn()
            return 1
        } else
            return -1
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
                val pieceType= getPieceTypeFromMoveRequest(moveRequest)
                val requestPiece = Piece.spawnPiece(pieceType ?: return false)
                requestPiece.location = moveRequest.fromPos
                if (!requestPiece.verifyMove(moveRequest.toPos))
                    return false
                else
                    moveRequest.pieceValidation = true
            }
        }

        return true
    }
    fun getPieceTypeFromMoveRequest(moveRequest: MoveRequest):PieceType?{
        return board.getPiece(moveRequest.fromPos)?.type
    }
}