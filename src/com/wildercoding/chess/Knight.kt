package wildercoding.chess

class Knight(color:Player): Piece(PieceType.KNIGHT,color) {
    public override fun verifyMove(coord: Coord): Boolean {
        val filterdMoves = Piece.filterOutOfBoardPositions(generatMoves())
        for (move in filterdMoves) {
            if(move == coord)
                return true
        }
        return false
    }

    fun generatMoves(): List<Coord> {
        val moves= arrayListOf<Coord>()
        var position = location.copy()

        position.rank+=2
        position.file+=1
        moves.add(position.copy())
        position = location.copy()
        position.rank+=2
        position.file-=1
        moves.add(position.copy())
        position = location.copy()
        position.rank-=2
        position.file+=1
        moves.add(position.copy())
        position = location.copy()
        position.rank-=2
        position.file-=1
        moves.add(position.copy())
        position = location.copy()
        position.rank+=1
        position.file+=2
        moves.add(position.copy())
        position = location.copy()
        position.rank+=1
        position.file-=2
        moves.add(position.copy())
        position = location.copy()
        position.rank-=1
        position.file+=2
        moves.add(position.copy())
        position = location.copy()
        position.rank-=1
        position.file-=2
        moves.add(position.copy())

        return moves
    }

}
