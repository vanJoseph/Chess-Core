package wildercoding.chess

class Queen(color: Player):Piece(PieceType.QUEEN,color) {
    override fun verifyMove(coord: Coord): Boolean {
        if (location ==coord )
            return false

        for (move in generateMovesList()) {
            if(coord == move)
                return true
        }
        return false
    }

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