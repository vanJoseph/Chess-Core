package wildercoding.chess

data class MoveRequest(var pieceType: PieceType?,
                       var fromPos: Coord,
                       var toPos: Coord,
                       var moveType: MoveType,
                       var pieceValidation: Boolean = false,
                       var moveValidation: Boolean = false,
                       var executeMove: Boolean = false
)