package wildercoding.chess

data class Coord(var file: Int, var rank: Int) {

    override fun toString(): String {
        return "($file,$rank)"
    }
}
