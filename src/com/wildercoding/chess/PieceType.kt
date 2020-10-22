package wildercoding.chess
enum class PieceType: Nameable  {
    PAWN {
        override fun toName(): String {
            return "Pawn"
        }
    },
    KNIGHT {
        override fun toName(): String {
            return "Knight"
        }
    },
    BISHOP {
        override fun toName(): String {
            return "Bishop"
        }
    },
    ROOK {
        override fun toName(): String {
            return "Rook"
        }
    },
    KING {
        override fun toName(): String {
            return "King"
        }
    },
    QUEEN {
        override fun toName(): String {
            return "Queen"
        }
    }

}

interface Nameable {
    fun toName():String
}