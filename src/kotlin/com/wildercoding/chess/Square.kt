package wildercoding.chess

import com.wildercoding.chess.pieces.None
import com.wildercoding.chess.pieces.Piece

class Square(val boardPostion: Coord) {
    var piece: Piece = None()
    private set

    fun addPiece(piece: Piece){
        this.piece=piece
    }
    fun removePiece(): Boolean {
        if (piece is None)
            return false
        piece= None()
        return true
    }
    override fun toString(): String {
        val pieceString = piece.type
        return "$pieceString:$boardPostion"
    }
}