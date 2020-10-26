package wildercoding.chess

abstract class Piece(var piece: PieceType,val color:Player) {
    var location = Coord(-1,-1)
    abstract fun verifyMove(coord: Coord): Boolean


}