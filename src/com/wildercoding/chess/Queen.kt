package wildercoding.chess

class Queen(color: Player) : Piece(PieceType.QUEEN, color) {
    constructor(color: Player, board: Board?) : this(color) {
        this.board = board
    }

    var board: Board? = null
    override fun generateMovesList(): List<Coord> {
        val possibleMoves = arrayListOf<Coord>()
        var rook: Rook
        var bishop: Bishop
        if (board==null) {
            rook = Rook(color)
            bishop = Bishop(color)
        }else{
            rook = Rook(color,board!!)
            bishop = Bishop(color,board!!)
        }
        rook.location = location
        bishop.location = location
        possibleMoves.addAll(rook.generateMovesList())
        possibleMoves.addAll(bishop.generateMovesList())
        return possibleMoves
    }
}