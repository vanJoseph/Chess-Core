package wildercoding.chess

enum class Player:Nameable {
    WHITE {
        override fun getTypeName(): String {
            return "White"
        }

        override fun getAbbr(): Char {
            return 'W'
        }
    },
    BLACK;

    override fun getTypeName(): String {
        return "Black"
    }

    override fun getAbbr(): Char {
        return 'B'
    }
}