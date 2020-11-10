package wildercoding.chess

abstract class Piece(var piece: PieceType,val color:Player) {
    var location = Coord(-1,-1)
    abstract fun verifyMove(coord: Coord): Boolean

companion object{
    fun filterOutOfBoardPositions(moves: List<Coord>): List<Coord> {
        var filteredMoves= arrayListOf<Coord>()
        for (move in moves) {
            if(move.file>=0||move.file<=7 &&
                    move.rank>=0 ||move.rank<=7){
                filteredMoves.add(move)
            }
        }
        return filteredMoves
    }
}
}