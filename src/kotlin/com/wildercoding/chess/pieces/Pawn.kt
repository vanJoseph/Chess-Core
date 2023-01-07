package com.wildercoding.chess.pieces

import com.wildercoding.chess.units.Board
import com.wildercoding.chess.units.Color
import com.wildercoding.chess.units.Coord
import wildercoding.chess.*

class Pawn(color: Color) : Piece(PieceType.PAWN, color) {

    override fun generateMovesList(coord: Coord): List<Coord> {
        val possibleMoves = arrayListOf<Coord?>()
        val blackModifier = if (color == Color.WHITE) 1 else -1
        if (firstMove) {
            possibleMoves.add(Coord.getValidatedCoord(coord.file, coord.rank + 1 * blackModifier) ?: null)
            possibleMoves.add(Coord.getValidatedCoord(coord.file, coord.rank + 2 * blackModifier) ?: null)
        } else {
            possibleMoves.add(Coord.getValidatedCoord(coord.file, coord.rank + 1 * blackModifier) ?: null)
        }
        return possibleMoves.filterNotNull()
    }

    override fun verifyMove(board: Board, fromPos: Coord, toPos: Coord): Boolean {
        val movesList = generateMovesList(fromPos)
        // Fix So that pawn do not move twice on the first move if there is a piece blocking
        if (board.getPiece(movesList[0]) !is None) {
            return false
        }
        // End Fix

        for (position in movesList) {

            if (toPos == position && board.getPiece(toPos) is None) {
                return true
            }
        }

        return false
    }

    override fun verifyTake(board: Board, fromPos: Coord, toPos: Coord): Boolean {
        val colorMod = if (color == Color.BLACK) -1 else 1
        val takeSquare = arrayOf(
            Coord.getValidatedCoord(fromPos.file + 1, fromPos.rank + colorMod),
            Coord.getValidatedCoord(fromPos.file - 1, fromPos.rank + colorMod)
        )
        for (square in takeSquare.filterNotNull()) {
            if (board.getPiece(square).color == Color.NONE) {
                continue
            }
            if (square == toPos) {
                return true
            }
        }
        return false
    }

}