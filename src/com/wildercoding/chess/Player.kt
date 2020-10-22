package wildercoding.chess

enum class Player:Nameable {
    WHITE {
        override fun toName(): String {
            return "White"
        }
    },
    BLACK;

    override fun toName(): String {
        return "Black"
    }}