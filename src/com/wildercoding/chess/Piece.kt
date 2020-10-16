package wildercoding.chess

abstract class Piece(var piece: PieceType,val color:Player) {
    abstract fun verifyMove()

}