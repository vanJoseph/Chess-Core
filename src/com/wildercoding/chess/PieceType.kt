package wildercoding.chess
enum class PieceType: Nameable  {
    PAWN {
        override fun getTypeName(): String {
            return "Pawn"
        }

        override fun getAbbr(): Char {
            return Char.MIN_VALUE
        }
    },
    KNIGHT {
        override fun getTypeName(): String {
            return "Knight"
        }

        override fun getAbbr(): Char {
            return 'N'
        }
    },
    BISHOP {
        override fun getTypeName(): String {
            return "Bishop"
        }
        override fun getAbbr(): Char {
            return 'B'
        }
    },
    ROOK {
        override fun getTypeName(): String {
            return "Rook"
        }

        override fun getAbbr(): Char {
            return 'R'
        }
    },
    KING {
        override fun getTypeName(): String {
            return "King"
        }

        override fun getAbbr(): Char {
            return 'K'
        }
    },
    QUEEN {
        override fun getTypeName(): String {
            return "Queen"
        }

        override fun getAbbr(): Char {
            return 'Q'
        }
    }

}

