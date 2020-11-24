package wildercoding.chess

class Queen(color: Color) : Piece(PieceType.QUEEN, color) {

    override fun generateMovesList(coord: Coord): List<Coord> {
        val possibleLocations = arrayListOf<Coord>()

        possibleLocations.addAll(generateNorthMoveList(coord))
        possibleLocations.addAll(generateSouthMoveList(coord))
        possibleLocations.addAll(generateWestMoveList(coord))
        possibleLocations.addAll(generateEastMoveList(coord))
        possibleLocations.addAll(generateNeMoveList(coord))
        possibleLocations.addAll(generateNwMoveList(coord))
        possibleLocations.addAll(generateSeMoveList(coord))
        possibleLocations.addAll(generateSwMoveList(coord))

        return possibleLocations
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
    fun generateNeMoveList(coord: Coord): List<Coord> {
        val possibleLocations = arrayListOf<Coord>()
        for (i in 1..7) {
            val nextCoord = Coord.getValidatedCoord(coord.file + i, coord.rank + i)
            possibleLocations.add(nextCoord ?:break)
        }
        return possibleLocations
    }

    fun generateSeMoveList(coord: Coord): List<Coord> {
        val possibleLocations = arrayListOf<Coord>()
        for (i in 1..7) {
            val nextCoord = Coord.getValidatedCoord(coord.file + i, coord.rank- i)
            possibleLocations.add(nextCoord ?:break)
        }
        return possibleLocations
    }

    fun generateNwMoveList(coord: Coord): List<Coord> {
        val possibleLocations = arrayListOf<Coord>()
        for (i in 1..7) {
            val nextCoord = Coord.getValidatedCoord(coord.file -i, coord.rank+i)
            possibleLocations.add(nextCoord ?:break)
        }
        return possibleLocations
    }

    fun generateSwMoveList(coord: Coord): List<Coord> {
        val possibleLocations = arrayListOf<Coord>()
        for (i in 1..7) {
            val nextCoord = Coord.getValidatedCoord(coord.file -i, coord.rank-i)
            possibleLocations.add(nextCoord ?:break)
        }
        return possibleLocations
    }
    override fun generateTakeList(): List<Coord> {
        TODO("Not yet implemented")
    }

    override fun verifyTake(): Boolean {
        TODO("Not yet implemented")
    }
}