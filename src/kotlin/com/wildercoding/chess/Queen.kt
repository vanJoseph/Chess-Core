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

    override fun verifyMove(board: Board, fromPos: Coord, toPos: Coord): Boolean {
        val moveArray = arrayOf<List<Coord>>(
                generateNorthMoveList(fromPos),
                generateEastMoveList(fromPos),
                generateSouthMoveList(fromPos),
                generateWestMoveList(fromPos),
                generateNeMoveList(fromPos),
                generateSeMoveList(fromPos),
                generateSwMoveList(fromPos),
                generateNwMoveList(fromPos))

        var positionDirection = -1
        // Find out which direction has to the toPos
        for ((index, direction) in moveArray.withIndex()){
            for(coord in direction){ //
                if(coord == toPos){
                    positionDirection = index
                }
            }
            if(positionDirection!=-1){ // Breaks the loop when it found the direction of the toPos
                break
            }
        }


        if (positionDirection==-1){
            return false
        }

        // Go tru the list to make sure there isn't any Pieces in the way

        for(position in moveArray[positionDirection]){
            if(position ==  toPos){
                break
            }
            if(board.getPiece(position)!is None){
                return false
            }
        }
        return true
    }

    override fun verifyTake(board: Board, fromPos: Coord, toPos: Coord): Boolean {
        return verifyMove(board,fromPos,toPos)
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
}