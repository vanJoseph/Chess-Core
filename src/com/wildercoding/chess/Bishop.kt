package wildercoding.chess

class Bishop(color: Player) : Piece(PieceType.BISHOP, color) {

    override fun generateMovesList(): List<Coord> {
        val moveList = arrayListOf<Coord>()

        val diagonal= Array<Coord?>(4){_->null}
        for (i in 1..7) {
            // NE diagonal
            diagonal[0] = Coord(location.file + i, location.rank + i)

            // NW diagonal
            diagonal[1] = Coord(location.file - i, location.rank + i)

            // SE diagonal
            diagonal[2] = Coord(location.file + i, location.rank - i)

            // SW diagonal
            diagonal[3]= Coord(location.file - i, location.rank - i)



            for (coord in diagonal) {
                if (Coord.validateCoord(coord!!))
                    moveList.add(coord)
            }
        }
        return moveList
    }


}