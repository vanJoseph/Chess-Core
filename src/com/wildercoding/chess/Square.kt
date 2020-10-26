package wildercoding.chess

class Square (var boardPostion:Coord,  piece: Piece?){
    var piece =piece
    set(value) {
        if(piece!=null)
            field!!.location=boardPostion
    }

}