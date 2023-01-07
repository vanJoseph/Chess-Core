package com.wildercoding.chess.pieces

import com.wildercoding.chess.units.Board
import com.wildercoding.chess.units.Color
import com.wildercoding.chess.units.Coord
import wildercoding.chess.*

class Knight(color: Color): Piece(PieceType.KNIGHT,color) {


    override fun generateMovesList(location: Coord): List<Coord> {
        val possibleMoves= arrayListOf<Coord?>()
        possibleMoves.add(Coord.getValidatedCoord(location.file + 2, location.rank + 1))
        possibleMoves.add(Coord.getValidatedCoord(location.file + 2, location.rank - 1))
        possibleMoves.add(Coord.getValidatedCoord(location.file - 2, location.rank + 1))
        possibleMoves.add(Coord.getValidatedCoord(location.file - 2, location.rank - 1))

        possibleMoves.add(Coord.getValidatedCoord(location.file + 1, location.rank + 2))
        possibleMoves.add(Coord.getValidatedCoord(location.file + 1, location.rank - 2))
        possibleMoves.add(Coord.getValidatedCoord(location.file - 1, location.rank + 2))
        possibleMoves.add(Coord.getValidatedCoord(location.file - 1, location.rank - 2))
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
}
