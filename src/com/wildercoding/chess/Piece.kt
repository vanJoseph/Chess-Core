package wildercoding.chess

import java.text.FieldPosition

abstract class Piece(val type: PieceType, val color: Color) {
    abstract fun generateMovesList(location: Coord): List<Coord>
    abstract fun verifyMove(board: Board, fromPos:Coord, toPos:Coord): Boolean
    abstract fun verifyTake(board: Board, fromPos:Coord, toPos: Coord): Boolean

}