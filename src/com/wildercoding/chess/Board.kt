package wildercoding.chess

class Board {
    val board=Array(8){rank->Array(8){file->Square(file,rank)}}

    fun initNewGame(){
        addPawns()
        addRooks()
        addKnights()
        addBishops()
        addKings()
        addQueens()
    }
    fun addPiece(piece: Piece?, coord: Coord) {
        addPiece(piece,coord.file,coord.rank)
    }
    fun addPiece(piece: Piece?, file: Int, rank: Int) {
        board[rank][file].piece = piece
    }
    fun clearboard(){
        for (y in 7 downTo 0) {
            for (x in 0..7) {
                addPiece(null,Coord(x,y))
            }
        }
    }

    private fun addPawns() {
        for (y in 0..7){
            board!![1][y].piece=Pawn(Player.WHITE)
            board!![6][y].piece=Pawn(Player.BLACK)
        }
    }
    private fun addRooks() {
        board!![0][0].piece = Rook(Player.WHITE)
        board!![0][7].piece = Rook(Player.WHITE)

        board!![7][0].piece = Rook(Player.BLACK)
        board!![7][7].piece = Rook(Player.BLACK)
    }
    private fun addKnights() {
        board!![0][1].piece = Knight(Player.WHITE)
        board!![0][6].piece = Knight(Player.WHITE)

        board!![7][1].piece = Knight(Player.BLACK)
        board!![7][6].piece = Knight(Player.BLACK)
    }
    private fun addBishops() {
        board!![0][2].piece = Bishop(Player.WHITE)
        board!![0][5].piece = Bishop(Player.WHITE)

        board!![7][2].piece = Bishop(Player.BLACK)
        board!![7][5].piece = Bishop(Player.BLACK)
    }
    private fun addQueens() {
        board!![0][3].piece = Queen(Player.WHITE)

        board!![7][3].piece = Queen(Player.BLACK)
    }
    private fun addKings() {
        board!![0][4].piece = King(Player.WHITE)

        board!![7][4].piece = King(Player.BLACK)
    }
    fun getPiece(coord: Coord):Piece?{
        return getPiece( coord.file,coord.rank)
    }
    fun getPiece(file: Int, rank: Int): Piece? {
        return board!![rank][file].piece
    }
    override fun toString(): String {
         var boardString= String()
        for (rank in 7 downTo 0) {
            for (file in 0..7) {
                val piece:Piece?=getPiece(file,rank)
                var pieceType=piece?.type ?:"None"
                boardString+="$pieceType:($file,$rank)\t"
            }
            boardString+="\n"
        }
        return boardString
    }

}