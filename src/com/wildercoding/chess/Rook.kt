package wildercoding.chess

class Rook(color: Color) : Piece(PieceType.ROOK, color) {

    override fun generateMovesList(coord: Coord): List<Coord> {
        val possibleLocations = arrayListOf<Coord>()

        possibleLocations.addAll(generateNorthMoveList(coord))
        possibleLocations.addAll(generateSouthMoveList(coord))
        possibleLocations.addAll(generateWestMoveList(coord))
        possibleLocations.addAll(generateEastMoveList(coord))

        return possibleLocations
    }

    override fun verifyMove(board: Board, fromPos: Coord, toPos: Coord): Boolean {
        TODO("Not yet implemented")
    }

    override fun verifyTake(board: Board, fromPos: Coord, toPos: Coord): Boolean {
        TODO("Not yet implemented")
    }

    fun generateNorthMoveList(coord: Coord): List<Coord> {
        val possibleLocations = arrayListOf<Coord>()
        for (rank in coord.rank + 1..7) {
            possibleLocations.add(Coord(coord.file, rank))
        }
        return possibleLocations
    }

    fun generateSouthMoveList(coord: Coord): List<Coord> {
        val possibleLocations = arrayListOf<Coord>()
        for (rank in coord.rank - 1 downTo 0) {
            possibleLocations.add(Coord(coord.file, rank))
        }
        return possibleLocations
    }

    fun generateWestMoveList(coord: Coord): List<Coord> {
        val possibleLocations = arrayListOf<Coord>()
        for (file in coord.file - 1 downTo 0) {
            possibleLocations.add(Coord(file, coord.rank))
        }
        return possibleLocations
    }

    fun generateEastMoveList(coord: Coord): List<Coord> {
        val possibleLocations = arrayListOf<Coord>()
        for (file in coord.file + 1..7) {
            possibleLocations.add(Coord(file, coord.rank))
        }
        return possibleLocations
    }
}