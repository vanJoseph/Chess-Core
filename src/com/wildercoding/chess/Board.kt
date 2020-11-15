package wildercoding.chess

class Board {
    val squares=Array(8){ rank->Array(8){ file->Square(file,rank)}}

    fun initNewGame(){
        addPawns()
        addRooks()
        addKnights()
        addBishops()
        addKings()
        addQueens()
    }
    fun addPiece(piece: Piece, coord: Coord) {
        addPiece(piece,coord.file,coord.rank)
    }
    fun addPiece(piece: Piece, file: Int, rank: Int) {

        squares[rank][file].piece = piece
    }
    fun removePiece(target: Coord) {
        removePiece(target.file,target.rank)
    }
    fun removePiece(targetFile:Int, targetRank:Int){
        squares[targetRank][targetFile].piece = null
    }
    fun clearboard(){
        for (rank in 7 downTo 0) {
            for (file in 0..7) {
                removePiece(file,rank)
            }
        }
    }

    private fun addPawns() {
        for (y in 0..7){
            squares!![1][y].piece=Pawn(Player.WHITE)
            squares!![6][y].piece=Pawn(Player.BLACK)
        }
    }
    private fun addRooks() {
        squares!![0][0].piece = Rook(Player.WHITE)
        squares[0][7].piece = Rook(Player.WHITE)

        squares!![7][0].piece = Rook(Player.BLACK)
        squares!![7][7].piece = Rook(Player.BLACK)
    }
    private fun addKnights() {
        squares!![0][1].piece = Knight(Player.WHITE)
        squares!![0][6].piece = Knight(Player.WHITE)

        squares!![7][1].piece = Knight(Player.BLACK)
        squares!![7][6].piece = Knight(Player.BLACK)
    }
    private fun addBishops() {
        squares!![0][2].piece = Bishop(Player.WHITE)
        squares!![0][5].piece = Bishop(Player.WHITE)

        squares!![7][2].piece = Bishop(Player.BLACK)
        squares!![7][5].piece = Bishop(Player.BLACK)
    }
    private fun addQueens() {
        squares!![0][3].piece = Queen(Player.WHITE)

        squares!![7][3].piece = Queen(Player.BLACK)
    }
    private fun addKings() {
        squares!![0][4].piece = King(Player.WHITE)

        squares!![7][4].piece = King(Player.BLACK)
    }
    fun getPiece(coord: Coord):Piece?{
        return getPiece( coord.file,coord.rank)
    }
    fun getPiece(file: Int, rank: Int): Piece? {
        return squares[rank][file].piece
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