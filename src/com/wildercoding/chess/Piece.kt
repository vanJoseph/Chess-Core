package wildercoding.chess

import java.lang.RuntimeException

abstract class Piece(var type: PieceType, val color: Player) {
    var location = Coord(-1, -1)
    abstract fun generateMovesList(): List<Coord>

    fun verifyMove(coord: Coord): Boolean {
        val filterdMoves = generateMovesList()

        if(coord==location) // The current position is not a valid move
            return false
        for (move in filterdMoves) {
            if (move == coord)
                return true
        }
        return false
    }
fun validateLocation(possibleMoves:ArrayList<Coord?>):List<Coord> {
    for ((index, move) in possibleMoves.withIndex()) {
        if (!Coord.validateCoord(move!!))
            possibleMoves[index] = null
    }
    return possibleMoves.filterNotNull()
}
    companion object {
        fun spawnPiece(pieceType: PieceType):Piece{
            when(pieceType){
                PieceType.PAWN -> return Pawn(Player.WHITE)
                PieceType.ROOK -> return Rook(Player.WHITE)
                PieceType.KNIGHT -> return Knight(Player.WHITE)
                PieceType.BISHOP -> return Bishop(Player.WHITE)
                PieceType.QUEEN -> return Queen(Player.WHITE)
                PieceType.KING -> return King(Player.WHITE)
                else -> throw RuntimeException("Unknow Piecetype $pieceType")
            }
        }

        fun filterOutOfBoardPositions(moves: List<Coord>): List<Coord> {
            var filteredMoves = arrayListOf<Coord>()
            for (move in moves) {
                if (move.file >= 0 || move.file <= 7 &&
                        move.rank >= 0 || move.rank <= 7) {
                    filteredMoves.add(move)
                }
            }
            return filteredMoves
        }
    }
}