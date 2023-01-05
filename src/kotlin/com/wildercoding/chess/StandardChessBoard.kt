package com.wildercoding.chess

import wildercoding.chess.*

class StandardChessBoard: Board() {
    init {
        addPawns()
        addRooks()
        addBishops()
        addKnights()
        addQueens()
        addKings()
    }
    fun addPawns() {
        for (i in 0..7){
            addPiece(Pawn(Color.WHITE), Coord(i,1))
            addPiece(Pawn(Color.BLACK), Coord(i,6))
        }
    }
    fun addRooks() {
        addPiece(Rook(Color.WHITE),Coord(7,0))
        addPiece(Rook(Color.WHITE),Coord(0,0))
        addPiece(Rook(Color.BLACK),Coord(7,7))
        addPiece(Rook(Color.BLACK),Coord(0,7))
    }
    fun addBishops() {
        addPiece(Bishop(Color.WHITE),Coord(5,0))
        addPiece(Bishop(Color.WHITE),Coord(2,0))
        addPiece(Bishop(Color.BLACK),Coord(5,7))
        addPiece(Bishop(Color.BLACK),Coord(2,7))
    }

    fun addKnights() {
        addPiece(Knight(Color.WHITE),Coord(6,0))
        addPiece(Knight(Color.WHITE),Coord(1,0))
        addPiece(Knight(Color.BLACK),Coord(6,7))
        addPiece(Knight(Color.BLACK),Coord(1,7))
    }

    fun addQueens() {
        addPiece(Queen(Color.WHITE),Coord(3,0))
        addPiece(Queen(Color.BLACK),Coord(3,7))
    }

    fun addKings() {
        addPiece(King(Color.WHITE),Coord(4,0))
        addPiece(King(Color.BLACK),Coord(4,7))
    }
}