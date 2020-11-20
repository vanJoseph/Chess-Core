package wildercoding.chess

data class Coord(var file: Int, var rank: Int) {
    init {
        if (validateCoord(file,rank)==false)
            throw InvalidCoordException(file,rank)
    }

    override fun toString(): String {
        return "($file,$rank)"
    }
        /**
         * Check to see if the Coord is within the range of the board
         */
        private fun validateCoord(file:Int, rank:Int ):Boolean {
            if (file < 0 || file > 7 || rank < 0 || rank > 7)
                return false
            return true
        }
}
