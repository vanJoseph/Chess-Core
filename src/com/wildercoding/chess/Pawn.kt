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

    override fun verifyMove(board: Board, fromPos: Coord, toPos: Coord): Boolean {

        for(position in generateMovesList(fromPos)){
            if(toPos == position && board.getPiece(toPos)is None){
                return true
            }
        }
        return false

        return true
    }

    override fun verifyTake(board: Board, fromPos: Coord, toPos: Coord): Boolean {
        val colorMod= if(color==Color.BLACK) -1 else 1
        val takeSquare = arrayOf(
                Coord.getValidatedCoord(fromPos.file+1, fromPos.rank+colorMod),
                Coord.getValidatedCoord(fromPos.file-1, fromPos.rank+colorMod))
        for (square in takeSquare.filterNotNull()) {
            if(square==toPos){
                return true
            }
        }
        return false
    }

}