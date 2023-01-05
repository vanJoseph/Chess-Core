package wildercoding.chess

enum class Color:Nameable {
    WHITE {
        override fun getTypeName(): String {
            return "White"
        }

        override fun getAbbr(): Char {
            return 'W'
        }
    },
    BLACK {

        override fun getTypeName(): String {
            return "Black"
        }

        override fun getAbbr(): Char {
            return 'B'
        }
    },
    NONE {

        override fun getTypeName(): String {
            return "None"
        }

        override fun getAbbr(): Char {
            return ' '
        }
    }
}