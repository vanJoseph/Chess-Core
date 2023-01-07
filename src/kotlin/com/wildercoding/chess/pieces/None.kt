package com.wildercoding.chess.pieces

import wildercoding.chess.Board
import wildercoding.chess.Color
import wildercoding.chess.Coord
import wildercoding.chess.PieceType

class None() : Piece(PieceType.NONE, Color.NONE) {
    override fun generateMovesList(position: Coord): List<Coord> = listOf()
    override fun verifyMove(board: Board, fromPos: Coord, toPos: Coord): Boolean = false
    override fun verifyTake(board: Board, fromPos: Coord, toPos: Coord): Boolean = false
}