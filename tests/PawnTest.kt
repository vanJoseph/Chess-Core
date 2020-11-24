
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import wildercoding.chess.*

class PawnTest  {
    lateinit var allCoordList: List<Coord>

    @Before
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
}