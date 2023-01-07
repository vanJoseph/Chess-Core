package wildercoding.chess
enum class PieceType: Abbreviation {
    PAWN {
        override fun toString(): String {
            return "Pawn"
        }

        override fun abbr(): Char {
            return 'P'
        }
    },
    KNIGHT {
        override fun toString(): String {
            return "Knight"
        }

        override fun abbr(): Char {
            return 'N'
        }
    },
    BISHOP {
        override fun toString(): String {
            return "Bishop"
        }

        override fun abbr(): Char {
            return 'B'
        }
    },
    ROOK {
        override fun toString(): String {
            return "Rook"
        }

        override fun abbr(): Char {
            return 'R'
        }
    },
    KING {
        override fun toString(): String {
            return "King"
        }

        override fun abbr(): Char {
            return 'K'
        }
    },
    QUEEN {
        override fun toString(): String {
            return "Queen"
        }

        override fun abbr(): Char {
            return 'Q'
        }
    },
    NONE {
        override fun toString(): String {
            return "None"
        }

        override fun abbr(): Char {
            return '_'
        }
    }
}
interface Abbreviation {
    fun abbr():Char;
}
