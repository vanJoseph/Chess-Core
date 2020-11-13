package wildercoding.chess

class King(color:Player):Piece(PieceType.KING,color) {

    override fun generateMovesList(): List<Coord> {
        val possibleMoves = arrayListOf<Coord?>()

        possibleMoves.add(Coord(location.file+1, location.rank))
        possibleMoves.add(Coord(location.file+1, location.rank-1))
        possibleMoves.add(Coord(location.file+1, location.rank+1))

        possibleMoves.add(Coord(location.file-1, location.rank))
        possibleMoves.add(Coord(location.file-1, location.rank-1))
        possibleMoves.add(Coord(location.file-1, location.rank+1))

        possibleMoves.add(Coord(location.file, location.rank+1))
        possibleMoves.add(Coord(location.file, location.rank-1))

        return validateLocation(possibleMoves)
    }
}