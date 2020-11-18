import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import wildercoding.chess.*

class PawnTest : PieceTest {
    @Test
    fun Should_NotPassPiece_When_PieceIsBlockingPath() {
        // For White Pawn
        var board = Board()
        var pawn = Pawn(Player.WHITE,board)
        var startlocation = Coord(0, 1)
        board.addPiece(pawn, startlocation)
        var passingMoves = Coord(0,3)
        var blockingPieceLocation = Coord(0,2)

        // Populate the board with Blocking pieces
            board.addPiece(Pawn(Player.WHITE), blockingPieceLocation)
        // Test the passing move in all directions
            assertFalse(pawn.verifyMove(passingMoves),
                    "White Pawn@: ${pawn.location} should not pass ${board.getPiece(blockingPieceLocation)!!.type}@: $blockingPieceLocation")


        // For White Pawn
         board = Board()
         pawn = Pawn(Player.BLACK,board)
         startlocation = Coord(0, 6)
        board.addPiece(pawn, startlocation)
         passingMoves = Coord(0,4)
         blockingPieceLocation = Coord(0,5)

        // Populate the board with Blocking pieces
        board.addPiece(Pawn(Player.WHITE), blockingPieceLocation)
        // Test the passing move in all directions
        assertFalse(pawn.verifyMove(passingMoves),
                "White Pawn@: ${pawn.location} should not pass ${board.getPiece(blockingPieceLocation)!!.type}@: $blockingPieceLocation")

    }

    @Test
    fun When_PawnOfCertainColorMove_Should_MoveDistinctDirection() {

        // Testing White Pawn direction
        var board = Board()
        var startPos = Coord(0, 1)
        var endPos = Coord(0, 0)
        var pawn = Pawn(Player.WHITE)
        board.addPiece(pawn,startPos)
        var gameManager=GameManager(board,DirectInputMethod(startPos,endPos),ConsoleOutputMethod())
        assertFalse(gameManager.executeMove().success)

        // Testing black Pawn direction
        board = Board()
        startPos = Coord(0, 2)
        endPos = Coord(0, 1)
        pawn = Pawn(Player.BLACK)
        board.addPiece(pawn,startPos)
        gameManager=GameManager(board,DirectInputMethod(startPos,endPos),ConsoleOutputMethod())
        gameManager.playerTurn=Player.BLACK
        assertTrue(gameManager.executeMove().success,"Black pawn can not move in the right direction")

        // Test the first move 2 square move
        board = Board()
        pawn = Pawn(Player.BLACK)
        board.addPiece(pawn,startPos)
        gameManager=GameManager(board,DirectInputMethod(startPos,Coord(0,0)),ConsoleOutputMethod())
        gameManager.playerTurn=Player.BLACK
        assertTrue(gameManager.executeMove().success, "This move is legal for black")


        // Test that back for black is illegal
        // One square Movement--Illegal
        board = Board()
        startPos = Coord(0, 2)
        endPos = Coord(0, 3)
        pawn = Pawn(Player.BLACK)
        board.addPiece(pawn,startPos)
        gameManager=GameManager(board,DirectInputMethod(startPos,endPos),ConsoleOutputMethod())
        gameManager.playerTurn=Player.BLACK
        assertFalse(gameManager.executeMove().success,"Black Pawn should not be able to move here")

        // Two square movement --Illegal
        board = Board()
        pawn = Pawn(Player.BLACK)
        board.addPiece(pawn,startPos)
        gameManager=GameManager(board,DirectInputMethod(startPos,Coord(0,4)),ConsoleOutputMethod())
        gameManager.playerTurn=Player.BLACK
        assertFalse(gameManager.executeMove().success, "Black Pawn should not be able to move here")

    }

    @Test
    override fun Should_VerifyCorrectMovePattern_When_GivenAMove() {

        val board = Board()
        val startingPosition = Coord(0, 0)
        val pawn = Pawn(Player.WHITE)
        board.addPiece(pawn, startingPosition)
        // First Move
        val legalFirstMoves = arrayOf<Coord>(Coord(0, 1), Coord(0, 2))
        pawn.firstMove = true   // Indicate that it is its first move

        // Test the moves
        for (move in legalFirstMoves) {
            assertTrue(pawn.verifyMove(move))
        }

        // Second Move
        val legalSecondMove = Coord(0, 1)
        pawn.firstMove = false   // Indicate that it is not the pawns first move

        // Test the moves
        assertTrue(pawn.generateMovesList().size == 1, "Pawn has too many possible moves")
        assertTrue(pawn.verifyMove(legalSecondMove), "Pawn isn't making the right non-first move")

    }

    @Test
    override fun Should_Not_VerifyCorrectMovePattern_When_GivenAMove() {

        val board = Board()
        val startingPosition = Coord(0, 0)
        val pawn = Pawn(Player.WHITE)
        board.addPiece(pawn, startingPosition)
        // First Move
        val illegalLegalFirstMoves = arrayOf<Coord>(Coord(0, 0), Coord(0, 3), Coord(1, 2), Coord(1, 0), Coord(1, 1))
        pawn.firstMove = true   // Indicate that it is its first move

        // Test the moves
        for (move in illegalLegalFirstMoves) {
            assertFalse(pawn.verifyMove(move))
        }

        // Second Move
        val legalSecondMove = Coord(0, 2)
        pawn.firstMove = false   // Indicate that it is not the pawns first move

        // Test the moves
        assertFalse(pawn.generateMovesList().size > 1, "Pawn has too many possible moves")
        assertFalse(pawn.verifyMove(legalSecondMove), "Pawn isn't making the right non-first move")
    }
}