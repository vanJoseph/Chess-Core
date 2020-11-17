package wildercoding.chess

data class MoveInfo(val success:Boolean,
                    val correctPiece: Boolean?,
                    val emptySquare:Boolean?,
                    val validMove:Boolean?
                    ) {
}