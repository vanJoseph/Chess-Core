package wildercoding.chess

import java.text.FieldPosition

abstract class Piece(val type: PieceType, val color: Color) {
    var firstMove = true
    abstract fun generateMovesList(location: Coord): List<Coord>
    /**
     * Checks to make sure that there is no pieces between the fromPos and the toPos
     */
    abstract fun verifyMove(board: Board, fromPos:Coord, toPos:Coord): Boolean
    abstract fun verifyTake(board: Board, fromPos:Coord, toPos: Coord): Boolean

}