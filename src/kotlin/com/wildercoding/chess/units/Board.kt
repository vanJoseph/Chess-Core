package com.wildercoding.chess.units

import com.wildercoding.chess.pieces.None
import com.wildercoding.chess.pieces.Piece


/**
 * The Representation of Chess board
 *
 * It contains an 8x8 array of [Square].
 * Use the methods to add and remove pieces in each square.
 *
 */
open class Board {
    private val squares = Array(8) { rank -> Array(8) { file -> Square(Coord(file, rank)) } }


    /**
     * Returns a [Square] on the board
     *
     * @param coord the board position
     */
    fun getSquare(coord: Coord): Square {
        return squares[coord.rank][coord.file]
    }

    /**
     * Returns a [Piece] from the board
     *
     * @param target the board position
     */
    fun getPiece(target: Coord): Piece {
        return squares[target.rank][target.file].piece
    }

    /**
     * Adds a piece to the board
     *
     *@param piece The [Piece]
     * @param toCoord The board position
     *
     * @return true if a piece was added  false if no piece was added
     */
    fun addPiece(piece: Piece, toCoord: Coord): Boolean {
        if (piece is None) {
            return false
        }
        val square = getSquare(toCoord)
        square.addPiece(piece)
        return true
    }

    /**
     * Removes a piece from the board
     *
     * @param target the board position
     */
    fun removePiece(target: Coord): Boolean {
        val square=getSquare(target)
        if(getPiece(target) is None) {
            return false
        }
        square.removePiece()
        return true
    }

    /**
     * Turn all square to [None]
     */
    fun clear() {
        for (y in 7 downTo 0) {
            for (x in 0..7) {
                removePiece(Coord(x,y))
            }
        }
    }

    override fun toString(): String {
        var boardString= StringBuilder()
        for (y in 7 downTo 0) {
            for (x in 0..7) {
                val coord= Coord(x,y)
                boardString.append("$coord: ${getPiece(coord).color.getAbbr()}${getPiece(coord).type.abbr()}\t")
            }
            boardString.append("\n")
        }
        return boardString.toString()
    }

    /**
     * Todo Create a Test for equals
     */
    override fun equals(other: Any?): Boolean {
        if(other !is Board){
            return false
        }
        val otherBoard = other as Board

        for (y in 0..7){
            for (x in 0..7){
                val coord = Coord(x,y)
                if(getPiece(coord).type != otherBoard.getPiece(coord).type ||
                        getPiece(coord).color != otherBoard.getPiece(coord).color){
                    return false
                }
            }
        }
        return true
    }
}