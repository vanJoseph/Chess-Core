package wildercoding.chess

interface InputMethod {
    fun getMove(gameInfo: GameInfo):MoveRequest
}