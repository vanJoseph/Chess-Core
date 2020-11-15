package wildercoding.chess

class ConsoleOutputMethod: OutputMethod {
    override fun display(gameInfo: GameInfo) {
        println(gameInfo.board)
    }
}