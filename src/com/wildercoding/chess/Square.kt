package wildercoding.chess

class Square(val file: Int, val rank: Int) {
    var boardPosition = Coord(file, rank)
    var piece: Piece? = null
        set(value) {
            if (value != null)
                value.location = boardPosition // Assigns the square boardPosition to the piece location

            field = value
        }

    override fun toString(): String {
        val pieceString = piece?.type?.getTypeName() ?: "None"
        return "$pieceString:$boardPosition"
    }
}