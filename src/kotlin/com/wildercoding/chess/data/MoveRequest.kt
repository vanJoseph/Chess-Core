package com.wildercoding.chess.data

import com.wildercoding.chess.units.Coord

data class MoveRequest(var fromPos: Coord, var toPos: Coord)