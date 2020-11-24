package wildercoding.chess

import java.text.FieldPosition

abstract class Piece(var type: PieceType, val color: Color) {
    abstract fun generateMovesList(location: Coord): List<Coord>

}