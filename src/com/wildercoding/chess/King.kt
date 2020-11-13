package wildercoding.chess

class King(color:Player):Piece(PieceType.KING,color) {
    override fun verifyMove(coord: Coord): Boolean {
        val filterdMoves = generateMovesList()
        for (move in filterdMoves) {
            if(move == coord)
                return true
        }
        return false
    }

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

        for ((index,move) in possibleMoves.withIndex()) {
            if(!Coord.validateCoord(move!!))
                possibleMoves[index]=null
        }
        return possibleMoves.filterNotNull()
    }
}