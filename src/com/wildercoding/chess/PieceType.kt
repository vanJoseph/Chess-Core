package wildercoding.chess
enum class PieceType: Nameable  {
    PAWN {
        override fun getPieceName(): String {
            return "Pawn"
        }
    },
    KNIGHT {
        override fun getPieceName(): String {
            return "Knight"
        }
    },
    BISHOP {
        override fun getPieceName(): String {
            return "Bishop"
        }
    },
    ROOK {
        override fun getPieceName(): String {
            return "Rook"
        }
    },
    KING {
        override fun getPieceName(): String {
            return "King"
        }
    },
    QUEEN {
        override fun getPieceName(): String {
            return "Queen"
        }
    }

}

interface Nameable {
    fun getPieceName():String
}