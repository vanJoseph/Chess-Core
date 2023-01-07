package com.wildercoding.chess.iomethods

import com.wildercoding.chess.data.MoveRequest

interface InputMethod {
    fun getMove(): MoveRequest
}