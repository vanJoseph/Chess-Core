package com.wildercoding.chess.exceptions

import java.lang.RuntimeException

class InvalidCoordException(file: Int, rank: Int): RuntimeException("Coord($file,$rank) is not in range.")