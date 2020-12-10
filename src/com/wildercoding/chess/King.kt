package wildercoding.chess

class King(color:Color):Piece(PieceType.KING,color) {
    override fun generateMovesList(position:Coord): List<Coord> {
       val possibleMoves = arrayListOf<Coord?>()

        possibleMoves.add(Coord.getValidatedCoord(position.file+1, position.rank) ?:null)
        possibleMoves.add(Coord.getValidatedCoord(position.file+1, position.rank-1) ?:null)
        possibleMoves.add(Coord.getValidatedCoord(position.file+1, position.rank+1) ?:null)

        possibleMoves.add(Coord.getValidatedCoord(position.file-1, position.rank) ?:null)
        possibleMoves.add(Coord.getValidatedCoord(position.file-1, position.rank-1) ?:null)
        possibleMoves.add(Coord.getValidatedCoord(position.file-1, position.rank+1) ?:null)

        possibleMoves.add(Coord.getValidatedCoord(position.file, position.rank+1) ?:null)
        possibleMoves.add(Coord.getValidatedCoord(position.file, position.rank-1) ?:null)

        return possibleMoves.filterNotNull()
    }

    override fun verifyMove(board: Board, fromPos: Coord, toPos: Coord): Boolean {
        for(position in generateMovesList(fromPos)){
            if(toPos == position){
                return true
            }
        }
        return false
    }

    override fun verifyTake(board: Board, fromPos: Coord, toPos: Coord): Boolean {
        return verifyMove(board,fromPos,toPos)
    }

    fun verifyCastling(moveRequest: MoveRequest): Boolean {
        when(color){
            Color.WHITE ->{
                if(moveRequest== MoveRequest(Coord(4,0), Coord(6,0))||
                        moveRequest== MoveRequest(Coord(4,0), Coord(2,0))){
                    return true
                }
            }
            Color.BLACK ->{
                if(moveRequest ==MoveRequest(Coord(4,7), Coord(6,7)) ||
                        moveRequest == MoveRequest(Coord(4,7), Coord(2,7))){
                    return true
                }
            }
        }
        return false
    }
}