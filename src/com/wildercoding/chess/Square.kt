package wildercoding.chess

class Square (val file:Int, val rank:Int){
    var boardPosition=Coord(file,rank)
    var piece:Piece? = null
        set(value) {
        if(value!=null) {
            field=value

            // Assigns the square boardPosition to the piece location
            value.location = boardPosition
        }
    }

    override fun toString(): String {
        val pieceString=piece?.type?.getTypeName() ?:"None"
        return "$pieceString:$boardPosition"
    }
}