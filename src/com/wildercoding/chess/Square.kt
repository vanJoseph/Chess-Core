package wildercoding.chess

class Square (val file:Int, val rank:Int){
    var boardPosition=Coord(file,rank)
    var piece:Piece? = null
        get() {
            return field
        }
        set(value) {
        if(piece!=null)
            field!!.location=boardPosition
    }

    override fun toString(): String {
        val pieceString=piece?.type?.getTypeName() ?:"None"
        return "$pieceString:$boardPosition"
    }
}