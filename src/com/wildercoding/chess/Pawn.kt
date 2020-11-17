package wildercoding.chess

class Pawn(color: Player) : Piece(PieceType.PAWN, color) {
    var firstMove = true

    override fun generateMovesList(): List<Coord> {
        val possibleMoves = arrayListOf<Coord?>()
        when (firstMove) {
            true -> {
                if (color == Player.BLACK) {
                    possibleMoves.add(Coord(location.file, location.rank - 1))
                    possibleMoves.add(Coord(location.file, location.rank - 2))
                } else {
                    possibleMoves.add(Coord(location.file, location.rank + 1))
                    possibleMoves.add(Coord(location.file, location.rank + 2))
                }
            }
            false -> {
                if (color == Player.BLACK)
                    possibleMoves.add(Coord(location.file, location.rank - 1))
                else
                    possibleMoves.add(Coord(location.file, location.rank + 1))
            }
        }

        return validateLocation(possibleMoves)
    }


}