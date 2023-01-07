
import com.wildercoding.chess.pieces.Pawn
import com.wildercoding.chess.pieces.Rook
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import wildercoding.chess.*

class PawnTest  {
    lateinit var allCoordList: List<Coord>

    @BeforeEach
    fun setup() {
        val allCoordsArrayList = arrayListOf<Coord>()
        for (y in 0..7) {
            for (x in 0..7) {
                allCoordsArrayList.add(Coord(x, y))
            }
        }
        allCoordList = allCoordsArrayList
    }

    @Test
    fun Should_MoveNorthTwoSpaces_When_WhiteFirstMove() {
        val piece= Pawn(Color.WHITE)
        val startPos = Coord(1, 1)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(Coord(1,2), Coord(1,3))
        legalMoves.forEach { coord:Coord -> assertTrue(verifiedMoves.contains(coord)) }
    }

    @Test
    fun Should_MoveSouthTwoSpaces_When_BlackFirstMove() {
        val piece= Pawn(Color.BLACK)
        val startPos = Coord(1, 6)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(Coord(1,5), Coord(1,4))
        legalMoves.forEach { coord:Coord -> assertTrue(verifiedMoves.contains(coord)) }
    }

    @Test
    fun Should_MoveNorthOneSpaces_When_NotWhiteFirstMove() {
        val piece= Pawn(Color.WHITE)
        piece.firstMove =  false
        val startPos = Coord(1, 1)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(Coord(1,2))
        legalMoves.forEach { coord:Coord -> assertTrue(verifiedMoves.contains(coord)) }
    }

    @Test
    fun Should_MoveSouthOneSpaces_When_NotBlackFirstMove() {
        val piece= Pawn(Color.BLACK)
        piece.firstMove = false
        val startPos = Coord(1, 6)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(Coord(1,5))
        legalMoves.forEach { coord:Coord -> assertTrue(verifiedMoves.contains(coord)) }
    }

    @Test
    fun Should_NotMoveToIllegalSpaces_When_WhiteFirstMove() {
        val piece= Pawn(Color.WHITE)
        piece.firstMove =  true
        val startPos = Coord(1, 1)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(Coord(1,2),Coord(1,3))
        val illegalMoves = allCoordList.subtract(legalMoves.toList())

        for (move in illegalMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotMoveToIllegalSpaces_When_BlackFirstMove() {
        val piece= Pawn(Color.BLACK)
        piece.firstMove = true
        val startPos = Coord(1, 6)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(Coord(1,5), Coord(1,4))
        val illegalMoves = allCoordList.subtract(legalMoves.toList())

        for (move in illegalMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NomMoveToIllegalSpace_When_NotWhiteFirstMove() {
        val piece= Pawn(Color.WHITE)
        piece.firstMove =  false
        val startPos = Coord(1, 1)
        val verifiedMoves = piece.generateMovesList(startPos)
        val illegalMoves = arrayOf(Coord(1,3))

        for (move in illegalMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotMoveToIllegalSpace_When_NotBlackFirstMove() {
        val piece= Pawn(Color.BLACK)
        piece.firstMove = false
        val startPos = Coord(1, 6)
        val verifiedMoves = piece.generateMovesList(startPos)
        val illegalMoves = arrayOf(Coord(1,4))

        for (move in illegalMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotMoveToSquare_When_TheyAreOccupiedByAnyPiece() {
        val piece= Pawn(Color.WHITE)
        piece.firstMove = false
        val startPos = Coord(5, 1)
        val verifiedMoves = piece.generateMovesList(startPos)
        val illegalMoves = arrayOf(Coord(5,2))

        val board = Board()
        // Add piece to the board
        board.addPiece(piece, startPos)
        // Add the Black pawns in front of the White pawn
        illegalMoves.forEach { board.addPiece(Pawn(Color.BLACK),it)}


        for (move in illegalMoves) {
            assertFalse(piece.verifyMove(board,startPos,move))
        }
    }

    @Test
    fun Should_NotMoveToSquare_When_FirstMove_TheyAreOccupiedByAnyPiece() {
        val piece= Pawn(Color.WHITE)
        piece.firstMove = true
        val startPos = Coord(5, 1)
        val verifiedMoves = piece.generateMovesList(startPos)
        val illegalMoves = arrayOf(Coord(5,3))

        val board = Board()
        // Add piece to the board
        board.addPiece(piece, startPos)
        // Add the Black pawns in front of the White pawn
        illegalMoves.forEach { board.addPiece(Pawn(Color.BLACK),it)}


        for (move in illegalMoves) {
            assertFalse(piece.verifyMove(board,startPos,move))
        }
    }

    @Test
    fun Should_TakePiece_When_PieceIsDiagonal() {
        val piece= Pawn(Color.WHITE)
        piece.firstMove = true
        val startPos = Coord(1, 1)
        val takeablePieces = arrayOf(Coord(0,2), Coord(2,2))

        val board = Board()
        board.addPiece(piece, startPos)
        // Add opponent pawns to the board
        takeablePieces.forEach { board.addPiece(Pawn(Color.BLACK),it) }
        // Pawn should be able to take theses pieces
        takeablePieces.forEach { assertTrue(piece.verifyTake(board,startPos,it)) }
    }

    @Test
    fun Should_NotMovePassPiece_When_FirstMove() {
        val board= Board()
        val fromCoord = Coord(7, 0)
        board.addPiece(Rook(Color.BLACK), Coord(7,1))
        val pawn = Pawn(Color.WHITE)
        board.addPiece(pawn, fromCoord)

        println(board)

        assertFalse(pawn.verifyMove(board,fromCoord, Coord(7,2)))
    }
}