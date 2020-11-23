package wildercoding.chess

class None() : Piece(PieceType.NONE,Color.NONE) {
    override fun generateMovesList(position: Coord): List<Coord> = listOf()
    override fun generateTakeList(): List<Coord> = listOf()
    override fun verifyTake(): Boolean = false
}