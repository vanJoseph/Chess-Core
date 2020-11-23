import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import wildercoding.chess.*

class KnightTest {
    lateinit var piece: Piece
    lateinit var allCoordList: List<Coord>

    @Before
    fun setup() {
        piece = Knight(Color.WHITE)

        val allCoordsArrayList = arrayListOf<Coord>()
        for (y in 0..7) {
            for (x in 0..7) {
                allCoordsArrayList.add(Coord(x, y))
            }
        }
        allCoordList = allCoordsArrayList
    }

    @Test
    fun Should_GenerateAllLegalMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(
                Coord(2, 5), Coord(1, 2),
                Coord(4, 5), Coord(5, 2),
                Coord(1, 4), Coord(2, 1),
                Coord(5, 4), Coord(4, 1))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateIllegalMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(
                Coord(2, 5), Coord(1, 2),
                Coord(4, 5), Coord(5, 2),
                Coord(1, 4), Coord(2, 1),
                Coord(5, 4), Coord(4, 1))

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
                Coord(2, 1),
                Coord(1, 2))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateIllegalMoves_When_InCornerPosition() {
        val startPos = Coord(0, 0)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(
                Coord(2, 1),
                Coord(1, 2))
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
                Coord(6, 5), Coord(5, 2),
                Coord(5, 4), Coord(6, 1))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateIllegalMoves_WhenInEdgePosition() {
        val startPos = Coord(7, 3)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(
                Coord(6, 5), Coord(5, 2),
                Coord(5, 4), Coord(6, 1))
        val illegalMoves = allCoordList.subtract(legalMoves.toList())
        for (move in illegalMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }
}