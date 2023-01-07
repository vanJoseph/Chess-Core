package wildercoding.chess

import com.wildercoding.chess.pieces.Piece

class None() : Piece(PieceType.NONE, Color.NONE) {
    override fun generateMovesList(position: Coord): List<Coord> = listOf()
    override fun verifyMove(board: Board, fromPos: Coord, toPos: Coord): Boolean = false
    override fun verifyTake(board: Board, fromPos: Coord, toPos: Coord): Boolean = false
}