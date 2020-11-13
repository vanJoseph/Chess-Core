package wildercoding.chess

class Queen(color: Player):Piece(PieceType.QUEEN,color) {

    override fun generateMovesList(): List<Coord> {
        val possibleMoves = arrayListOf<Coord>()
        val rook = Rook(Player.BLACK)
        rook.location = location
        val bishop = Bishop(Player.BLACK)
        bishop.location=location
        possibleMoves.addAll(rook.generateMovesList())
        possibleMoves.addAll(bishop.generateMovesList())
        return possibleMoves
    }
}