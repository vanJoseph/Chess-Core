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
            getNorthMoveRange(possibleLocations)
            // Get the South Squares
            getSouthMoveRange(possibleLocations)
            // Get the East Squares
            getEastMoveRange(possibleLocations)
            // Get the West Squares
            getWestMoveRange(possibleLocations)
        }else {
            // Get the North Squares
            getNorthMoves(possibleLocations)
            // Get the South Squares
            getSouthMoves(possibleLocations)
            // Get the East Squares
            getEastMoves(possibleLocations)
            // Get the West Squares
            getWestMoves(possibleLocations)
        }
        return validateLocation(possibleLocations)
    }

    private fun getNorthMoveRange(possibleLocations: ArrayList<Coord?>) {
        for (rank in location.rank..7) {
            possibleLocations.add(Coord(location.file, rank))
        }
    }

    private fun getSouthMoveRange(possibleLocations: ArrayList<Coord?>) {
        for (rank in location.rank downTo 0) {
            possibleLocations.add(Coord(location.file, rank))
        }
    }

    private fun getEastMoveRange(possibleLocations: ArrayList<Coord?>) {
        for (file in location.file..7) {
            possibleLocations.add(Coord(file, location.rank))
        }
    }

    private fun getWestMoveRange(possibleLocations: ArrayList<Coord?>) {
        for (file in location.file downTo 0) {
            possibleLocations.add(Coord(file, location.rank))
        }
    }

    private fun getNorthMoves(possibleLocations: ArrayList<Coord?>) {
        for (rank in location.rank..7) {
            if (board!!.getPiece(location.file, rank) == null) {
                possibleLocations.add(Coord(location.file, rank))
            } else {
                break
            }
        }
    }

    private fun getSouthMoves(possibleLocations: ArrayList<Coord?>) {
        for (rank in location.rank downTo 0) {
            if (board!!.getPiece(location.file, rank) == null) {
                possibleLocations.add(Coord(location.file, rank))
            } else {
                break
            }
        }
    }

    private fun getEastMoves(possibleLocations: ArrayList<Coord?>) {
        for (file in location.file..7) {
            if (board!!.getPiece(file, location.rank) == null) {
                possibleLocations.add(Coord(file, location.rank))
            } else {
                break
            }
        }
    }

    private fun getWestMoves(possibleLocations: ArrayList<Coord?>) {
        for (file in location.file downTo 0) {
            if (board!!.getPiece(file, location.rank) == null) {
                possibleLocations.add(Coord(file, location.rank))
            } else {
                break
            }
        }
    }

    override fun generateTakeList(): List<Coord> {

    }

    override fun verifyTake(coord: Coord): Boolean {
        TODO("Not yet implemented")
    }


}