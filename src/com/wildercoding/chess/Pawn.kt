package wildercoding.chess

class Pawn(color: Color) : Piece(PieceType.PAWN, color) {

    var firstMove = true

    override fun generateMovesList(coord: Coord): List<Coord> {
        val possibleMoves = arrayListOf<Coord?>()
        val blackModifier = if(color == Color.WHITE) 1 else-1
        if (firstMove){
            possibleMoves.add(Coord.getValidatedCoord(coord.file,coord.rank+1 *blackModifier) ?:null)
            possibleMoves.add(Coord.getValidatedCoord(coord.file,coord.rank+2*blackModifier) ?:null)
        }else{
            possibleMoves.add(Coord.getValidatedCoord(coord.file,coord.rank+1*blackModifier) ?:null)
        }
        return possibleMoves.filterNotNull()
    }

    override fun generateTakeList(): List<Coord> {
        TODO("Not yet implemented")
    }

    override fun verifyTake(): Boolean {
        TODO("Not yet implemented")
    }


}