package com.wildercoding.chess.iomethods

import wildercoding.chess.Board
import wildercoding.chess.Color
import wildercoding.chess.MoveInfo

interface OutputMethod {
    fun display(playerTurn: Color, isPlayable:Boolean, isInCheck: Boolean, moveInfo: MoveInfo?, board : Board)
}