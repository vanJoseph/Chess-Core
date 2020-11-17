import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import wildercoding.chess.*

class GameManagerTest {

    @Test
    fun When_MoveIsExecuted_TurnIsChanged() {
        val board= Board()
        board.addPiece(Pawn(Player.WHITE),0,1)

        val inputMethod=DirectInputMethod(0,1,0,2)
        val gameManager=GameManager(board,inputMethod,ConsoleOutputMethod())
        assertTrue(gameManager.playerTurn==Player.WHITE)
        gameManager.executeMove()
        assertTrue(gameManager.playerTurn==Player.BLACK)

    }
    //Todo create a more robust test that tests all of the pieces
    @Test
    fun When_MoveIsExecuted_PieceIsMoved(){
        val board= Board()
        val startPos=Coord(0,1)
        board.addPiece(Pawn(Player.WHITE),startPos)
        val endPos= Coord(0,2)
        val inputMethod=DirectInputMethod(startPos,endPos)
        val gameManager=GameManager(board,inputMethod,ConsoleOutputMethod())

        // Checking to see if the pawn is at its initial position
        assertTrue(gameManager.board.getPiece(startPos)?.type==PieceType.PAWN)
        gameManager.executeMove()

        // Check to see if the pawn is at the movedTo position
        assertTrue(gameManager.board.getPiece(endPos)?.type==PieceType.PAWN)

        // Check to see if the from space is null
        assertTrue(gameManager.board.getPiece(startPos)==null)

        println(board)
    }

    @Test
    fun When_Move_NotLegal_TurnIsTheSame() {
        val board= Board()
        val startPos=Coord(0,1)
        board.addPiece(Pawn(Player.WHITE),startPos)
        val endPos= Coord(7,7)
        val inputMethod=DirectInputMethod(startPos,endPos)
        val gameManager=GameManager(board,inputMethod,ConsoleOutputMethod())

        assertFalse(gameManager.executeMove().success,"The move is illegal and shouldn't execute")
        assertTrue(gameManager.playerTurn==Player.WHITE,"The playerTurn shouldn't change, because of illegal move")
    }

    @Test
    fun Should_MoveOnlyCurrentPlayerPiece(){

        // White Turn
        var board= Board()

        var pawn =Pawn(Player.WHITE)
        var startPos=Coord(0,1)
        board.addPiece(pawn,startPos)
        var endPos= Coord(0,2)
        var inputMethod=DirectInputMethod(startPos,endPos)
        var gameManager=GameManager(board,inputMethod,ConsoleOutputMethod())
        gameManager.playerTurn= Player.WHITE
        assertTrue( gameManager.executeMove().success,"${gameManager.playerTurn} can not move a ${pawn.color} piece.")


        // Black Turn
        board= Board()
         startPos=Coord(0,1)
         pawn =Pawn(Player.WHITE)
        board.addPiece(pawn,startPos)
        endPos= Coord(0,2)
         inputMethod=DirectInputMethod(startPos,endPos)
         gameManager=GameManager(board,inputMethod,ConsoleOutputMethod())
        gameManager.playerTurn= Player.BLACK
        assertFalse( gameManager.executeMove().success,"${gameManager.playerTurn} can not move a ${pawn.color} piece.") // Todo geting weird output for the message when it fails

    }

    @Test
    fun When_SquareIsOccupied_Should_NotBeAbleToMoveThere() {
        // White Turn
        var board= Board()

        var pawn1 =Pawn(Player.WHITE)
        var pawn2 =Pawn(Player.WHITE)
        var startPos=Coord(0,1)
        var endPos= Coord(0,2)
        board.addPiece(pawn1,startPos)
        board.addPiece(pawn2,endPos)

        var inputMethod=DirectInputMethod(startPos,endPos)
        var gameManager=GameManager(board,inputMethod,ConsoleOutputMethod())
        assertFalse( gameManager.executeMove().success,"Illegal Move: Square $endPos is occupied.")
    }



}