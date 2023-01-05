package wildercoding.chess

import java.lang.RuntimeException

class InvalidCoordException(file: Int, rank: Int): RuntimeException("Coord($file,$rank) is not in range.")