import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import wildercoding.chess.*

class BishopTest {
    lateinit var piece: Piece
    lateinit var allCoordList: List<Coord>

    @Before
    fun setup() {
        piece = Bishop(Color.WHITE)

        val allCoordsArrayList = arrayListOf<Coord>()
        for (y in 0..7) {
            for (x in 0..7) {
                allCoordsArrayList.add(Coord(x, y))
            }
        }
        allCoordList = allCoordsArrayList
    }

    @Test
    fun Should_GenerateAllMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(
                Coord(4, 4), Coord(4, 2),
                Coord(5, 5), Coord(5, 1),
                Coord(6, 6), Coord(6, 0),
                Coord(7, 7), Coord(2, 2),
                Coord(2, 4), Coord(1, 1),
                Coord(1, 5), Coord(0, 0),
                Coord(0, 6))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateIllegalMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(
                Coord(4, 4), Coord(4, 2),
                Coord(5, 5), Coord(5, 1),
                Coord(6, 6), Coord(6, 0),
                Coord(7, 7), Coord(2, 2),
                Coord(2, 4), Coord(1, 1),
                Coord(1, 5), Coord(0, 0),
                Coord(0, 6))

        val illegalMoves = allCoordList.subtract(legalMoves.toList())

        for (move in illegalMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_GenerateAllMoves_When_InCornerPosition() {
        val startPos = Coord(0, 0)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(
                Coord(1, 1), Coord(5, 5),
                Coord(2, 2), Coord(6, 6),
                Coord(3, 3), Coord(7, 7),
                Coord(4, 4))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateIllegalMoves_When_InCornerPosition() {
        val startPos = Coord(0, 0)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(
                Coord(1, 1), Coord(5, 5),
                Coord(2, 2), Coord(6, 6),
                Coord(3, 3), Coord(7, 7),
                Coord(4, 4))
        val illegalMoves = allCoordList.subtract(legalMoves.toList())
        for (move in illegalMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_GenerateLegalMoves_WhenInEdgePosition() {
        val startPos = Coord(7, 3)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(
                Coord(6, 4), Coord(6, 2),
                Coord(5, 5), Coord(5, 1),
                Coord(4, 6), Coord(4, 0),
                Coord(3, 7))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateIllegalMoves_WhenInEdgePosition() {
        val startPos = Coord(7, 3)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(
                Coord(6, 4), Coord(6, 2),
                Coord(5, 5), Coord(5, 1),
                Coord(4, 6), Coord(4, 0),
                Coord(3, 7))
        val illegalMoves = allCoordList.subtract(legalMoves.toList())
        for (move in illegalMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }
    @Test
    fun Should_GenerateNeMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val bishop = piece as Bishop
        val verifiedMoves = bishop.generateNeMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(4, 4),
                Coord(5, 5),
                Coord(6, 6),
                Coord(7, 7))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateNonNeMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val bishop = piece as Bishop
        val verifiedMoves = bishop.generateNeMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(4, 4),
                Coord(5, 5),
                Coord(6, 6),
                Coord(7, 7))
        val nonMoves = allCoordList.subtract(legalMoves.toList())
        for (move in nonMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_GenerateSeMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val bishop = piece as Bishop
        val verifiedMoves = bishop.generateSeMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(4, 2),
                Coord(5, 1),
                Coord(6, 0))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateNonSeMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val bishop = piece as Bishop
        val verifiedMoves = bishop.generateSeMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(4, 2),
                Coord(5, 1),
                Coord(6, 0))
        val nonMoves = allCoordList.subtract(legalMoves.toList())
        for (move in nonMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_GenerateNwMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val bishop = piece as Bishop
        val verifiedMoves = bishop.generateNwMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(0, 6),
                Coord(1, 5),
                Coord(2, 4))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateNonNwMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val bishop = piece as Bishop
        val verifiedMoves = bishop.generateNwMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(0, 6),
                Coord(1, 5),
                Coord(2, 4))
        val nonMoves = allCoordList.subtract(legalMoves.toList())
        for (move in nonMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_GenerateSwMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val bishop = piece as Bishop
        val verifiedMoves = bishop.generateSwMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(2, 2),
                Coord(1, 1),
                Coord(0, 0))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateNonSwMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val bishop = piece as Bishop
        val verifiedMoves = bishop.generateSwMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(2, 2),
                Coord(1, 1),
                Coord(0, 0))
        val nonMoves = allCoordList.subtract(legalMoves.toList())
        for (move in nonMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }

}