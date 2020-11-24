package wildercoding.chess

class None() : Piece(PieceType.NONE,Color.NONE) {
    override fun generateMovesList(position: Coord): List<Coord> = listOf()
}