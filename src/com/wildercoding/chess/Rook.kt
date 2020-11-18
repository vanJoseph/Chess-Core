package wildercoding.chess

class Rook(color: Player) : Piece(PieceType.ROOK, color) {
    constructor(color: Player, board: Board) : this(color) {
        this.board = board
    }

    var board: Board? = null

    override fun generateMovesList(): List<Coord> {

        val possibleLocations = arrayListOf<Coord?>()

        // If there is a board no passing will be permitted
        if (board==null) {
            // Get the North Squares
            for (rank in location.rank..7) {
                possibleLocations.add(Coord(location.file, rank))
            }
            // Get the South Squares
            for (rank in location.rank downTo 0) {
                possibleLocations.add(Coord(location.file, rank))
            }
            // Get the East Squares
            for (file in location.file..7) {
                possibleLocations.add(Coord(file, location.rank))
            }
            // Get the West Squares
            for (file in location.file downTo 0) {
                possibleLocations.add(Coord(file, location.rank))
            }
        }else {
            // Get the North Squares
            for (rank in location.rank..7) {
                if (board!!.getPiece(location.file,rank)==null) {
                    possibleLocations.add(Coord(location.file, rank))
                }else{
                    break
                }
            }
            // Get the South Squares
            for (rank in location.rank downTo 0) {
                if (board!!.getPiece(location.file,rank)==null) {
                    possibleLocations.add(Coord(location.file, rank))
                }else{
                    break
                }
            }
            // Get the East Squares
            for (file in location.file..7) {
                if (board!!.getPiece(file,location.rank)==null) {
                    possibleLocations.add(Coord(file, location.rank))
                }else{
                    break
                }
            }
            // Get the West Squares
            for (file in location.file downTo 0) {
                if (board!!.getPiece(file,location.rank)==null) {
                    possibleLocations.add(Coord(file, location.rank))
                }else{
                    break
                }
            }
        }
        return validateLocation(possibleLocations)
    }


}