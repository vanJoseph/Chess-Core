package wildercoding.chess

class Pawn(color: Player) : Piece(PieceType.PAWN, color) {
    constructor(color: Player,board: Board): this(color){
        this.board=board
    }
    var board:Board?=null
    var firstMove = true

    override fun generateMovesList(): List<Coord> {
        val possibleMoves = arrayListOf<Coord?>()

        if(board==null) {
            when (firstMove) {
                true -> {
                    if (color == Player.BLACK) {
                        possibleMoves.add(Coord(location.file, location.rank - 1))
                        possibleMoves.add(Coord(location.file, location.rank - 2))

                    } else {
                        possibleMoves.add(Coord(location.file, location.rank + 1))
                        possibleMoves.add(Coord(location.file, location.rank + 2))
                    }
                }
                false -> {
                    if (color == Player.BLACK)
                        possibleMoves.add(Coord(location.file, location.rank - 1))
                    else
                        possibleMoves.add(Coord(location.file, location.rank + 1))
                }
            }
        }else {

            //  This if statement is trash and unclear i just wanted to see if i could write it
            if(board!!.getPiece(location.file, location.rank + if (color==Player.BLACK)-1 else 1)==null) {

                when (firstMove) {
                    true -> {
                        if (color == Player.BLACK) {
                            possibleMoves.add(Coord(location.file, location.rank - 1))
                            possibleMoves.add(Coord(location.file, location.rank - 2))

                        } else {
                            possibleMoves.add(Coord(location.file, location.rank + 1))
                            possibleMoves.add(Coord(location.file, location.rank + 2))
                        }
                    }
                    false -> {
                        if (color == Player.BLACK)
                            possibleMoves.add(Coord(location.file, location.rank - 1))
                        else
                            possibleMoves.add(Coord(location.file, location.rank + 1))
                    }
                }
            }
        }
        return validateLocation(possibleMoves)
    }


}