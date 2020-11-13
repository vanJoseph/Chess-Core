package wildercoding.chess

data class Coord(var file: Int, var rank: Int) {

    override fun toString(): String {
        return "($file,$rank)"
    }

    companion object {
        /**
         * Check to see if the Coord is within the range of the board
         */
        fun validateCoord(coord: Coord):Boolean {
            if (coord.file < 0 || coord.file > 7 || coord.rank < 0 || coord.rank > 7)
                return false
            return true
        }
    }
}
