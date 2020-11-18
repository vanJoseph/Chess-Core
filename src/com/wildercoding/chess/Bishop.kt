package wildercoding.chess

class Bishop(color: Player) : Piece(PieceType.BISHOP, color) {
    constructor(color: Player, board: Board) : this(color) {
        this.board = board
    }

    var board: Board? = null
    override fun generateMovesList(): List<Coord> {
        val possibleLocations = arrayListOf<Coord?>()
        // If there is a board no passing will be permitted
        if (board == null) {

            for (i in 1..7) {
                // Add the NE Squares
                var nextCoord = Coord(location.file + i, location.rank + i)
                if (checkInbounds(nextCoord)) {
                    possibleLocations.add(nextCoord)
                }
                // Add the NW Squares
                nextCoord = Coord(location.file - i, location.rank + i)
                if (checkInbounds(nextCoord)) {
                    possibleLocations.add(nextCoord)

                }

                // Add the SE Squares
                nextCoord = Coord(location.file + i, location.rank - i)
                if (checkInbounds(nextCoord)) {
                    possibleLocations.add(nextCoord)

                }
                // Add the SW Squares
                nextCoord = Coord(location.file - i, location.rank - i)
                if (checkInbounds(nextCoord)) {
                    possibleLocations.add(nextCoord)

                }
            }
        } else {
            // Add the NE Squares
            for (i in 1..7) {
                val nextCoord = Coord(location.file + i, location.rank + i)
                if (checkInbounds(nextCoord) && board!!.getPiece(nextCoord) == null) {
                    possibleLocations.add(nextCoord)
                }else
                    break
            }
            // Add the NW Squares
            for (i in 1..7) {
                val nextCoord = Coord(location.file - i, location.rank + i)
                if (checkInbounds(nextCoord) && board!!.getPiece(nextCoord) == null) {
                    possibleLocations.add(nextCoord)
                }else
                    break
            }

            // Add the SE Squares
            for (i in 1..7) {
                val nextCoord = Coord(location.file + i, location.rank - i)
                if (checkInbounds(nextCoord) && board!!.getPiece(nextCoord) == null) {
                    possibleLocations.add(nextCoord)
                }else
                    break
            }
            // Add the SW Squares
            for (i in 1..7) {
                val nextCoord = Coord(location.file - i, location.rank - i)
                if (checkInbounds(nextCoord) && board!!.getPiece(nextCoord) == null) {
                    possibleLocations.add(nextCoord)
                }else
                    break
            }
        }

        return validateLocation(possibleLocations)
    }

    private fun checkInbounds(coord: Coord): Boolean {
        if (coord.file >= 0 || coord.file <= 7 &&
                coord.rank >= 0 || coord.rank <= 7)
            return true
        else
            return false
    }


}