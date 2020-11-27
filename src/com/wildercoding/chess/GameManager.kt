package wildercoding.chess

import java.util.function.ToDoubleBiFunction

class GameManager(val board:Board) {
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
        if (moveLog.size<1) {
            outputMethod.display(playerTurn, isPlayable, isInCheck, null, board)
        }
        val moveRequest = inputMethod.getMove()
        val moveInfo = executeMove(moveRequest)
        outputMethod.display(playerTurn,isPlayable,isInCheck,moveInfo,board)

    }
    fun updateBoard(moveRequest: MoveRequest) {
        val piece =  board.getPiece(moveRequest.fromPos)
        board.removePiece(moveRequest.fromPos)
        board.addPiece(piece,moveRequest.toPos)
    }


    fun validateMove(moveRequest: MoveRequest): MoveInfo {
        val piece = board.getPiece(moveRequest.fromPos)
        if (!isPlayable){ // Check to see if the game is not finished
            return MoveInfo(false,"Not Playable")
        }
        if (piece is None){// Check to see if the piece being moved is a moveable piece
            return MoveInfo(false, "There is no piece to move")
        }
        if(piece.color != playerTurn){ //Check to see that the player is moving their piece
            return MoveInfo(false, "Can not move other color piece")
        }
        if(board.getPiece(moveRequest.toPos).color == playerTurn){ // Check to see if a player is trying to take their own piece
            return MoveInfo(false, "You can not take your own piece")
        }else{
            if(piece.verifyTake(board,moveRequest.fromPos,moveRequest.toPos)){ //Verify that a take is possible
                return MoveInfo(true)
            }
        }

        if(piece.verifyMove(board,moveRequest.fromPos,moveRequest.toPos)){
            return MoveInfo(true)
        }

        return MoveInfo(false)
    }

    fun changeTurns() {
        if (playerTurn==Color.BLACK)
            playerTurn=Color.WHITE
        else
            playerTurn=Color.BLACK
    }

}