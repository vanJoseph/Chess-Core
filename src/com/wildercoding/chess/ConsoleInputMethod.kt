package wildercoding.chess

class ConsoleInputMethod: InputMethod {
    override fun getMove(gameInfo: GameInfo): MoveRequest {
        println("${gameInfo.playerTurn.name} Turn:")

        val fromValidation =false

        println("Choose piece: ")
        val fromPos = readLine()!!


        println("Choose move: ")
        val toPos = readLine()!!

        return validateMoveRequst(fromPos,toPos)
    }

    fun validateMoveRequst(fromPos: String, toPos: String): MoveRequest {

        // create the fromPos Coord
        val fromFile = fromPos[0].toString().toInt()
        val fromRank = fromPos[1].toString().toInt()

        val fromCoord = Coord(fromFile, fromRank)

        // create the toPos Coord
        val toFile = toPos[0].toString().toInt()
        val toRank = toPos[1].toString().toInt()

        val toCoord = Coord(toFile, toRank)

        return MoveRequest(null,fromCoord,toCoord,MoveType.MOVE)




    }

}