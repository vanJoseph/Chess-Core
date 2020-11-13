package wildercoding.chess

class Bishop(color:Player):Piece(PieceType.BISHOP,color) {
    override fun verifyMove(coord: Coord): Boolean {
        if (location.equals(coord)) {
            return false
        }
        for (move in generateMovesList()) {
            if (move==coord) {
                return true
            }
        }
        return false
    }

    override fun generateMovesList(): List<Coord> {
        val moveList= arrayListOf<Coord>()

        for (i in 1..7) {
            val diagonalNE = Coord(location.file+i,location.rank+i)
            val diagonalNW = Coord(location.file-i,location.rank+i)
            val diagonalSE = Coord(location.file+i,location.rank-i)
            val diagonalSW = Coord(location.file-i,location.rank-i)

            if(Coord.validateCoord(diagonalNE))
                moveList.add(diagonalNE)
            if(Coord.validateCoord(diagonalNW))
                moveList.add(diagonalNW)
            if(Coord.validateCoord(diagonalSE))
                moveList.add(diagonalSE)
            if(Coord.validateCoord(diagonalSW))
                moveList.add(diagonalSW)
        }
        return moveList
    }


}