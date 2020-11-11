import org.junit.jupiter.api.Test
import wildercoding.chess.Board
import wildercoding.chess.Coord
import wildercoding.chess.Knight
import wildercoding.chess.Player

class BoardTest {

    @Test
    fun When_PieceAdded_PieceDisplay(){
        val board= Board()
       board.initNewGame()
  //      board.addPiece(Knight(Player.WHITE),0,0)

//        val location= Coord(0,0)
//        board.add(Knight(Player.WHITE), location)


        println(board.toString())


    }

}