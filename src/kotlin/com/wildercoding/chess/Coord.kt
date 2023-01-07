package wildercoding.chess

import com.wildercoding.chess.exceptions.InvalidCoordException

data class Coord(var file: Int, var rank: Int) {
    init {
        if (!validateCoord(file, rank))
            throw InvalidCoordException(file, rank)
    }

    override fun toString(): String {
        return "($file,$rank)"
    }


    companion object {
        /**
         * Check to see if the Coord is within the range of the board
         */
        fun validateCoord(file: Int, rank: Int): Boolean {
            if (file < 0 || file > 7 || rank < 0 || rank > 7)
                return false
            return true
        }

        /**
         *Return a Choord that is in range. Returns Null if not
         */
        fun getValidatedCoord(file: Int, rank: Int): Coord? {
            if(validateCoord(file,rank))
                return Coord(file,rank)
            return null
        }
    }
}
