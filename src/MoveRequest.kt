data class MoveRequest(var piece: Piece,
                       var fromPos: Coord,
                       var toPos: Coord,
                       var moveType: MoveType,
                       var pieceValidation: Boolean = false,
                       var moveValidation: Boolean = false,
                       var executeMove: Boolean = false
)