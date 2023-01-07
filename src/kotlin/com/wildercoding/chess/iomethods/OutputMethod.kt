package com.wildercoding.chess.iomethods

import com.wildercoding.chess.units.Board
import com.wildercoding.chess.units.Color
import com.wildercoding.chess.data.MoveInfo

interface OutputMethod {
    fun display(playerTurn: Color, isPlayable:Boolean, isInCheck: Boolean, moveInfo: MoveInfo?, board : Board)
}