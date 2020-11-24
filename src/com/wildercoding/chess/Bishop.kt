package wildercoding.chess

class Bishop(color: Color) : Piece(PieceType.BISHOP, color) {

    override fun generateMovesList(coord: Coord): List<Coord> {
        val possibleLocations = arrayListOf<Coord>()

        possibleLocations.addAll(generateNeMoveList(coord))
        possibleLocations.addAll(generateNwMoveList(coord))
        possibleLocations.addAll(generateSeMoveList(coord))
        possibleLocations.addAll(generateSwMoveList(coord))

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
}