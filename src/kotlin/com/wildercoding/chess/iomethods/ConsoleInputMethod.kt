package com.wildercoding.chess.iomethods

import wildercoding.chess.Coord
import wildercoding.chess.MoveRequest

class ConsoleInputMethod: InputMethod {
    override fun getMove(): MoveRequest {
        var fromLoc: String? =null
        while (fromLoc == null) {
            fromLoc =getFromLocation()
            if(!validateInput(fromLoc ?:continue)){
                fromLoc = null
            }
        }

        var toLoc: String? =null
        while (toLoc == null) {
            toLoc =getToLocation()
            if(!validateInput(toLoc ?:continue)){
                toLoc = null
            }
        }
        return MoveRequest(parseInput(fromLoc), parseInput(toLoc))
    }

    fun getFromLocation(): String?{
        println("Choose piece: ")
        return readLine()
    }

    fun getToLocation(): String? {
        println("Choose move: ")
        return readLine()
    }
    fun parseInput(input: String): Coord {

        // create the fromPos Coord
        val fromFile = input[0].toString().toInt()
        val fromRank = input[1].toString().toInt()

        return Coord(fromFile, fromRank)
    }
    fun validateInput(input: String): Boolean {
        if(input.length != 2){
            return false
        }
        val inputValues= arrayOf(
                input[0].toString().toInt(),
                input[1].toString().toInt())

        for (value in inputValues) {
            if( value > 7 || value < 0){
                return false
            }
        }
        return true
    }

}