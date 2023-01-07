package com.wildercoding.chess.pieces

import com.wildercoding.chess.units.Board
import com.wildercoding.chess.units.Color
import com.wildercoding.chess.units.Coord
import wildercoding.chess.*

class Rook(color: Color) : Piece(PieceType.ROOK, color) {

    override fun generateMovesList(coord: Coord): List<Coord> {
        val possibleLocations = arrayListOf<Coord>()

        possibleLocations.addAll(generateNorthMoveList(coord))
        possibleLocations.addAll(generateSouthMoveList(coord))
        possibleLocations.addAll(generateWestMoveList(coord))
        possibleLocations.addAll(generateEastMoveList(coord))

        return possibleLocations
    }

    override fun verifyMove(board: Board, fromPos: Coord, toPos: Coord): Boolean {
        val moveArray = arrayOf<List<Coord>>(
                generateNorthMoveList(fromPos),
                generateEastMoveList(fromPos),
                generateSouthMoveList(fromPos),
                generateWestMoveList(fromPos))

        var positionDirection = -1
        // Find out which direction has to the toPos
        for ((index, direction) in moveArray.withIndex()){
            for(coord in direction){ //
                if(coord == toPos){
                    positionDirection = index
                }
            }
            if(positionDirection!=-1){ // Breaks the loop when it found the direction of the toPos
                break
            }
        }


        if (positionDirection==-1){
            return false
        }

        // Go tru the list to make sure there isn't any Pieces in the way

        for(position in moveArray[positionDirection]){
            if(position ==  toPos){
                break
            }
            if(board.getPiece(position)!is None){
                return false
            }
        }
        return true
    }

    override fun verifyTake(board: Board, fromPos: Coord, toPos: Coord): Boolean {
        return verifyMove(board,fromPos,toPos)
    }

    fun generateNorthMoveList(coord: Coord): List<Coord> {
        val possibleLocations = arrayListOf<Coord>()
        for (rank in coord.rank + 1..7) {
            possibleLocations.add(Coord(coord.file, rank))
        }
        return possibleLocations
    }

    fun generateSouthMoveList(coord: Coord): List<Coord> {
        val possibleLocations = arrayListOf<Coord>()
        for (rank in coord.rank - 1 downTo 0) {
            possibleLocations.add(Coord(coord.file, rank))
        }
        return possibleLocations
    }

    fun generateWestMoveList(coord: Coord): List<Coord> {
        val possibleLocations = arrayListOf<Coord>()
        for (file in coord.file - 1 downTo 0) {
            possibleLocations.add(Coord(file, coord.rank))
        }
        return possibleLocations
    }

    fun generateEastMoveList(coord: Coord): List<Coord> {
        val possibleLocations = arrayListOf<Coord>()
        for (file in coord.file + 1..7) {
            possibleLocations.add(Coord(file, coord.rank))
        }
        return possibleLocations
    }
}