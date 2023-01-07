package com.wildercoding.chess.iomethods

import wildercoding.chess.MoveRequest

interface InputMethod {
    fun getMove(): MoveRequest
}